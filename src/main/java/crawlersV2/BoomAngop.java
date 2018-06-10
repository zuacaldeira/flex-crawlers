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
public class BoomAngop {

    private final String SOURCES_URL = "http://www.angop.ao/";
    private final String COUNTRY = "AO";
    private final String LANGUAGE = "pt";
    private final Logger LOGGER = Logger.getLogger("Angop");

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
        Elements article = selectArticle(link);
        if (article != null) {
            String title = getArticleTitle(article);
            if (title == null || title.isEmpty()) {
                LOGGER.log(Level.WARNING, "Empty title");
                return false;
            }

            String description = getArticleDescription(article);
            if (description == null || description.isEmpty()) {
                LOGGER.log(Level.WARNING, "Empty description");
                return false;
            }

            String url = link;
            if (url == null || url.isEmpty()) {
                LOGGER.log(Level.WARNING, "Empty url");
                return false;
            }

            String src = getArticleImageSource(article);
            if (src == null || src.isEmpty()) {
                LOGGER.log(Level.WARNING, "Empty image");
                return false;
            }

            String imageCaption = getArticleImageCopyright(article);
            if (imageCaption == null || imageCaption.isEmpty()) {
                LOGGER.log(Level.WARNING, "Empty image copyright");
                //return false;
            }

            String dateString = getArticleDate(article);
            if (dateString == null || dateString.isEmpty()) {
                LOGGER.log(Level.WARNING, "Empty date");
                return false;
            }

            String author = getArticleAuthor(article);
            if (author == null || author.isEmpty()) {
                LOGGER.log(Level.WARNING, "Empty author");
                author = "Angop";
            }

            return true;
        }

        return false;

    }

    public NewsArticle toArticle(String articleUrl) {
        Elements article = selectArticle(articleUrl);
        if (article != null) {
            String title = getArticleTitle(article);
            if (title == null || title.isEmpty()) {
                LOGGER.log(Level.WARNING, "Empty title");
                return null;
            }

            String description = getArticleDescription(article);
            if (description == null || description.isEmpty()) {
                LOGGER.log(Level.WARNING, "Empty description");
                return null;
            }

            String url = articleUrl;
            if (url == null || url.isEmpty()) {
                LOGGER.log(Level.WARNING, "Empty url");
                return null;
            }

            String src = getArticleImageSource(article);
            if (src == null || src.isEmpty()) {
                LOGGER.log(Level.WARNING, "Empty image");
                return null;
            }

            String imageCaption = getArticleImageCopyright(article);
            if (imageCaption == null || imageCaption.isEmpty()) {
                LOGGER.log(Level.WARNING, "Empty image copyright");
                //return null;
            }

            String dateString = getArticleDate(article);
            if (dateString == null || dateString.isEmpty()) {
                LOGGER.log(Level.WARNING, "Empty date");
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
                Document document = connection.get();
                Elements anchorTags = document.body().select("a");
                return anchorTags;
            } catch (IOException ex) {
                Logger.getLogger(BoomAngop.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private Elements selectArticle(String articlePage) {
        Connection connection = Jsoup.connect(articlePage);
        try {
            Document document = connection.get();
            Element body = document.body();
            Elements articles = body.select("article");
            return articles;
        } catch (Exception iox) {
            LOGGER.log(Level.WARNING, iox.getMessage());
        }
        return null;
    }

    private NewsArticle toArticle(String title, String description, String url, String imageUrl, String imageCaption, String dateString, String author) {
        NewsArticle newsArticle = new NewsArticle();
        newsArticle.setTitle(title);
        newsArticle.setDescription(description);
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("pt", "AO"));
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
        Elements article = selectArticle(articleUrl);
        String author = getArticleAuthor(article);
        return new NewsAuthor(author);
    }

    private NewsSource toSource() {
        // If there is a source corresponding to IOL SA, return it
        NewsSource source = getSourceFromDb("Angop");
        // Else create a new one
        if(source == null){
            source = new NewsSource();
            source.setName("Angop");
            source.setSourceId("angop");
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

    private String getArticleTitle(Elements article) {
        Elements h1s = article.select("div.box-titulo h2");
        if(h1s != null && !h1s.isEmpty()) {
            Element first = h1s.first();
            if(first != null) {
                return first.text();
            }
        }
        return null;
    }

    private String getArticleAuthor(Elements article) {
        String author = article.select("p.info-autor").text();
        if (author == null || author.isEmpty()) {
            LOGGER.log(Level.WARNING, "Empty author");
            author = "Angop";
        }
        return author;
    }

    private String getArticleDate(Elements article) {
        return article.select("div.box-titulo > p > strong").attr("datetime");        
    }

    private String getArticleDescription(Elements article) {
        return article.select("div.box-titulo h3").text();
    }

    private String getArticleImageSource(Elements article) {
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

    private Elements getArticleImages(Elements article) {
        return article.select("div.foto-galeria > img.fl, div.texto-noticia:first-child > div.img-noticia > img.fl");
    }

    private String getArticleImageCopyright(Elements article) {
        return article.select("div.legenda-foto").text();
    }

}
