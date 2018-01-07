/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.aVerdadeOnline;

import backend.utils.MyDateUtils;
import crawlers.publishers.exceptions.ArticlesNotFoundException;
import crawlers.FlexNewsCrawler;
import crawlers.Logos;
import crawlers.publishers.exceptions.TimeNotFoundException;
import db.news.NewsSource;
import db.news.Tag;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author zua
 */

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
            getLogger().error(String.format("Exception thrown %s", e.getMessage()));
        }
        try {
            crawlWebsite(getUrl() + "/destaques", getMySource());
        } catch (Exception e) {
            getLogger().error(String.format("Exception thrown %s", e.getMessage()));
        }
        try {
            crawlWebsite(getUrl() + "/destaques/africa", getMySource());
        } catch (Exception e) {
            getLogger().error(String.format("Exception thrown %s", e.getMessage()));
        }
        try {
            crawlWebsite(getUrl() + "/destaques/democracia", getMySource());
        } catch (Exception e) {
            getLogger().error(String.format("Exception thrown %s", e.getMessage()));
        }
        try {
            crawlWebsite(getUrl() + "/destaques/economia", getMySource());
        } catch (Exception e) {
            getLogger().error(String.format("Exception thrown %s", e.getMessage()));
        }
        try {
            crawlWebsite(getUrl() + "/destaques/global-voices", getMySource());
        } catch (Exception e) {
            getLogger().error(String.format("Exception thrown %s", e.getMessage()));
        }
        try {
            crawlWebsite(getUrl() + "/destaques/internacional", getMySource());
        } catch (Exception e) {
            getLogger().error(String.format("Exception thrown %s", e.getMessage()));
        }
        try {
            crawlWebsite(getUrl() + "/destaques/nacional", getMySource());
        } catch (Exception e) {
            getLogger().error(String.format("Exception thrown %s", e.getMessage()));
        }
        try {
            crawlWebsite(getUrl() + "/destaques/tecnologias", getMySource());
        } catch (Exception e) {
            getLogger().error(String.format("Exception thrown %s", e.getMessage()));
        }
        try {
            crawlWebsite(getUrl() + "/destaques/tema-de-fundo", getMySource());
        } catch (Exception e) {
            getLogger().error(String.format("Exception thrown %s", e.getMessage()));
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

        NewsSource source = new NewsSource();
        source.setCategory(new Tag(category));
        source.setCountry(country);
        source.setDescription(description);
        source.setLanguage(language);
        source.setLogoUrl(Logos.getLogo(sourceId));
        source.setName(name);
        source.setSourceId(sourceId);
        source.setUrl(url);

        source.setLogoUrl(Logos.getLogo(sourceId));
        return source;
    }

    /*
    ""
     */
    @Override
    public Elements getArticles(Document document) throws ArticlesNotFoundException {
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
    protected String getUrlValue(Element article) {
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
        return null;
    }

    /*
    "";
     */
    @Override
    protected String getTitleValue(Document document) {
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
        return null;
    }

    /*
    "#main_body > div > table > tbody > tr > td > p > img[src]"
     */
    @Override
    protected String getImageUrlValue(Document document) {
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
        return null;
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
    protected String getAuthorsValue(Document document) {
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
        return getMySource().getName();
    }

    /*
     "#main_body > div > table:nth-child(4) > tbody > tr:nth-child(3) > td > p:nth-child(3)"
     */
    @Override
    protected String getContentValue(Document document)  {
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
        return null;
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
        return null;
    }

    @Override
    public Date getPublishedAt(Document document) throws TimeNotFoundException {
        String timeValue = getTimeValue(document);
        Date date = null;
        try {
            date = MyDateUtils.parseDate(timeValue, "pt");
        } catch (ParseException ex) {
            Logger.getLogger(AVerdadeOnlineCrawler.class.getName()).log(Level.SEVERE, null, ex);
            date = new Date();
        }
        return date;
    }
    
    
}
