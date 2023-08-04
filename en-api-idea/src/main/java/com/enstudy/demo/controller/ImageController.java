package com.enstudy.demo.controller;


import com.enstudy.demo.controller.form.TokenForm;
import com.enstudy.demo.dto.CkEditorResult;
import com.enstudy.demo.util.ImageUploadUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@Tag(name = "DeptController", description = "图片上传接口")
public class ImageController {

    @Autowired
    private ImageUploadUtil imageUploadUtil;

    /**
     * 七牛云传入上传文件名，获取令牌
     * @param tokenForm
     * @return
     */
    @PostMapping("/upload-token")
    @Operation(summary = "获取上传令牌")
    public String getToken(@RequestBody TokenForm tokenForm) {
        System.out.println(tokenForm.getFileName());
        return imageUploadUtil.getToken(tokenForm.getFileName());
    }

    /**
     * ckeditor上传路径
     * @param upload
     * @return
     */
    @PostMapping("/upload-ck")
    public CkEditorResult addCkImage(MultipartFile upload) {
        CkEditorResult ckEditorResult=new CkEditorResult();
        String url=imageUploadUtil.uploadImageToQiNiu("image/", upload);
        if(url!=null){ //图片上传成功
            ckEditorResult.setUrl(url);
        }else{ //上传失败
            CkEditorResult.Error error=ckEditorResult.new Error();
            error.setMessage("上传失败");
            ckEditorResult.setError(error);
        }
        return ckEditorResult;
    }
}
