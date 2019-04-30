package com.dripop.nuonuoinvoice.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 压缩
 * @author sdk.jss.com.cn
 * @version 1.1
 * @since jdk1.6
 */
public class Compress {

	public static String compress(String str) throws Exception {
		if (str == null || str.length() == 0) {
			return "";
		}
		byte[] tArray;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		try {
			gzip.write(str.getBytes("UTF-8"));
			gzip.flush();
		} finally {
			gzip.close();
		}
		
		tArray = out.toByteArray();
		out.close();
		BASE64Encoder tBase64Encoder = new BASE64Encoder();
		return tBase64Encoder.encode(tArray);
	}

	public static String uncompress(String str) throws Exception {
		if (str == null || str.length() == 0) {
			return "";
		}
		BASE64Decoder tBase64Decoder = new BASE64Decoder();
		byte[] t = tBase64Decoder.decodeBuffer(str);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(t);
		GZIPInputStream gunzip = new GZIPInputStream(in);
		try {
			byte[] buffer = new byte[256];
			int n;
			while ((n = gunzip.read(buffer)) >= 0) {
				out.write(buffer, 0, n);
			}
		}finally{
			gunzip.close();
		}
		in.close();
		out.close();
		return out.toString("UTF-8");
	}
}
