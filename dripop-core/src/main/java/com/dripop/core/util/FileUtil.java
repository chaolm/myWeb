/*
 * @(#)FileUtil.java 2013-12-10下午12:05:08
 * Copyright 2013 sinovatech, Inc. All rights reserved.
 */
package com.dripop.core.util;

import com.dripop.core.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件工具类
 *
 * <ul>
 * <li>
 * <b>修改历史：</b><br/>
 * <p>
 * [2015-9-29下午1:02:38]涂小炼<br/>
 * 新建
 * </p>
 * </li>
 * </ul>
 */

public class FileUtil {

	private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 上传
	 * @author 涂小炼
	 * @creationDate. 2013-12-10 下午02:38:52
	 * @param uploadDir 上传目录
	 * @param file 文件
	 * @param destFileName 目标文件名
	 * @return 上传的文件路径
	 * @throws IOException
	 */
	public static String upload(String uploadDir, MultipartFile file, String destFileName) throws IOException {
		// 获得目标目录
		String destDir = _getDestDir(uploadDir);

		// 上传文件全路径
		File destFile = new File(destDir + "/" + destFileName);

		// 写入硬盘
		FileCopyUtils.copy(file.getBytes(), destFile);

		// 返回文件相对路径
		return uploadDir + "/" + destFileName;
	}

	public static String upload(String uploadDir, String file, String destFileName) throws IOException {
		// 获得目标目录
		String destDir = _getDestDir(uploadDir);

		// 上传文件全路径
		File destFile = new File(destDir + "/" + destFileName);

		// 写入硬盘
		FileCopyUtils.copy(file.getBytes(), destFile);

		// 返回文件相对路径
		return destDir + "/" + destFileName;
	}

	public static String upload(String uploadDir, byte[] file, String destFileName) {
		// 获得目标目录
		String destDir = _getDestDir(uploadDir);

		// 上传文件全路径
		File destFile = new File(destDir + "/" + destFileName);

		// 写入硬盘
		try {
			FileCopyUtils.copy(file, destFile);
		} catch (IOException e) {
			throw new SystemException(e.getMessage(), e);
		}

		// 返回文件相对路径
		return destDir + "/" + destFileName;
	}

	/**
	 * 删除文件
	 * @author 涂小炼
	 * @creationDate. 2013-12-11 上午11:53:34
	 * @param filePath 文件相对路径
	 * @return 布尔
	 * @throws IOException
	 */
	public static boolean deleteFile(String filePath) throws IOException {
		File file = new File(_getRootFolderDiskPath() + filePath);
		if (file.exists() && file.isFile()) return file.delete();
		return false;
	}

	/**
	 * 获得年月日格式上传目录（上传目录 + 年/月/日）
	 * @author 涂小炼
	 * @creationDate. 2013-12-19 上午09:39:42
	 * @param uploadDir 上传目录
	 * @return 目标目录
	 */
	public static String getYmdUploadDir(String uploadDir) {
		return uploadDir + "/" + new SimpleDateFormat("yyyy/MM/dd").format(new Date());
	}

	/**
	 * 获得目标文件名称（年月日+系统纳秒数+源文件后缀）
	 * @author 涂小炼
	 * @creationDate. 2013-12-10 下午03:08:54
	 * @param srcFileName 源文件名称
	 * @return 文件名称
	 */
	public static String getDestFileName(String srcFileName) {
		String suffix = srcFileName.substring(srcFileName.lastIndexOf(".") + 1);
		return System.nanoTime() + "." + suffix;
	}

	/**
	 * 获得真实路径
	 * @author 涂小炼
	 * @creationDate. 2013-12-17 下午11:26:10
	 * @param uploadDir 上传目录
	 * @return 硬盘真实目录
	 */
	public static String getRealPath(String uploadDir) {
		return _getRootFolderDiskPath() + uploadDir;
	}

	/**
	 * 获得目标目录，上传目录不存在即创建
	 * @author 涂小炼
	 * @creationDate. 2013-12-10 下午04:22:07
	 * @param uploadDir 上传目录
	 * @return 目标目录
	 */
	public static String _getDestDir(String uploadDir) {
		String destDir = getRealPath(uploadDir);
		File dest = new File(destDir);
	    if (!dest.exists()) dest.mkdirs();
	    return destDir;
	}

	// 获得根目录的硬盘目录
	public static String _getRootFolderDiskPath() {
        return "/temp/";
	}

	public static String sha1(byte[] original) {
		MessageDigest msgDigest = null;
		try {
			msgDigest = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(
					"System doesn't support SHA1 algorithm.");
		}
		msgDigest.update(original);

		return BytesToHexString(msgDigest.digest());
	}

	public static String BytesToHexString(byte[] bytes) {
		byte tb;
		char low;
		char high;
		char tmpChar;
		StringBuffer str = new StringBuffer();

		for (int i = 0; i < bytes.length; i++) {
			tb = bytes[i];
			tmpChar = (char) ((tb >>> 4) & 0x000f);
			if (tmpChar >= 10) {
				high = (char) (('a' + tmpChar) - 10);
			} else {
				high = (char) ('0' + tmpChar);
			}
			str.append(high);
			tmpChar = (char) (tb & 0x000f);
			if (tmpChar >= 10) {
				low = (char) (('a' + tmpChar) - 10);
			} else {
				low = (char) ('0' + tmpChar);
			}
			str.append(low);
		}
		return str.toString();
	}

	public static void createFile(String filePath) throws IOException {
		File file = new File(filePath);
		if(!file.exists()) {
			file.createNewFile();
		}
	}

	public static void write2Txt(String content,File filePath) {
		FileOutputStream o=null;
		  try {
		   o = new FileOutputStream(filePath);
		      o.write(content.getBytes("UTF-8"));
		      o.close();
		  } catch (Exception e) {
		   // TODO: handle exception
		   log.error("系统异常",e);
		  }finally{
		  }
	}


}
