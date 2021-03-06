/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import crawlers.Logos;
import db.news.NewsSource;
import db.news.Tag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zua
 */
public class SingleSourceResponse {

    private String id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String language;
    private String country;
    private Map<String, String> urlsToLogos;
    private List<String> sortBysAvailable;

    public SingleSourceResponse() {
        urlsToLogos = new HashMap<>();
        sortBysAvailable = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Map<String, String> getUrlsToLogos() {
        return urlsToLogos;
    }

    public void setUrlsToLogos(Map<String, String> urlsToLogos) {
        this.urlsToLogos = urlsToLogos;
    }

    public List<String> getSortBysAvailable() {
        return sortBysAvailable;
    }

    public void setSortBysAvailable(List<String> sortBysAvailable) {
        this.sortBysAvailable = sortBysAvailable;
    }


    public NewsSource convert2NewsSource() {
        NewsSource result = new NewsSource();
        result.setSourceId(id);
        result.setName(name);
        result.setDescription(description);
        result.setCountry(country);
        result.setLanguage(language);
        result.setUrl(url);
        result.setLogoUrl(Logos.getLogo(id));
        result.setCategory(new Tag(category));
        return result;
    }
}
