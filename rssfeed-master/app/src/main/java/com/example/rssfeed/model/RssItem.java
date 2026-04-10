package com.example.rssfeed.model;

public class RssItem {
    private String title;
    private String description;
    private String link;
    private String pubDate;
    private String imageUrl;

    public RssItem(String title, String description,
                   String link, String pubDate, String imageUrl) {
        this.title       = title;
        this.description = description;
        this.link        = link;
        this.pubDate     = pubDate;
        this.imageUrl    = imageUrl;
    }

    public String getTitle()       { return title; }
    public String getDescription() { return description; }
    public String getLink()        { return link; }
    public String getPubDate()     { return pubDate; }
    public String getImageUrl()    { return imageUrl; }
}