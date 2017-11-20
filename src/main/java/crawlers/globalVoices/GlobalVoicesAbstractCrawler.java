/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.globalVoices;

import crawlers.FlexNewsCrawler;
import crawlers.exceptions.ArticlesNotFoundException;
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
        Elements urls = document.select("div > a > img");
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
    protected String getTimeValue(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements elements = document.select("span.post-date");
        if (!elements.isEmpty()) {
            Element dayElement = elements.first();
            if (dayElement != null) {
                String dayString = extractDate(dayElement.select("a").attr("title")).trim();
                if (dayString != null && !dayString.isEmpty()) {
                    Element timeElement = dayElement.select("span.post-time").first();
                    String timeString = timeElement.text().replace("GMT", "").trim();
                    timeString = timeString.substring(0, timeString.length() - 1);
                    if (timeString.length() == 4) {
                        timeString = ("0" + timeString);
                    }
                    return dayString + " " + timeString;
                }
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
}
