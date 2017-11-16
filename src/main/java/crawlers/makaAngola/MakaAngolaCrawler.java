/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.makaAngola;

import crawlers.FlexNewsCrawler;
import crawlers.Logos;
import crawlers.exceptions.UrlNotFoundException;
import crawlers.exceptions.TitleNotFoundException;
import crawlers.exceptions.TimeNotFoundException;
import crawlers.exceptions.ImageNotFoundException;
import crawlers.exceptions.AuthorsNotFoundException;
import crawlers.exceptions.ContentNotFoundException;
import crawlers.exceptions.ArticlesNotFoundException;
import db.NewsSource;
import javax.ejb.Stateless;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author zua
 */

@Stateless public class MakaAngolaCrawler extends FlexNewsCrawler {

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
            getLogger().error("Exception thrown %s", e.getMessage());
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

        NewsSource source = new NewsSource(sourceId, name, description, url, category, language, country);
        source.setLogoUrl(Logos.getLogo("maka-angola"));

        return source;
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
    protected String getUrlValue(Element article) throws UrlNotFoundException {
        if (article == null) {
            throw new IllegalArgumentException("Article cannot be null.");
        }
        Elements links = article.select("a");
        if (!links.isEmpty() && links.first() != null && !links.first().absUrl("href").isEmpty()) {
            return links.first().absUrl("href");
        }
        throw new UrlNotFoundException();
    }

    @Override
    protected String getTitleValue(Document document) throws TitleNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements elements = document.select(".post-title");
        if (!elements.isEmpty() && !elements.text().isEmpty()) {
            return elements.text();
        }
        throw new TitleNotFoundException();
    }

    @Override
    protected String getImageUrlValue(Document document) throws ImageNotFoundException {
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
        throw new ImageNotFoundException();
    }

    @Override
    protected String getContentValue(Document document) throws ContentNotFoundException {
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
        throw new ContentNotFoundException();
    }

    @Override
    protected String getAuthorsValue(Document document) throws AuthorsNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements authors = document.select(".author");
        if (!authors.isEmpty() && !authors.text().isEmpty()) {
            return authors.text();
        }
        throw new AuthorsNotFoundException();
    }

    @Override
    protected String getTimeValue(Document document) throws TimeNotFoundException {
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
        throw new TimeNotFoundException();
    }
}
