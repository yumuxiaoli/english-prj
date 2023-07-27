package com.enstudy.demo.dto;

/**
 * ckeditor上传图片返回的消息对象
 */
public class CkEditorResult {
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;
    private Error error;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public class Error{
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
