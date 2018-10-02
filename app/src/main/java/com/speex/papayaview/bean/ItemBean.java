package com.speex.papayaview.bean;

/**
 * Created by Byron on 2018/10/2.
 */

public class ItemBean {
    private String url;
    private String content;

    public ItemBean(String url, String content) {
        this.url = url;
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
