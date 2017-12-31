/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.makaAngola;

import crawlers.FlexNewsCrawler;
import crawlers.Logos;
import crawlers.publishers.exceptions.ArticlesNotFoundException;
import db.news.NewsSource;
import db.news.Tag;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author zua
 */
public class MakaAngolaCrawler extends FlexNewsCrawler {

    public MakaAngolaCrawler() {
        super();
    }

    private String getUrl() {
        return "https://www.makaangola.org";
    }

    @Override

    public void crawl() {
        try {
            crawlWebsite(getUrl(), getMySource());
        } catch (Exception e) {
            getLogger().error(String.format("Exception thrown %s", e.getMessage()));
        }
    }

    @Override
    public NewsSource getMySource() {
        String sourceId = "maka-angola";
        String name = "Maka Angola";
        String description = "Em defesa da democracia, contra a corrupção";
        String url = getUrl();
        String category = "política";
        String language = "pt";
        String country = "AO";

        NewsSource source = new NewsSource();
        source.setCategory(new Tag(category));
        source.setCountry(country);
        source.setDescription(description);
        source.setLanguage(language);
        source.setLogoUrl(Logos.getLogo(sourceId));
        source.setName(name);
        source.setSourceId(sourceId);
        source.setUrl(url);
        source.setLogoUrl(Logos.getLogo("maka-angola"));

        return source;
    }

    @Override
    public Elements getArticles(Document document) throws ArticlesNotFoundException {
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
    protected String getUrlValue(Element article) {
        if (article == null) {
            throw new IllegalArgumentException("Article cannot be null.");
        }
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
        Elements elements = document.select(".post-title");
        if (!elements.isEmpty() && !elements.text().isEmpty()) {
            return elements.text();
        }
        return null;
    }

    @Override
    protected String getImageUrlValue(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements images = document.select("section.primary > article > img");
        if (!images.isEmpty()) {
            Element image = images.first();
            if (image != null && !image.attr("src").isEmpty()) {
                return image.attr("src");
            }
        }
        return null;
    }

    @Override
    protected String getContentValue(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements contents = document.select("div.entry.clearfix > p:nth-child(1)");
        if (!contents.isEmpty()) {
            Element content = contents.first();
            if (content != null && !content.text().isEmpty()) {
                return content.text();
            }
        }
        return null;
    }

    @Override
    protected String getAuthorsValue(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements authors = document.select(".author");
        if (!authors.isEmpty() && !authors.text().isEmpty()) {
            return authors.text();
        }
        return getMySource().getName();
    }

    @Override
    protected String getTimeValue(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements times = document.select("time");
        if (!times.isEmpty()) {
            Element time = times.first();
            if (time != null && !time.attr("datetime").isEmpty()) {
                return time.attr("datetime");
            }
        }
        return null;
    }
}
