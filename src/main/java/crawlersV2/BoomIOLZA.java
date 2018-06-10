/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlersV2;

import crawlers.Logos;
import db.news.NewsArticle;
import db.news.NewsAuthor;
import db.news.NewsSource;
import db.news.Publish;
import db.news.Writes;
import java.io.IOException;
import java.text.ParseException;
import java.util.ListIterator;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import services.news.NewsArticleService;
import services.news.NewsSourceService;
import services.news.PublishService;
import services.news.WriteService;
import utils.MyDateUtils;

/**
 *
 * @author zua
 */
public class BoomIOLZA {

    private final String SOURCES_URL = "https://www.iol.co.za/";
    private final String COUNTRY = "ZA";
    private final String LANGUAGE = "en";
    private final Logger logger = Logger.getLogger("BoomIOLZA");

    public TreeSet<String> loadLinks() {
        TreeSet<String> result = new TreeSet();
        Elements anchorTags = selectAnchorTags(SOURCES_URL);
        ListIterator<Element> it = anchorTags.listIterator();
        for (int i = 0; it.hasNext(); i++) {
            String next = it.next().absUrl("href");
            if (next != null && !next.isEmpty()) {
                if (toArticle(next) != null) {
                    result.add(next);
                }
            }
        }
        return result;
    }

    public TreeSet<NewsArticle> loadArticles() {
        TreeSet<NewsArticle> result = new TreeSet();
        Elements anchorTags = selectAnchorTags(SOURCES_URL);
        ListIterator<Element> it = anchorTags.listIterator();
        int i = 0;
        while (it.hasNext()) {
            String next = it.next().absUrl("href");
            if (next != null && !next.isEmpty() && !inDb(next)) {
                NewsArticle newsArticle = toArticle(next);
                if (newsArticle != null) {
                    NewsAuthor author = toAuthor(next);
                    NewsSource source = toSource();
                    store(newsArticle, author, source);
                    result.add(newsArticle);
                }
            }
        }
        return result;
    }



    public boolean isArticleLink(String link) {
        logger.log(Level.WARNING, "---------------------------------");
        Elements article = selectArticle(link);
        if (article != null) {
            String title = article.select("meta[itemprop=headline]").attr("content");
            if (title == null || title.isEmpty()) {
                logger.log(Level.WARNING, "Empty title");
                return false;
            }

            String description = article.select("meta[itemprop=description]").attr("content");
            if (description == null || description.isEmpty()) {
                logger.log(Level.WARNING, "Empty description");
                return false;
            }

            String url = link;
            if (url == null || url.isEmpty()) {
                logger.log(Level.WARNING, "Empty url");
                return false;
            }

            Elements images = article.select("figure img");
            Element first = null;
            String src = null;
            if (images != null && !images.isEmpty()) {
                first = images.first();
                if (first != null) {
                    src = first.attr("src");
                    if (src == null || src.isEmpty()) {
                        logger.log(Level.WARNING, "Empty image");
                        return false;
                    }
                }

            }

            String imageCaption = article.select("span.imageCaption").text();
            if (imageCaption == null || imageCaption.isEmpty()) {
                logger.log(Level.WARNING, "Empty image copyright");
                return false;
            }

            String dateString = article.select("p.meta span[itemprop=datePublished]").attr("content");
            if (dateString == null || dateString.isEmpty()) {
                logger.log(Level.WARNING, "Empty date");
                return false;
            }

            String author = article.select("p.meta span[itemprop=author] strong[itemprop=name]").text();
            if (author == null || author.isEmpty()) {
                logger.log(Level.WARNING, "Empty author");
                return false;
            }

            logger.log(Level.WARNING, "title       : " + title);
            logger.log(Level.WARNING, "description : " + description);
            logger.log(Level.WARNING, "url         : " + url);
            logger.log(Level.WARNING, "image       : " + src);
            logger.log(Level.WARNING, "imageCaption: " + imageCaption);
            logger.log(Level.WARNING, "date        : " + dateString);
            logger.log(Level.WARNING, "author      : " + author);

            return true;
        }

        return false;

    }

