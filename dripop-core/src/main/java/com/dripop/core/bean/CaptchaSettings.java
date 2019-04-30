package com.dripop.core.bean;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.NumberUtils;

@ConfigurationProperties(prefix = "captcha")
@Configuration
public class CaptchaSettings {
	
	public static final String LOGIN_CAPTCHA = "LOGIN_CAPTCHA";
	public static final String PRE_LOGIN_FAILURE_TIMES = "PRE_LOGIN_FAILURE_TIMES";
	private int height,width,length,fontStyle,fontSize,showGtLoginFailureTimes;
	private float strokeWidth;
	private boolean addBorder;
	private char[] word;
	private String background,noise,gimp;
	private String[] fontFamilies,fontColor;
	
	private static CaptchaSettings CAPTCHA_SETTINGS;
	@PostConstruct
	private void init(){
		CAPTCHA_SETTINGS = this;
	}
	
	public static CaptchaSettings instance(){
		return CAPTCHA_SETTINGS;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getNoise() {
		return noise;
	}
	public void setNoise(String noise) {
		this.noise = noise;
	}
	public char[] getWord() {
		return word;
	}
	public void setWord(char[] word) {
		this.word = word;
	}
	public boolean isAddBorder() {
		return addBorder;
	}
	public void setAddBorder(boolean addBorder) {
		this.addBorder = addBorder;
	}
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public String getGimp() {
		return gimp;
	}
	public void setGimp(String gimp) {
		this.gimp = gimp;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public float getStrokeWidth() {
		return strokeWidth;
	}
	public void setStrokeWidth(float strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	public String[] getFontFamilies() {
		return fontFamilies;
	}
	public void setFontFamilies(String[] fontFamilies) {
		this.fontFamilies = fontFamilies;
	}
	public String[] getFontColor() {
		return fontColor;
	}
	public void setFontColor(String[] fontColor) {
		this.fontColor = fontColor;
	}
	public int getFontStyle() {
		return fontStyle;
	}
	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getShowGtLoginFailureTimes() {
		return showGtLoginFailureTimes;
	}

	public void setShowGtLoginFailureTimes(int showGtLoginFailureTimes) {
		this.showGtLoginFailureTimes = showGtLoginFailureTimes;
	}
	
	public static void preLoginFailure(HttpServletRequest request){
		Object timeObject = 	request.getSession().getAttribute(PRE_LOGIN_FAILURE_TIMES);
		int times = 0;
		if(timeObject!=null){
			times=NumberUtils.parseNumber(String.valueOf(timeObject), Integer.class);
		}
		request.getSession().setAttribute(PRE_LOGIN_FAILURE_TIMES,++times);
	}
	public static int getLoginFailureTimes(HttpServletRequest request){
		Object timeObject = 	request.getSession().getAttribute(PRE_LOGIN_FAILURE_TIMES);
		int times = 0;
		if(timeObject!=null){
			times = NumberUtils.parseNumber(String.valueOf(timeObject), Integer.class);
		}
		return times;
	}
	public static boolean showCapcha(HttpServletRequest request){
		int times = getLoginFailureTimes(request);
		return times>instance().getShowGtLoginFailureTimes();
	}
}
