package com.dripop.core.util;

import com.dripop.core.exception.SystemException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 上传非商品类图片至又拍云
 * <p>
 * 注意：直接使用部分图片处理功能后，将会丢弃原图保存处理后的图片
 */
public class UpOtherImgToUpyun {

	// 运行前先设置好以下三个参数
	private static final String BUCKET_NAME = SpringContextUtil.getPropertiesValue("file_cdn_bucket_name");
	private static final String OPERATOR_NAME = SpringContextUtil.getPropertiesValue("file_cdn_operate_name");
	private static final String OPERATOR_PWD = SpringContextUtil.getPropertiesValue("file_cdn_operate_pwd");

	/** 绑定的域名 */
	public static final String URL = SpringContextUtil.getPropertiesValue("file_cdn_url");

	/** 根目录 */
	private static final String DIR_ROOT = "/";

	private static UpYun upyun = null;

//	public static void main(String[] args) throws Exception {
//
//		// 初始化空间
//		upyun = new UpYun(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);
//
//		// ****** 可选设置 begin ******
//
//		// 切换 API 接口的域名接入点，默认为自动识别接入点
//		// upyun.setApiDomain(UpYun.ED_AUTO);
//
//		// 设置连接超时时间，默认为30秒
//		// upyun.setTimeout(60);
//
//		// 设置是否开启debug模式，默认不开启
//		upyun.setDebug(false);
//
//		// ****** 可选设置 end ******
//
//		/*
//		 * 一般性操作参考文件空间的demo（FileBucketDemo.java）
//		 * 
//		 * 注：图片的所有参数均可以自由搭配使用
//		 */
//
//		// 1.上传文件（文件内容）
//		testWriteFile();
//
//		// 2.图片做缩略图；若使用了该功能，则会丢弃原图
//		testGmkerl();
//
//		// 3.图片旋转；若使用了该功能，则会丢弃原图
//		testRotate();
//
//		// 4.图片裁剪；若使用了该功能，则会丢弃原图
//		testCrop();
//
//	}
	
	private static void initUpYun(){
		// 初始化空间
		upyun = new UpYun(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);

		// 设置是否开启debug模式，默认不开启
		upyun.setDebug(false);
	}

	/**
	 * 上传文件，返回上传文件的路径
	 * 
	 */
	public static String writeFile(String folder, File file) {
		initUpYun();
		String srcFileName = file.getName();
		if(StringUtil.isBlank(folder)) {
			folder = DIR_ROOT;
		}else {
			folder = DIR_ROOT + folder;
		}
		
//		// 临时文件存放地址:
//		String upload2 = FileConstantsUtil.UPLOAD_DIR_TEMP;
//		upload2 = upload2 + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
//		upload2 = FileUtil._getDestDir(upload2) + "/" + srcFileName;
//		File file = new File(upload2);
//		originfile.transferTo(file);
		try {
			// 要传到upyun后的文件路径：/yyyyMMdd/xxx.xx
			String md5key = UpYun.md5(file);
			String suffix = srcFileName.substring(srcFileName.lastIndexOf(".") + 1);
			String filePath = folder + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/" + md5key + "." + suffix;

			// 设置待上传文件的 Content-MD5 值
			// 如果又拍云服务端收到的文件MD5值与用户设置的不一致，将回报 406 NotAcceptable 错误
			upyun.setContentMD5(md5key);

			// 上传文件，并自动创建父级目录（最多10级）
			if (upyun.writeFile(filePath, file, true))
                return URL + filePath;
            else
                return "";
		} catch (IOException e) {
			throw new SystemException(e.getMessage(), e);
		}
	}

}