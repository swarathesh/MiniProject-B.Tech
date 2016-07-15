package com.salyert.swarathesh.newsreader;

public class NewsItem {
    private String webTitle;
    private String webPublicationDate;
    private String webUrl;
    private String imageUrl;
    public  String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getWebTitle() {
        return webTitle;
    }
    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }
    public String getWebPublicationDate() {
        return webPublicationDate;
    }
    public void setWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
    }
    public String getWebUrl() {
        return webUrl;
    }
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    @Override
    public String toString() {
        return "NewsItem{" +
                "webTitle='" + webTitle + '\'' +
                ", webPublicationDate='" + webPublicationDate + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
