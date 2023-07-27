package com.enstudy.demo.dto;
/**
 * WangEditor使用的上传文件返回结果对象
 */
public class WangEditorResult {

    private int errno;
    private Data data;
    private String message;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Data{
        private String url; // 图片 src ，必须
        private String alt; // 图片描述文字，非必须
        private String href; // 图片的链接，非必须
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        public String getAlt() {
            return alt;
        }
        public void setAlt(String alt) {
            this.alt = alt;
        }
        public String getHref() {
            return href;
        }
        public void setHref(String href) {
            this.href = href;
        }
    }
}
