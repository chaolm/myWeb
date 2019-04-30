package com.dripop.controller;

import com.alibaba.fastjson.JSONObject;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.FileUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.core.util.UpOtherImgToUpyun;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by liyou on 2018/1/12.
 */
@Controller
@RequestMapping("common")
public class CommonController extends BaseController {

    /**
     * 图片等文件上传，返回给调用者文件在UPYUN中的文件路径
     * @param request
     * @return
     */
    @PostMapping("upload")
    @ResponseBody
    public ResultInfo uploadFile(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        String imageFile = reqJson.getString("fileData");
        String fileName = reqJson.getString("fileName");
        Integer type = reqJson.getInteger("type");
        if(imageFile.toLowerCase().indexOf(";base64,") > 0) {
            imageFile = imageFile.substring(imageFile.toLowerCase().indexOf(";base64,")+8, imageFile.length());
        }
        byte[] bytes = StringUtil.fromBase64(imageFile);

        // 把图片转化为文件
        String folder = null;
        if(type == 1) {
            folder = "goods/";
        }else if(type == 2) {
            folder = "image/";
        }else {
            throw new ServiceException("图片上传类型无效");
        }
        String upload = FileUtil.getYmdUploadDir(folder);
        String filePathTemp = FileUtil.upload(upload, bytes, FileUtil.getDestFileName(fileName));
        File f = new File(filePathTemp);
        // 上传U盘云
        String filePath = UpOtherImgToUpyun.writeFile(folder, f);
        // 删除本地文件
        if (f.exists() && f.isFile()) {
            f.delete();
        }
        return returnSuccess(filePath);
    }
}
