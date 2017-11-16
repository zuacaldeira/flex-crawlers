/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.aVerdadeOnline;

import crawlers.exceptions.ArticlesNotFoundException;
import crawlers.exceptions.AuthorsNotFoundException;
import crawlers.exceptions.ContentNotFoundException;
import crawlers.FlexNewsCrawler;
import crawlers.exceptions.ImageNotFoundException;
import crawlers.Logos;
import crawlers.exceptions.TimeNotFoundException;
import crawlers.exceptions.TitleNotFoundException;
import crawlers.exceptions.UrlNotFoundException;
import db.NewsSource;
import javax.ejb.Stateless;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author zua
 */
@Stateless
public class AVerdadeOnlineCrawler extends FlexNewsCrawler {

    public AVerdadeOnlineCrawler() {
        super();
    }

    private String getUrl() {
        return "http://www.verdade.co.mz";
    }

    @Override
    
    public void crawl() {
        try {
            crawlWebsite(getUrl(), getMySource());
        } catch (Exception e) {
            getLogger().error("Exception thrown %s", e.getMessage());
        }
        try {
            crawlWebsite(getUrl() + "/destaques", getMySource());
        } catch (Exception e) {
            getLogger().error("Exception thrown %s", e.getMessage());
        }
        try {
            crawlWebsite(getUrl() + "/destaques/africa", getMySource());
        } catch (Exception e) {
            getLogger().error("Exception thrown %s", e.getMessage());
        }
        try {
            crawlWebsite(getUrl() + "/destaques/democracia", getMySource());
        } catch (Exception e) {
            getLogger().error("Exception thrown %s", e.getMessage());
        }
        try {
            crawlWebsite(getUrl() + "/destaques/economia", getMySource());
        } catch (Exception e) {
            getLogger().error("Exception thrown %s", e.getMessage());
        }
        try {
            crawlWebsite(getUrl() + "/destaques/global-voices", getMySource());
        } catch (Exception e) {
            getLogger().error("Exception thrown %s", e.getMessage());
        }
        try {
            crawlWebsite(getUrl() + "/destaques/internacional", getMySource());
        } catch (Exception e) {
            getLogger().error("Exception thrown %s", e.getMessage());
        }
        try {
            crawlWebsite(getUrl() + "/destaques/nacional", getMySource());
        } catch (Exception e) {
            getLogger().error("Exception thrown %s", e.getMessage());
        }
        try {
            crawlWebsite(getUrl() + "/destaques/tecnologias", getMySource());
        } catch (Exception e) {
            getLogger().error("Exception thrown %s", e.getMessage());
        }
        try {
            crawlWebsite(getUrl() + "/destaques/tema-de-fundo", getMySource());
        } catch (Exception e) {
            getLogger().error("Exception thrown %s", e.getMessage());
        }
    }

    @Override
    public NewsSource getMySource() {
        String sourceId = "verdade-online";
        String name = "@Verdade Online";
        String description = "";
        String url = "http://www.verdade.co.mz";
        String category = "geral";
        String language = "pt";
        String country = "MZ";

        NewsSource source = new NewsSource(sourceId, name, description, url, category, language, country);
        source.setLogoUrl(Logos.getLogo(sourceId));

        return source;
    }

    /*
    ""
     */
    @Override
    protected Elements getArticles(Document document) throws ArticlesNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements articles = document.select("table + table");
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
        Elements links = article.select("tbody > tr > td > a");
        for (Element link : links) {
            String href = link.absUrl("href");
            if (href != null && !href.isEmpty() && href.startsWith(getUrl())) {
                return href;
            }
        }
        throw new UrlNotFoundException();
    }

    /*
    "";
     */
    @Override
    protected String getTitleValue(Document document) throws TitleNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements links = document.select("td.contentheading");
        for (Element link : links) {
            String text = link.text();
            if (text != null && !text.isEmpty()) {
                return text;
            }
        }
        throw new TitleNotFoundException();
    }

    /*
    "#main_body > div > table > tbody > tr > td > p > img[src]"
     */
    @Override
    protected String getImageUrlValue(Document document) throws ImageNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements images = document.select("p > img");
        for (Element image : images) {
            String src = image.attr("src");
            if (src != null && !src.isEmpty()) {
                return getFullImageUrl(src);
            }
        }
        throw new ImageNotFoundException();
    }

    protected String getFullImageUrl(String src) {
        if (!src.startsWith(getUrl())) {
            return getUrl() + src;
        }
        return src;
    }

    /*
    "main > article > p.info-autor"
     */
    @Override
    protected String getAuthorsValue(Document document) throws AuthorsNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements elements = document.select("td > span.small > a");
        if (!elements.isEmpty() && !elements.text().isEmpty()) {
            String text = elements.text().trim();
            /*if (text.contains("|")) {
                int i = text.indexOf('|');
                text = text.substring(0, i);
            }*/
            return text;
        }
        throw new AuthorsNotFoundException();
    }

    /*
     "#main_body > div > table:nth-child(4) > tbody > tr:nth-child(3) > td > p:nth-child(3)"
     */
    @Override
    protected String getContentValue(Document document) throws ContentNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements elements = document.select("tbody > tr > td > p");
        Element first = elements.first();
        if (first != null) {
            String text = first.text().trim();
            if (text != null && !text.isEmpty()) {
                return text;
            }
        }
        throw new ContentNotFoundException();

    }

    /*
    "main > article > p > time"
     */
    @Override
    protected String getTimeValue(Document document) throws TimeNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements elements = document.select("td > span.small");
        if (!elements.isEmpty()) {
            String text = elements.first().ownText().trim();
            if (text != null && !text.isEmpty()) {
                String[] parts = text.split(" ");
                int k = parts.length;
                if (k >= 3) {
                    String result = parts[k - 3] + " " + parts[k - 2] + " " + parts[k - 1];
                    return result;
                }
            }
        }
        throw new TimeNotFoundException();
    }
}
