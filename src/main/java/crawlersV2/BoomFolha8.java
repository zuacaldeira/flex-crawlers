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
import java.text.SimpleDateFormat;
import java.util.ListIterator;
import java.util.Locale;
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

/**
 *
 * @author zua
 */
public class BoomFolha8 {

    private final String SOURCES_URL = "http://jornalf8.net/";
    private final String SOURCE_ID = "folha8";
    private final String SOURCE_NAME = "Folha 8";
    private final String COUNTRY = "AO";
    private final String LANGUAGE = "pt";
    private final Logger logger = Logger.getLogger("Folha8");

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
        System.out.println("---------------------------------");
        Element article = selectArticle(link);
        if (article != null) {
            String title = getArticleTitle(article);
            if (title == null || title.isEmpty()) {
                logger.log(Level.WARNING, "Empty title");
                return false;
            }

            String description = getArticleDescription(article);
            if (description == null || description.isEmpty()) {
                logger.log(Level.WARNING, "Empty description");
                return false;
            }

            String url = link;
            if (url == null || url.isEmpty()) {
                logger.log(Level.WARNING, "Empty url");
                return false;
            }

            String src = getArticleImageSource(article);
            if (src == null || src.isEmpty()) {
                logger.log(Level.WARNING, "Empty image");
                return false;
            }

            String imageCaption = getArticleImageCopyright(article);
            if (imageCaption == null || imageCaption.isEmpty()) {
                logger.log(Level.WARNING, "Empty image copyright");
                //return false;
            }

            String dateString = getArticleDate(article);
            if (dateString == null || dateString.isEmpty()) {
                logger.log(Level.WARNING, "Empty date");
                return false;
            }

            getArticleAuthor(article);
            return true;
        }

        return false;

    }

    public NewsArticle toArticle(String articleUrl) {
        Element article = selectArticle(articleUrl);
        if (article != null) {
            String title = getArticleTitle(article);
            if (title == null || title.isEmpty()) {
                logger.log(Level.WARNING, "Empty title");
                return null;
            }

            String description = getArticleDescription(article);
            if (description == null || description.isEmpty()) {
                logger.log(Level.WARNING, "Empty description");
                return null;
            }

            String url = articleUrl;
            if (url == null || url.isEmpty()) {
                logger.log(Level.WARNING, "Empty url");
                return null;
            }

            String src = getArticleImageSource(article);
            if (src == null || src.isEmpty()) {
                logger.log(Level.WARNING, "Empty image");
                return null;
            }

            String imageCaption = getArticleImageCopyright(article);
            if (imageCaption == null || imageCaption.isEmpty()) {
                logger.log(Level.WARNING, "Empty image copyright");
                //return null;
            }

            String dateString = getArticleDate(article);
            if (dateString == null || dateString.isEmpty()) {
                logger.log(Level.WARNING, "Empty date");
                return null;
            }

            String author = getArticleAuthor(article);            
            return toArticle(title, description, url, src, imageCaption, dateString, author);
        }

        return null;

    }

    private Elements selectAnchorTags(String url) {
        Connection connection = Jsoup.connect(url);
        if (connection != null) {
            try {
                connection.validateTLSCertificates(false);
                Document document = connection.get();
                Elements anchorTags = document.body().select("a");
                return anchorTags;
            } catch (IOException ex) {
                Logger.getLogger(BoomFolha8.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private Element selectArticle(String articlePage) {
        Connection connection = Jsoup.connect(articlePage);
        try {
            connection.validateTLSCertificates(false);
            Document document = connection.get();
            Element body = document.body();
            Elements articles = body.select("div>article");
            return articles.first();
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
            SimpleDateFormat format = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "AO"));
            newsArticle.setPublishedAt(format.parse(dateString));
        } catch (ParseException ex) {
            System.err.println("Could't parse date string");
            return null;
        }
        newsArticle.setUrl(url);
        newsArticle.setImageUrl(imageUrl);
        newsArticle.setCountry(COUNTRY);
        newsArticle.setLanguage(LANGUAGE);
        return newsArticle;
    }
    
    public NewsAuthor toAuthor(String articleUrl) {
        Element article = selectArticle(articleUrl);
        String author = getArticleAuthor(article);
        return new NewsAuthor(author);
    }

    private NewsSource toSource() {
        // If there is a source corresponding to IOL SA, return it
        NewsSource source = getSourceFromDb(SOURCE_NAME);
        // Else create a new one
        if(source == null){
            source = new NewsSource();
            source.setName(SOURCE_NAME);
            source.setSourceId(SOURCE_ID);
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

    private String getArticleTitle(Element article) {
        Elements titles = article.select("h1.title");
        if(titles != null && !titles.isEmpty()) {
            Element title = titles.first();
            if(title != null) {
                return title.text();
            }
        }
        return null;
    }

    private String getArticleAuthor(Element article) {
        String author = article.select("a.author-name").first().text();
        if (author == null || author.isEmpty()) {
            logger.log(Level.WARNING, "Empty author");
            author = SOURCE_NAME;
        }
        return author;
    }

    private String getArticleDate(Element article) {
        return  article.select("a.post-date").first().text();
    }

    private String getArticleDescription(Element article) {
        String text = article.select("div.post-content > h2").text();
        return text;
    }

    private String getArticleImageSource(Element article) {
        Elements images = getArticleImages(article);
        Element first = null;
        if (images != null && !images.isEmpty()) {
            first = images.first();
            if (first != null) {
                return first.attr("src");
            }
        }
        return null;
    }

    private Elements getArticleImages(Element article) {
        return article.select("div.post-thumbnail-wrapper img");
    }

    private String getArticleImageCopyright(Element article) {
        return null;
    }

}
