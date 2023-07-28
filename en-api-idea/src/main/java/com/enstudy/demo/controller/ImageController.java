package com.enstudy.demo.controller;

import com.enstudy.demo.controller.form.TokenForm;
import com.enstudy.demo.util.ImageUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageUploadUtil imageUploadUtil;

    /**
     * 七牛云传入上传文件名，获取令牌
     * @param tokenForm
     * @return
     */
    @PostMapping("/upload-token")
    public String getToken(@RequestBody TokenForm tokenForm) {
        System.out.println(tokenForm.getFileName());
        return imageUploadUtil.getToken(tokenForm.getFileName());
    }
}