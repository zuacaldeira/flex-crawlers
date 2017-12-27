/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.json;

import db.news.NewsArticle;
import db.news.NewsAuthor;
import db.news.NewsSource;
import java.util.Date;
import backend.utils.MyDateUtils;

/**
 *
 * @author zua
 */
public class SingleArticleResponse {

    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String source;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    
    

    public NewsArticle convert2NewsArticle(NewsSource source) {
        Date date;
        try {
            date = MyDateUtils.parseDate(publishedAt, source.getLanguage());
        } catch (Exception ex) {
            date = new Date();
        }
        String language = source.getLanguage();
        String country = source.getCountry().toUpperCase();
        
        NewsArticle article = new NewsArticle();
        article.setTitle(title);
        article.setDescription(description);
        article.setUrl(url);
        article.setImageUrl(urlToImage);
        article.setPublishedAt(date);
        article.setLanguage(language);
        article.setCountry(country);
        article.setSourceId(source.getSourceId());
        return article;
    }

    public NewsAuthor convert2NewsAuthor(NewsSource aSource) {
        if(author != null) {
            return new NewsAuthor(author);
        }
        else {
            return new NewsAuthor(aSource.getName());
        }
    }

}
