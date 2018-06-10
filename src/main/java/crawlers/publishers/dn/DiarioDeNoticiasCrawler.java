/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.dn;

import crawlers.FlexNewsCrawler;
import crawlers.Logos;
import crawlers.publishers.exceptions.ArticlesNotFoundException;
import crawlers.publishers.exceptions.TimeNotFoundException;
import db.news.NewsSource;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author zua
 */
public class DiarioDeNoticiasCrawler extends FlexNewsCrawler {

    @Override
    public void crawl() {
        crawlWebsite(getMySource().getUrl(), getMySource());
    }

    @Override
    public NewsSource getMySource() {
        NewsSource source = new NewsSource();
        source.setSourceId("diario-de-noticias-pt");
        source.setName("Diário de Notícias");
        source.setCountry("PT");
        source.setLanguage("pt");
        source.setLogoUrl(Logos.getLogo(source.getSourceId()));
        source.setUrl("https://www.dn.pt");
        
        return source;
    }

    @Override
    public Elements getArticles(Document document) throws ArticlesNotFoundException {
        if(document == null) {
            throw new IllegalArgumentException();
        }
        Elements articles = document.select("a");
        return articles;
    }

    @Override
    protected String getUrlValue(Element article) {
        if(article == null) {
            throw new IllegalArgumentException();
        }
        return article.absUrl("href");
    }

    @Override
    protected String getTitleValue(Document document) {
        if(document == null) {
            throw new IllegalArgumentException();
        }
        String title = document.select("header > h1").text();
        if(title == null || title.isEmpty()) {
            return null;
        }
        return title;
    }

    @Override
    protected String getImageUrlValue(Document document) {
        if(document == null) {
            throw new IllegalArgumentException();
        }        
        return document.select("body > main > article > div > div.t-article-wrap-inner > div > div.t-article-content-1.js-article-media-root > figure > img").attr("src");
    }

    @Override
    protected String getContentValue(Document document) {
        if(document == null) {
            throw new IllegalArgumentException();
        }
        return document.select("body > main > article > div > div.t-article-wrap-inner > div > div.t-article-content-2 > p:nth-child(3)").text();
    }

    @Override
    protected String getAuthorsValue(Document document) {
        if(document == null) {
            throw new IllegalArgumentException();
        }
        String authors = document.select("body > main > article > header > div > div > span > em").text();
        if(authors != null && !authors.isEmpty()) {
            return authors;
        }
        else {
            return getMySource().getName();
        }
    }

    @Override
    protected String getTimeValue(Document document) throws TimeNotFoundException {
        Elements timeElements = document.select("body  article  time");
        if(timeElements != null && !timeElements.isEmpty()) {
            String value = timeElements.attr("datetime");
            if(value != null && !value.trim().isEmpty()) {
                return transform(value.trim());
            }
        }
        return null;
    }
    
    private String transform(String date) {
        return date.replace("\u00a0", "T");
    }

    private boolean isArticle(Document linkDocument) {
        return !linkDocument.select(getArticleSelector()).isEmpty();
    }

    private String getArticleSelector() {
        return "body > main > article";
    }
    
}
