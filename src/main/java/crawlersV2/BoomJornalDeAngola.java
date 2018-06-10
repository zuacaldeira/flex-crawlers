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
public class BoomJornalDeAngola {

    private final String SOURCES_URL = "http://jornaldeangola.sapo.ao/";
    private final String COUNTRY = "AO";
    private final String LANGUAGE = "pt";
    private final Logger logger = Logger.getLogger("Jornal de Angola");

    public TreeSet<String> loadLinks() {
        TreeSet<String> result = new TreeSet();
        Elements anchorTags = selectAnchorTags(SOURCES_URL);
        ListIterator<Element> it = anchorTags.listIterator();
        for (int i = 0; it.hasNext(); i++) {
            String next = it.next().absUrl("href");
            if (next != null && !next.isEmpty()) {
                System.out.print(next + ": ");
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
            String title = article.select("h1").first().text();
            if (title == null || title.isEmpty()) {
                logger.log(Level.WARNING, "Empty title");
                return false;
            }

            String description = article.select("p:nth-child(6)").text();
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

            String imageCaption = article.select("figcaption").text();
            if (imageCaption == null || imageCaption.isEmpty()) {
                logger.log(Level.WARNING, "Empty image copyright");
                //return false;
            }

            String dateString = article.select("time.date-time").attr("datetime");
            if (dateString == null || dateString.isEmpty()) {
                logger.log(Level.WARNING, "Empty date");
                return false;
            }

            String author = article.select("p.info-autor").text();
            if (author == null || author.isEmpty()) {
                logger.log(Level.WARNING, "Empty author");
                author = "Jornal de Angola";
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
            Elements ts = article.select("h1");
            if (ts == null) {
                logger.log(Level.WARNING, "Empty title");
                return null;
            }
            Element f = ts.first();
            if (f == null) {
                logger.log(Level.WARNING, "Empty title");
                return null;
            }
            String title = f.text();
            if (title == null || title.isEmpty()) {
                logger.log(Level.WARNING, "Empty title");
                return null;
            }

            String description = article.select("p:nth-child(6)").text();
            if (description == null || description.isEmpty()) {
                logger.log(Level.WARNING, "Empty description");
                return null;
            }

            String url = articleUrl;
            if (url == null || url.isEmpty()) {
                logger.log(Level.WARNING, "Empty url");
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
                        logger.log(Level.WARNING, "Empty image");
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }

            String imageCaption = article.select("figcaption").text();
            if (imageCaption == null || imageCaption.isEmpty()) {
                logger.log(Level.WARNING, "Empty image copyright");
                //return null;
            }

            String dateString = article.select("time.date-time").attr("datetime");
            if (dateString == null || dateString.isEmpty()) {
                logger.log(Level.WARNING, "Empty date");
                return null;
            }

            String author = article.select("p.info-autor").text();
            if (author == null || author.isEmpty()) {
                logger.log(Level.WARNING, "Empty author");
                author = "Jornal de Angola";
            }

            logger.log(Level.WARNING, "title       : " + title);
            logger.log(Level.WARNING, "description : " + description);
            logger.log(Level.WARNING, "url         : " + url);
            logger.log(Level.WARNING, "image       : " + src);
            logger.log(Level.WARNING, "imageCaption: " + imageCaption);
            logger.log(Level.WARNING, "date        : " + dateString);
            logger.log(Level.WARNING, "author      : " + author);

            return toArticle(title, description, url, src, imageCaption, dateString, author);
        }

        return null;

    }

    private Elements selectAnchorTags(String url) {
        Connection connection = Jsoup.connect(url);
        if (connection != null) {
            try {
                Document document = connection.get();
                Elements anchorTags = document.body().select("a");
                logger.log(Level.WARNING, String.valueOf(anchorTags.size()));
                return anchorTags;
            } catch (IOException ex) {
                Logger.getLogger(BoomJornalDeAngola.class.getName()).log(Level.SEVERE, null, ex);
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
            iox.printStackTrace();
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
        String author = article.select("p.info-autor").text();
        if (author == null || author.isEmpty()) {
            logger.log(Level.WARNING, "Empty author");
            author = "Jornal de Angola";
        }
        return new NewsAuthor(author);
    }

    private NewsSource toSource() {
        // If there is a source corresponding, return it
        NewsSource source = getSourceFromDb("Jornal de Angola");
        // Else create a new one
        if (source == null) {
            source = new NewsSource();
            source.setName("Jornal de Angola");
            source.setSourceId("jornal-de-angola");
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
