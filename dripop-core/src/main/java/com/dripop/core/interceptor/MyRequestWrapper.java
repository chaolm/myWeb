/**
 * MyRequestWrapper.java
 *
 * @screen
 * @author havery
 */
package com.dripop.core.interceptor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * MyRequestWrapper.
 *
 * @author havery
 */
public class MyRequestWrapper extends HttpServletRequestWrapper {
    private final String body;

    public MyRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
        String line = null;
        StringBuffer reqJson = new StringBuffer();
        while ((line = br.readLine()) != null) {
            reqJson.append(line).append("\n");
        }
        br.close();
        if(reqJson.length() > 0) {
            reqJson.delete(reqJson.length()-1, reqJson.length());
        }
        body = reqJson.toString();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletInputStream = new ServletInputStream() {
            public boolean isFinished() {
                return false;
            }

            public boolean isReady() {
                return false;
            }

            public void setReadListener(ReadListener readListener) {

            }

            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
        return servletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String getBody() {
        return this.body;
    }
}