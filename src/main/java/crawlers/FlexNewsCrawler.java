/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers;

import crawlers.exceptions.TimeNotFoundException;
import crawlers.exceptions.ArticlesNotFoundException;
import db.news.NewsArticle;
import db.news.NewsAuthor;
import db.news.NewsSource;
import db.news.Tag;
import db.relationships.AuthoredBy;
import db.relationships.EditedBy;
import db.relationships.PublishedBy;
import db.relationships.TaggedAs;
import utils.Neo4jSessionFactoryForCrawlers;
import utils.elements.TitleElement;
import utils.elements.UrlElement;
import utils.elements.AuthorsElement;
import utils.elements.TimeElement;
import utils.elements.ContentElement;
import utils.elements.ImageUrlElement;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.DatabaseUtils;
import utils.FlexCrawlerLogger;

/**
 *
 * @author zua
 */
public abstract class FlexNewsCrawler {
    
    private FlexCrawlerLogger logger;
    private Neo4jSessionFactoryForCrawlers sessionFactory;
    
    public FlexNewsCrawler() {
        logger = new FlexCrawlerLogger(getClass());
        sessionFactory = Neo4jSessionFactoryForCrawlers.getInstance();
    }

    public abstract void crawl();

    protected void crawlWebsite(String url, NewsSource source) {
        logger.info("Processing source %s (%s)", source.getName(), url);
        Document document = openDocument(url);
        if(document != null) {
            crawlUrl(document, source);
            logger.info("Finished: %s", source.getName());
        }
        else {
            logger.info("Document not found");
        }
    }

    /**
     * Connects to the web address.
     *
     * @param url A web address url, starting with http(s).
     * @return The top document representing the content of web address.
     * @throws crawlers.exceptions.DocumentNotFoundException It no corresponding
     * document exists for the given url.
     */
    protected Document openDocument(String url) {
        try {
            return Jsoup.connect(url).userAgent("Mozilla").get();
        } catch (IOException | NullPointerException | IllegalArgumentException e) {
            logger.error("Could not open url %s: ", url);
            return null;
        }
    }

    protected NewsSource getSource() {
        return saveReturnSource(getMySource());
    }

    private void crawlUrl(Document document, NewsSource source) {
        Elements articles = getArticles(document);
        for(int i = 0; i < articles.size(); i++) {
            Element article = articles.get(i);
            importArticle(article, source);
        }
    }

    protected void importArticle(Element article, NewsSource source) {
        //prettyPrint(article);
        logger.log("Processing article: %s", article.text());

        String articleUrl = getUrl(article);
        if(articleUrl != null) {
            Document document = openDocument(articleUrl);
            if(document != null) {
                String title = getTitle(document);
                if(title != null) {
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
            
            PublishedBy publishedBy = new PublishedBy();
            publishedBy.setArticle(newsArticle);
            publishedBy.setSource(source);
            
            Tag tag = new Tag();
            tag.setTag(source.getCategory());
            
            TaggedAs taggedAs = new TaggedAs();
            taggedAs.setArticle(newsArticle);
            taggedAs.setTag(tag);
            
            AuthoredBy authoredBy = new AuthoredBy();
            authoredBy.setArticle(newsArticle);
            for(NewsAuthor na: authors) {
                authoredBy.setAuthor(na);
                EditedBy editedBy = new EditedBy();
                editedBy.setAuthor(na);
                editedBy.setSource(source);
            }

            sessionFactory.getNeo4jSession().save(newsArticle);
            logger.info("\tSaved new article: %s", newsArticle.getTitle());
        } else {
            logger.info("\tIgnored old article: %s", title);
        }
    }

    public abstract NewsSource getMySource();

    protected abstract Elements getArticles(Document document) throws ArticlesNotFoundException;

    public final String getUrl(Element article){
        return getUrlElement(article).getValue();
    }

    public final UrlElement getUrlElement(Element article){
        return new UrlElement(getUrlValue(article));
    }

    protected abstract String getUrlValue(Element article);

    public final String getTitle(Document document) {
        return getTitleElement(document).getValue();
    }

    public final TitleElement getTitleElement(Document document)  {
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
        return DatabaseUtils.getInstance().wrapUp(getContentElement(document).getValue());
    }

    public final ContentElement getContentElement(Document document)  {
        return new ContentElement(getContentValue(document));
    }

    protected abstract String getContentValue(Document document) ;

    public final Set<String> getAuthors(Document document) {
        AuthorsElement authorsElement = getAuthorsElement(document);
        if (authorsElement != null && !authorsElement.getAuthors().isEmpty()) {
            return authorsElement.getAuthors();
        }
        Set<String> result = new HashSet<>();
        result.add(getSource().getName());
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

    public final Date getPublishedAt(Document document) throws TimeNotFoundException {
        TimeElement timeElement = getTimeElement(document);
        timeElement.setLanguage(getSource().getLanguage());
        return timeElement.getDate();
    }

    public final TimeElement getTimeElement(Document document) throws TimeNotFoundException {
        return new TimeElement(getTimeValue(document), getSource().getLanguage());
    }

    protected abstract String getTimeValue(Document document) throws TimeNotFoundException;

    public FlexCrawlerLogger getLogger() {
        return logger;
    }

    private boolean notInBd(String title) {
        String query = "MATCH (n:NewsArticle) WHERE n.title='" + title + "' RETURN n";
        NewsArticle t = sessionFactory.getNeo4jSession().queryForObject(NewsArticle.class, query, new HashMap<String, Object>());
        return t == null;
    }
    
    protected NewsSource saveReturnSource(NewsSource source) {
        String query = "MATCH (s:NewsSource) WHERE s.sourceId='" + source.getSourceId() + "' RETURN s";
        NewsSource db = sessionFactory.getNeo4jSession().queryForObject(NewsSource.class, query, new HashMap<>());
        if(db != null) {
            return db;
        }
        sessionFactory.getNeo4jSession().save(source);
        return sessionFactory.getNeo4jSession().queryForObject(NewsSource.class, query, new HashMap<>());
    }

}
