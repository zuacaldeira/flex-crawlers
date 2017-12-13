/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.iolNews;

import crawlers.FlexNewsCrawler;
import crawlers.publishers.exceptions.AuthorsNotFoundException;
import crawlers.publishers.exceptions.ArticlesNotFoundException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author zua
 */
public abstract class AbstactIOLNewsCrawler extends FlexNewsCrawler {

    protected abstract String getUrl();

    @Override
    public void crawl() {
        try {
            crawlWebsite(getMySource().getUrl(), getMySource());
        } catch (Exception e) {
            getLogger().error("Exception thrown %s", e.getMessage());
        }
    }

    @Override
    protected Elements getArticles(Document document) throws ArticlesNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements articles = document.select("article");
        if (!articles.isEmpty()) {
            return articles;
        }
        throw new ArticlesNotFoundException();
    }

    @Override
    protected String getUrlValue(Element article)  {
        // Check argument
        if (article == null) {
            throw new IllegalArgumentException("Article cannot be null");
        }
        // Select links
        Elements links = article.select("a");
        if (!links.isEmpty() && links.first() != null && !links.first().absUrl("href").isEmpty()) {
            return links.first().absUrl("href");
        }
        return null;
    }

    @Override
    protected String getTitleValue(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }

        Elements titles = document.select("main  article  header h1");
        if (!titles.isEmpty() && !titles.text().isEmpty()) {
            return titles.text().trim();
        }
        return null;
    }

    @Override
    protected String getImageUrlValue(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }

        Elements images = document.select("#top > main > div.wrapper.white-bg.main-article > article > div:nth-child(1) > div.article-body > figure > meta:nth-child(1)");
        if (!images.isEmpty() && !images.attr("content").isEmpty()) {
            return images.attr("content");
        }
        return null;
    }

    @Override
    protected String getContentValue(Document document){
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements contents = document.select("#article-more-body > p:nth-child(1)");
        if (!contents.isEmpty() && !contents.text().isEmpty()) {
            return contents.text();
        }
        return null;
    }

    @Override
    protected String getAuthorsValue(Document document) throws AuthorsNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements authors = document.select("main  article  header p.meta span strong");
        if (!authors.isEmpty() && !authors.text().isEmpty()) {
            return authors.text();
        }
        return getMySource().getName();
    }

    @Override
    protected String getTimeValue(Document document)  {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements times = document.select("main  article  header p.meta span[itemprop=datePublished]");
        if (!times.isEmpty() && !times.attr("content").isEmpty()) {
            return times.attr("content");
        }
        return null;
    }
}
