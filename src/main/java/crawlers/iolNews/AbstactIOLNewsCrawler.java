/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.iolNews;

import crawlers.FlexNewsCrawler;
import crawlers.exceptions.TimeNotFoundException;
import crawlers.exceptions.TitleNotFoundException;
import crawlers.exceptions.ImageNotFoundException;
import crawlers.exceptions.UrlNotFoundException;
import crawlers.exceptions.AuthorsNotFoundException;
import crawlers.exceptions.ContentNotFoundException;
import crawlers.exceptions.ArticlesNotFoundException;
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
            getLogger().log("%s %d %s", "Found ", articles.size(), " articles");
            return articles;
        }
        throw new ArticlesNotFoundException();
    }

    @Override
    protected String getUrlValue(Element article) throws UrlNotFoundException {
        // Check argument
        if (article == null) {
            throw new IllegalArgumentException("Article cannot be null");
        }
        // Select links
        Elements links = article.select("a");
        if (!links.isEmpty() && links.first() != null && !links.first().absUrl("href").isEmpty()) {
            getLogger().log("%s %s", "Found url ", links.first().absUrl("href"));
            return links.first().absUrl("href");
        }
        throw new UrlNotFoundException();
    }

    @Override
    protected String getTitleValue(Document document) throws TitleNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }

        Elements titles = document.select("main  article  header h1");
        if (!titles.isEmpty() && !titles.text().isEmpty()) {
            getLogger().log("%s %s", "Found title ", titles.text().trim());
            return titles.text().trim();
        }
        throw new TitleNotFoundException();
    }

    @Override
    protected String getImageUrlValue(Document document) throws ImageNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }

        Elements images = document.select("#top > main > div.wrapper.white-bg.main-article > article > div:nth-child(1) > div.article-body > figure > meta:nth-child(1)");
        if (!images.isEmpty() && !images.attr("content").isEmpty()) {
            getLogger().log("%s %s", "Found image ", images.attr("content"));
            return images.attr("content");
        }
        throw new ImageNotFoundException();
    }

    @Override
    protected String getContentValue(Document document) throws ContentNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements contents = document.select("#article-more-body > p:nth-child(1)");
        if (!contents.isEmpty() && !contents.text().isEmpty()) {
            getLogger().log("%s %s", "Found content ", contents.text());
            return contents.text();
        }
        throw new ContentNotFoundException();
    }

    @Override
    protected String getAuthorsValue(Document document) throws AuthorsNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements authors = document.select("main  article  header p.meta span strong");
        if (!authors.isEmpty() && !authors.text().isEmpty()) {
            getLogger().log("%s", "Found authors " + authors.text());
            return authors.text();
        }
        throw new AuthorsNotFoundException();
    }

    @Override
    protected String getTimeValue(Document document) throws TimeNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements times = document.select("main  article  header p.meta span[itemprop=datePublished]");
        if (!times.isEmpty() && !times.attr("content").isEmpty()) {
            getLogger().log("%s", "Found time " + times.attr("content"));
            return times.attr("content");
        }
        throw new TimeNotFoundException();
    }
}
