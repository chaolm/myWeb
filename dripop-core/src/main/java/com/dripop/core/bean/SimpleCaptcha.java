package com.dripop.core.bean;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dripop.core.util.SessionUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.Captcha.Builder;
import cn.apiclub.captcha.backgrounds.BackgroundProducer;
import cn.apiclub.captcha.gimpy.GimpyRenderer;
import cn.apiclub.captcha.noise.NoiseProducer;
import cn.apiclub.captcha.servlet.CaptchaServletUtil;
import cn.apiclub.captcha.text.producer.DefaultTextProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;
import cn.apiclub.captcha.text.renderer.WordRenderer;
import sun.misc.BASE64Encoder;

@Component
public class SimpleCaptcha {
	private static final String CLASS_PACKAGE = "cn.apiclub.captcha";
	private static final String BACKGROUND_CLASS_PACKAGE = CLASS_PACKAGE
			+ ".backgrounds";
	private static final String NOISE_CLASS_PACKAGE = CLASS_PACKAGE + ".noise";
	private static final String GIMP_CLASS_PACKAGE = CLASS_PACKAGE + ".gimpy";
	private static final String BACKGROUND_CLASS_FMT = BACKGROUND_CLASS_PACKAGE
			+ ".%sBackgroundProducer";
	private static final String NOISE_CLASS_FMT = NOISE_CLASS_PACKAGE
			+ ".%sNoiseProducer";
	private static final String GIMP_CLASS_FMT = GIMP_CLASS_PACKAGE
			+ ".%sGimpyRenderer";
	public static final String SIMPLE_CAPTCHA_SESSION_KEY_FMT = Captcha.class
			.getName() + ".%s";
	private Log log = LogFactory.getLog(SimpleCaptcha.class);

	@Autowired
	private CaptchaSettings captchaSettings;
	private Builder builder;
	private Captcha currentCaptcha;

	private String answer;

	public SimpleCaptcha init() {
		this.initBuilder().initText().initGimp().initBackground().initNoise();
		return this;
	}

	protected SimpleCaptcha initBuilder() {
		builder = new Builder(captchaSettings.getWidth(),
				captchaSettings.getHeight());
		return this;
	}

	protected SimpleCaptcha initBackground() {
		try {
			builder.addBackground((BackgroundProducer) ClassUtils.forName(
					String.format(BACKGROUND_CLASS_FMT,
							captchaSettings.getBackground()), null)
					.newInstance());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return this;
	}

	protected SimpleCaptcha initBorder() {
		if (captchaSettings.isAddBorder()) {
			builder.addBorder();
		}
		return this;
	}

	protected SimpleCaptcha initNoise() {
		if ("none".equalsIgnoreCase(captchaSettings.getNoise())) {
			return this;
		}
		try {
			builder.addNoise((NoiseProducer) ClassUtils.forName(
					String.format(NOISE_CLASS_FMT, captchaSettings.getNoise()),
					null).newInstance());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return this;
	}

	protected SimpleCaptcha initGimp() {
		try {
			builder.gimp((GimpyRenderer) ClassUtils.forName(
					String.format(GIMP_CLASS_FMT, captchaSettings.getGimp()),
					null).newInstance());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return this;
	}

	protected SimpleCaptcha initText() {
		DefaultTextProducer dtp = new DefaultTextProducer(
				captchaSettings.getLength(), captchaSettings.getWord());
		builder.addText(dtp, createColorWord());
		return this;
	}

	private WordRenderer createColorWord() {
		List<Color> colors = new ArrayList<Color>(
				ArrayUtils.isEmpty(captchaSettings.getFontColor()) ? 1
						: captchaSettings.getFontColor().length);
		List<Font> fonts = new ArrayList<Font>(
				ArrayUtils.isEmpty(captchaSettings.getFontFamilies()) ? 1
						: captchaSettings.getFontFamilies().length);
		try {
			fillFont(colors, fonts);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		DefaultWordRenderer cewr = new DefaultWordRenderer(colors,
				fonts);
		return cewr;
	}

	private void fillFont(List<Color> colors, List<Font> fonts)
			throws IllegalArgumentException, IllegalAccessException {
		if (!ArrayUtils.isEmpty(captchaSettings.getFontColor())) {
			for (int i = 0; i < captchaSettings.getFontColor().length; i++) {
				String color = captchaSettings.getFontColor()[i];
				colors.add((Color) ReflectionUtils
						.findField(Color.class, color).get(null));
			}
		} else {
			colors.add(Color.black);
		}
		if (!ArrayUtils.isEmpty(captchaSettings.getFontFamilies())) {
			for (int i = 0; i < captchaSettings.getFontFamilies().length; i++) {
				String font = captchaSettings.getFontFamilies()[i];
				fonts.add(new Font(font, captchaSettings.getFontStyle(),
						captchaSettings.getFontSize()));
			}
		} else {
			fonts.add(new Font("Arial", Font.BOLD, captchaSettings
					.getFontSize() <= 0 ? 40 : captchaSettings.getFontSize()));
		}
	}

	protected Captcha build() {
		return this.currentCaptcha = builder.build();
	}

	public Captcha getCurrentCaptcha() {
		return currentCaptcha;
	}

	public Captcha writeAndAddToSession(HttpServletRequest request,
			HttpServletResponse response, String key) {
		Captcha captcha = init().build();
		key = StringUtils.hasText(key) ? key : String.format(SIMPLE_CAPTCHA_SESSION_KEY_FMT, Captcha.NAME);
		SessionUtil.setSession(key, captcha);
		CaptchaServletUtil.writeImage(response, captcha.getImage());
		return captcha;
	}

	public String base64Captcha() {
		Captcha captcha = init().build();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
		try {
			ImageIO.write(captcha.getImage(), "png", baos);//写入流中
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] bytes = baos.toByteArray();//转换成字节
		BASE64Encoder encoder = new BASE64Encoder();
		String png_base64 =  encoder.encodeBuffer(bytes).trim();
		return "data:image/png;base64," + png_base64;
	}

	public String getAnswer() {
		return getCurrentCaptcha().getAnswer();
	}
}
