/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.telaNon;

import crawlers.FlexNewsCrawler;
import crawlers.Logos;
import crawlers.exceptions.ArticlesNotFoundException;
import db.news.NewsSource;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author zua
 */
public class TelaNonCrawler extends FlexNewsCrawler {

    public TelaNonCrawler() {
    }

    @Override
    public void crawl() {
        try {
            crawlWebsite(getMySource().getUrl(), getMySource());
        } catch (Exception e) {
            getLogger().error("Exception thrown %s", e.getMessage());
        }
    }

    @Override
    public NewsSource getMySource() {
        NewsSource source = new NewsSource();
        source.setCategory("geral");
        source.setCountry("ST");
        source.setDescription("");
        source.setLanguage("pt");
        source.setSourceId("tela-non");
        source.setLogoUrl(Logos.getLogo(source.getSourceId()));
        source.setName("Téla Nón");
        source.setUrl("http://www.telanon.info/");
        source.setLogoUrl(Logos.getLogo("tela-non"));
        return source;
    }

    @Override
    protected Elements getArticles(Document document) throws crawlers.exceptions.ArticlesNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null.");
        }
        Elements articles = document.select("body div.blog-item-holder div.blog-content-wrapper");
        if (!articles.isEmpty()) {
            getLogger().log("%s %d %s", "Found ", articles.size(), " articles");
            return articles;
        }
        throw new ArticlesNotFoundException();
    }

    @Override
    protected String getUrlValue(Element article) {
        if (article == null) {
            throw new IllegalArgumentException("Article cannot be null");
        }
        Elements urls = article.select("div > a");
        if (!urls.isEmpty() && !urls.attr("href").isEmpty()) {
            getLogger().log("%s %s", "Found url: ", urls.attr("href"));
            return urls.attr("href");
        }
        return null;
    }

    @Override
    protected String getTitleValue(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null.");
        }
        Elements titles = document.select("body h1 a");
        if (!titles.isEmpty() && titles.first() != null && !titles.first().text().isEmpty()) {
            String title = titles.first().text();
            getLogger().log("%s %s", "Found title: ", title);
            return title;
        }
        return null;
    }

    @Override
    protected String getImageUrlValue(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null.");
        }
        Elements images = document.select("body div.gdl-blog-full > div.blog-content > div.blog-media-wrapper.gdl-image > a > img");
        if (!images.isEmpty() && !images.attr("src").isEmpty()) {
            String image = images.attr("src");
            getLogger().log("%s %s", "Found image: ", image);
            return image;
        }
        return null;
    }

    @Override
    protected String getContentValue(Document document)  {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null.");
        }
        Elements contents = document.select("body div.gdl-blog-full > div.blog-content > p");
        if (!contents.isEmpty() && contents.first() != null && !contents.first().text().isEmpty()) {
            String content = contents.first().text();
            getLogger().log("%s %s", "Found content: ", content);
            return content;
        }
        return null;
    }

    @Override
    protected String getAuthorsValue(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null.");
        }
        return getMySource().getName();
    }

    @Override
    protected String getTimeValue(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null.");
        }
        Elements paragraphs = document.select("body div.blog-date > a");
        if (!paragraphs.isEmpty() && paragraphs.first() != null && !paragraphs.first().text().isEmpty()) {
            String time = paragraphs.first().text();
            getLogger().log("%s %s", "Found time: ", time);
            return time;
        }
        return null;
    }
}
