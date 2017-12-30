/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.globalVoices;

import crawlers.FlexNewsCrawler;
import crawlers.Logos;
import crawlers.publishers.exceptions.ArticlesNotFoundException;
import crawlers.publishers.exceptions.TimeNotFoundException;
import db.news.NewsSource;
import db.news.Tag;
import db.relationships.TaggedSourceAs;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author zua
 */
public abstract class GlobalVoicesAbstractCrawler extends FlexNewsCrawler {

    public GlobalVoicesAbstractCrawler() {
        super();
    }

    @Override
    public final NewsSource getMySource() {
        String sourceId = "global-voices";
        String name = "Global Voices";
        String description = "";
        String url = "https://globalvoices.org";
        String category = "community";

        NewsSource source = new NewsSource();
        TaggedSourceAs tagged = new TaggedSourceAs();
        tagged.setSource(source);
        tagged.setTag(new Tag(category));
        source.setCountry(getSourceCountry());
        source.setDescription(description);
        source.setLanguage(getSourceLanguage());
        source.setName(name + " (" + source.getLanguage().toUpperCase() + ")");
        source.setSourceId(sourceId + "-" + source.getLanguage());
        source.setUrl("https://" + getSubdomain() + ".globalvoices.org");

        source.setLogoUrl(Logos.getLogo("global-voices"));
        return source;
    }

    @Override
    public final void crawl() {
        try {
            crawlWebsite(getMySource().getUrl(), getMySource());
        } catch (Exception e) {
            getLogger().error(String.format("Exception thrown %s", e.getMessage()));
        }
    }

    @Override
    public Elements getArticles(Document document) throws ArticlesNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements body = document.select("div.main");
        if (!body.isEmpty() && !body.select("div.post-summary-content").isEmpty()) {
            return body.select("div.post-summary-content");
        }
        throw new ArticlesNotFoundException();
    }

    @Override
    protected String getUrlValue(Element article) {
        if (article == null) {
            throw new IllegalArgumentException("Article cannot be null");
        }
        Elements urls = article.select("a");
        if (!urls.isEmpty()) {
            Element url = urls.first();
            if (url != null && !url.absUrl("href").isEmpty()) {
                return url.absUrl("href");
            }
        }
        return null;
    }

    @Override
    protected String getTitleValue(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements titles = document.select("div.post-header a");
        if (!titles.isEmpty() && !titles.text().isEmpty()) {
            return titles.text();
        }
        return null;
    }

    @Override
    protected String getImageUrlValue(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements urls = document.select("div#single img");
        if (!urls.isEmpty()) {
            Element url = urls.first();
            if (url != null && !urls.attr("src").isEmpty()) {
                return urls.attr("src");
            }
        }
        return null;
    }

    @Override
    protected String getContentValue(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements paragraphs = document.select("div#single.entry > p");
        if (!paragraphs.isEmpty()) {
            Element paragraph = paragraphs.first();
            if (paragraph != null && !paragraph.text().isEmpty()) {
                return paragraph.text();
            }
        }
        return null;
    }

    @Override
    protected String getAuthorsValue(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements elements = document.select("#sidebar > div.postmeta-sidebar > div.postmeta-container.post-credit-container > div > div.author.contributor > div.contributor-name > a");
        if (!elements.isEmpty() && !elements.text().isEmpty()) {
            return elements.text().trim();
        }
        return getMySource().getName();
    }

    @Override
    public final Date getPublishedAt(Document document) throws TimeNotFoundException {
        try {
            String timeValue = getTimeValue(document).trim();
            //getLogger().log("##########################A %s", timeValue);
            Date date = DateUtils.parseDate(timeValue, new String[]{"yyyy-MM-dd"});
            return date;
        } catch (ParseException ex) {
            Logger.getLogger(GlobalVoicesAbstractCrawler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    protected String getTimeValue(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements elements = document.select("span.post-date");
        if (!elements.isEmpty()) {
            Element dayElement = elements.first();
            if (dayElement != null) {
                String dayString = dayElement.select("a").attr("title");
                dayString = dayString.substring(dayString.length() - 10);
                dayString = dayString.replace("/", "-");
                /*
                if (dayString != null && !dayString.isEmpty()) {
                    String timeString = dayElement.select("span.post-time").first().text().trim();
                    timeString = timeString.trim().replace("GMT", " ").trim();
                    if(timeString.length() < 5) {
                        timeString = "0" + timeString + ":00";
                    }
                    String result = dayString.trim() + " " + timeString;
                    return result;
                }
                 */
                return dayString;
            }
        }
        return null;
    }

    protected String extractDate(String phrase) {
        if (phrase != null) {
            String[] parts = phrase.split(" ");
            if (parts.length > 0) {
                return parts[parts.length - 1].replace('/', '-');
            }
        }
        return null;
    }

    protected abstract String getSourceCountry();

    protected abstract String getSourceLanguage();

    protected String getSubdomain() {
        return getSourceLanguage();
    }
}