    public NewsArticle toArticle(String articleUrl) {
        Elements article = selectArticle(articleUrl);
        if (article != null) {
            String title = article.select("meta[itemprop=headline]").attr("content");
            if (title == null || title.isEmpty()) {
                logger.log(Level.WARNING, "No title");
                return null;
            }

            String description = article.select("meta[itemprop=description]").attr("content");
            if (description == null || description.isEmpty()) {
                logger.log(Level.WARNING, "No description");
                return null;
            }

            String url = articleUrl;
            if (url == null || url.isEmpty()) {
                logger.log(Level.WARNING, "No URL");
                return null;
            }

            Elements images = article.select("figure img");
            Element first = null;
            String src = null;
            if (images != null && !images.isEmpty()) {
                first = images.first();
                if (first != null) {
                    src = first.attr("src");
                    if (src == null || src.isEmpty()) {
                        logger.log(Level.WARNING, "No image");
                        return null;
                    }
                } else {
                    logger.log(Level.WARNING, "No image");
                    return null;
                }
            } else {
                logger.log(Level.WARNING, "No image");
                return null;
            }

            String imageCaption = article.select("span.imageCaption").text();
            if (imageCaption == null || imageCaption.isEmpty()) {
                logger.log(Level.WARNING, "No image copyright");
                return null;
            }

            String dateString = article.select("p.meta span[itemprop=datePublished]").attr("content");
            if (dateString == null || dateString.isEmpty()) {
                logger.log(Level.WARNING, "No date");
                return null;
            }

            String author = article.select("p.meta span[itemprop=author] strong[itemprop=name]").text();
            if (author == null || author.isEmpty()) {
                logger.log(Level.WARNING, "No author");
                return null;
            }
            return toArticle(title, description, url, src, imageCaption, dateString, author);
        }

        return null;

    }

    private Elements selectAnchorTags(String url) {
        Connection connection = Jsoup.connect(url);
        if (connection != null) {
            try {
                Document document = connection.get();
                Elements anchorTags = document.body().getElementsByTag("a");
                logger.log(Level.WARNING, String.valueOf(anchorTags.size()));
                return anchorTags;
            } catch (IOException ex) {
                Logger.getLogger(BoomIOLZA.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private Elements selectArticle(String articlePage) {
        Connection connection = Jsoup.connect(articlePage);
        try {
            Document document = connection.validateTLSCertificates(false).ignoreContentType(true).get();
            Element body = document.body();
            Elements articles = body.select("article");
            return articles;
        } catch (Exception iox) {
            logger.log(Level.SEVERE, iox.getMessage());
        }
        return null;
    }

    private NewsArticle toArticle(String title, String description, String url, String imageUrl, String imageCaption, String dateString, String author) {
        NewsArticle newsArticle = new NewsArticle();
        newsArticle.setTitle(title);
        newsArticle.setDescription(description);
        try {
            newsArticle.setPublishedAt(MyDateUtils.parseDate(dateString));
        } catch (ParseException ex) {
            logger.log(Level.WARNING, ex.getMessage());
            return null;
        }
        newsArticle.setUrl(url);
        newsArticle.setImageUrl(imageUrl);
        newsArticle.setCountry(COUNTRY);
        newsArticle.setLanguage(LANGUAGE);
        return newsArticle;
    }
    
    public NewsAuthor toAuthor(String articleUrl) {
        Elements article = selectArticle(articleUrl);
        String author = article.select("p.meta span[itemprop=author] strong[itemprop=name]").text();
        if (author == null || author.isEmpty()) {
            logger.log(Level.WARNING, "No author");
            return null;
        }
        else {
            return new NewsAuthor(author);
        }

    }

    private NewsSource toSource() {
        // If there is a source corresponding to IOL SA, return it
        NewsSource source = getSourceFromDb("IOL South Africa");
        // Else create a new one
        if(source == null){
            source = new NewsSource();
            source.setName("IOL South Africa");
            source.setSourceId("iol-news-za");
            source.setLogoUrl(Logos.getLogo(source.getSourceId()));
            source.setCountry(COUNTRY);
            source.setLanguage(LANGUAGE);
            source.setUrl(SOURCES_URL);
        }
        
        return source;
    }

    private NewsSource getSourceFromDb(String name) {
        NewsSourceService service = new NewsSourceService();
        return service.findSourceNamed(name);
    }

    private void store(NewsArticle newsArticle, NewsAuthor author, NewsSource source) {
        new PublishService().save(new Publish(source, author));
        new WriteService().save(new Writes(author, newsArticle));
    }


    private boolean inDb(String next) {
        return new NewsArticleService().findArticlesWithUrl(next).iterator().hasNext();
    }

}
