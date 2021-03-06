/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers;

import crawlers.publishers.exceptions.TimeNotFoundException;
import crawlers.publishers.exceptions.ArticlesNotFoundException;
import db.news.NewsArticle;
import db.news.NewsAuthor;
import db.news.NewsSource;
import crawlers.elements.TitleElement;
import crawlers.elements.UrlElement;
import crawlers.elements.AuthorsElement;
import crawlers.elements.TimeElement;
import crawlers.elements.ContentElement;
import crawlers.elements.ImageUrlElement;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import backend.services.news.NewsArticleService;
import backend.services.news.NewsSourceService;
import crawlers.utils.FlexCrawlerLogger;
import java.net.URLEncoder;

/**
 *
 * @author zua
 */
public abstract class FlexNewsCrawler {

    private FlexCrawlerLogger logger;

    public FlexNewsCrawler() {
        logger = new FlexCrawlerLogger(getClass());
    }

    public abstract void crawl();

    protected void crawlWebsite(String url, NewsSource source) {
        logger.info(String.format("Processing source %s (%s)", source.getName(), url));
        Document document = openDocument(url);
        if (document != null && source != null) {
            crawlUrl(document, source);
        } else {
            logger.info("Document not found");
        }
    }

    /**
     * Connects to the web address.
     *
     * @param url A web address url, starting with http(s).
     * @return The top document representing the content of web address.
     * @throws crawlers.publishers.exceptions.DocumentNotFoundException It no
     * corresponding document exists for the given url.
     */
    public Document openDocument(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException | NullPointerException | IllegalArgumentException e) {
            logger.error(String.format("Could not open url %s: ", url));
            return null;
        }
    }

    private void crawlUrl(Document document, NewsSource source) {
        Elements articles = getArticles(document);
        for (int i = 0; i < articles.size(); i++) {
            Element article = articles.get(i);
            try {
                importArticle(article, source);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    protected void importArticle(Element article, NewsSource source) {
        //prettyPrint(article);
        String articleUrl = getUrl(article);
        if (articleUrl != null) {
            Document document = openDocument(articleUrl);
            if (document != null) {
                String title = getTitle(document);
                if (title != null) {
                    String imageUrl = getImageUrl(document);
                    String description = getContent(document);
                    Date date = getPublishedAt(document);
                    Set<NewsAuthor> authors = getNewsAuthors(getAuthors(document), source);
                    saveArticle(articleUrl, title, imageUrl, description, date, authors, source);
                }
            }
        }
    }

    private void saveArticle(String articleUrl, String title, String imageUrl, String description, Date date, Set<NewsAuthor> authors, NewsSource source) {
        if (title != null && !title.isEmpty() && notInBd(title)) {
            NewsArticle newsArticle = new NewsArticle();
            newsArticle.setLanguage(source.getLanguage());
            newsArticle.setCountry(source.getCountry());
            newsArticle.setTitle(title);
            newsArticle.setUrl(articleUrl);
            newsArticle.setImageUrl(imageUrl);
            newsArticle.setPublishedAt(date);
            newsArticle.setDescription(description);
            newsArticle.setSourceId(source.getSourceId());
            newsArticle.getTags().add(source.getCategory());

            for (NewsAuthor na : authors) {
                if (na != null) {
                    source.getAuthors().add(na);
                    na.getAuthored().add(newsArticle);
                }
            }
            new NewsSourceService().save(source);
            logger.info(String.format("\t Saved: %s", title));
        } else {
            logger.log(String.format("\tIgnored old article: %s", title));
        }
    }

    public abstract NewsSource getMySource();

    public abstract Elements getArticles(Document document) throws ArticlesNotFoundException;

    public final String getUrl(Element article) {
        return getUrlElement(article).getValue();
    }

    public final UrlElement getUrlElement(Element article) {
        return new UrlElement(getUrlValue(article));
    }

    protected abstract String getUrlValue(Element article);

    public final String getTitle(Document document) {
        return getTitleElement(document).getValue();
    }

    public final TitleElement getTitleElement(Document document) {
        return new TitleElement(getTitleValue(document));
    }

    protected abstract String getTitleValue(Document document);

    public final String getImageUrl(Document document) {
        return getImageUrlElement(document).getValue();
    }

    public final ImageUrlElement getImageUrlElement(Document document) {
        return new ImageUrlElement(getImageUrlValue(document));
    }

    protected abstract String getImageUrlValue(Document document);

    public final String getContent(Document document) {
        return getContentElement(document).getValue();
    }

    public final ContentElement getContentElement(Document document) {
        return new ContentElement(getContentValue(document));
    }

    protected abstract String getContentValue(Document document);

    public final Set<String> getAuthors(Document document) {
        AuthorsElement authorsElement = getAuthorsElement(document);
        if (authorsElement != null && !authorsElement.getAuthors().isEmpty()) {
            return authorsElement.getAuthors();
        }
        Set<String> result = new HashSet<>();
        result.add(getMySource().getName());
        return result;
    }

    public final AuthorsElement getAuthorsElement(Document document) {
        return new AuthorsElement(getAuthorsValue(document));
    }

    protected abstract String getAuthorsValue(Document document);

    protected Set<NewsAuthor> getNewsAuthors(Set<String> names, NewsSource source) {
        Set<NewsAuthor> result = new HashSet<>();
        if (names == null || names.isEmpty()) {
            result.add(findAuthor(source.getName()));
        } else {
            names.stream().filter((name) -> (name != null && !name.isEmpty())).forEachOrdered((name) -> {
                result.add(findAuthor(name));
            });
        }
        return result;
    }

    private NewsAuthor findAuthor(String name) {
        return new NewsAuthor(name);
    }

    public Date getPublishedAt(Document document) throws TimeNotFoundException {
        TimeElement timeElement = getTimeElement(document);
        Date date = timeElement.getDate();
        return (date != null) ? date : new Date();
    }

    public TimeElement getTimeElement(Document document) throws TimeNotFoundException {
        return new TimeElement(getTimeValue(document), getMySource().getLanguage());
    }

    protected abstract String getTimeValue(Document document) throws TimeNotFoundException;

    public FlexCrawlerLogger getLogger() {
        return logger;
    }

    private boolean notInBd(String title) {
        NewsArticle t = new NewsArticleService().find(title.trim());
        return t == null;
    }

}
