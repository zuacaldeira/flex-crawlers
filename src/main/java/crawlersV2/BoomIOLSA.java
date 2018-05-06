/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlersV2;

import backend.services.news.NewsArticleService;
import backend.services.news.NewsSourceService;
import backend.utils.MyDateUtils;
import crawlers.Logos;
import db.news.NewsArticle;
import db.news.NewsAuthor;
import db.news.NewsSource;
import db.news.Tag;
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

/**
 *
 * @author zua
 */
public class BoomIOLSA {

    private final String SOURCES_URL = "https://www.iol.co.za/";
    private final String COUNTRY = "ZA";
    private final String LANGUAGE = "en";

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
        Elements article = selectArticle(link);
        if (article != null) {
            String title = article.select("meta[itemprop=headline]").attr("content");
            if (title == null || title.isEmpty()) {
                System.out.println("Empty title");
                return false;
            }

            String description = article.select("meta[itemprop=description]").attr("content");
            if (description == null || description.isEmpty()) {
                System.out.println("Empty description");
                return false;
            }

            String url = link;
            if (url == null || url.isEmpty()) {
                System.out.println("Empty url");
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
                        System.out.println("Empty image");
                        return false;
                    }
                }

            }

            String imageCaption = article.select("span.imageCaption").text();
            if (imageCaption == null || imageCaption.isEmpty()) {
                System.out.println("Empty image copyright");
                return false;
            }

            String dateString = article.select("p.meta span[itemprop=datePublished]").attr("content");
            if (dateString == null || dateString.isEmpty()) {
                System.out.println("Empty date");
                return false;
            }

            String author = article.select("p.meta span[itemprop=author] strong[itemprop=name]").text();
            if (author == null || author.isEmpty()) {
                System.out.println("Empty author");
                return false;
            }

            System.out.println("title       : " + title);
            System.out.println("description : " + description);
            System.out.println("url         : " + url);
            System.out.println("image       : " + src);
            System.out.println("imageCaption: " + imageCaption);
            System.out.println("date        : " + dateString);
            System.out.println("author      : " + author);

            return true;
        }

        return false;

    }

    public NewsArticle toArticle(String articleUrl) {
        Elements article = selectArticle(articleUrl);
        if (article != null) {
            String title = article.select("meta[itemprop=headline]").attr("content");
            if (title == null || title.isEmpty()) {
                System.out.println("Empty title");
                return null;
            }

            String description = article.select("meta[itemprop=description]").attr("content");
            if (description == null || description.isEmpty()) {
                System.out.println("Empty description");
                return null;
            }

            String url = articleUrl;
            if (url == null || url.isEmpty()) {
                System.out.println("Empty url");
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
                        System.out.println("Empty image");
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }

            String imageCaption = article.select("span.imageCaption").text();
            if (imageCaption == null || imageCaption.isEmpty()) {
                System.out.println("Empty image copyright");
                return null;
            }

            String dateString = article.select("p.meta span[itemprop=datePublished]").attr("content");
            if (dateString == null || dateString.isEmpty()) {
                System.out.println("Empty date");
                return null;
            }

            String author = article.select("p.meta span[itemprop=author] strong[itemprop=name]").text();
            if (author == null || author.isEmpty()) {
                System.out.println("Empty author");
                return null;
            }

            System.out.println("title       : " + title);
            System.out.println("description : " + description);
            System.out.println("url         : " + url);
            System.out.println("image       : " + src);
            System.out.println("imageCaption: " + imageCaption);
            System.out.println("date        : " + dateString);
            System.out.println("author      : " + author);

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
                System.out.println(anchorTags.size());
                return anchorTags;
            } catch (IOException ex) {
                Logger.getLogger(BoomIOLSA.class.getName()).log(Level.SEVERE, null, ex);
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
        newsArticle.setSourceId("iol-news-za");
        return newsArticle;
    }
    
    public NewsAuthor toAuthor(String articleUrl) {
        Elements article = selectArticle(articleUrl);
        String author = article.select("p.meta span[itemprop=author] strong[itemprop=name]").text();
        if (author == null || author.isEmpty()) {
            System.out.println("Empty author");
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
            source.setCategory(new Tag("general"));
        }
        
        return source;
    }

    private NewsSource getSourceFromDb(String name) {
        NewsSourceService service = new NewsSourceService();
        return service.findSourceNamed(name);
    }

    private void store(NewsArticle newsArticle, NewsAuthor author, NewsSource source) {
        author.getAuthored().add(newsArticle);
        source.getAuthors().add(author);
        new NewsSourceService().save(source);
    }

    private boolean inDb(String next) {
        return new NewsArticleService().findArticlesWithUrl(next).iterator().hasNext();
    }

}
