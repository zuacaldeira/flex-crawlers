/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlersV2;

import backend.services.news.NewsArticleService;
import backend.services.news.NewsSourceService;
import crawlers.Logos;
import db.news.NewsArticle;
import db.news.NewsAuthor;
import db.news.NewsSource;
import db.news.Tag;
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

/**
 *
 * @author zua
 */
public class BoomCorreioAngolense {

    private final String SOURCES_URL = "https://www.correioangolense.com/";
    private final String SOURCE_ID = "correio-angolense";
    private final String SOURCE_NAME = "Correio Angolense";
    private final String COUNTRY = "AO";
    private final String LANGUAGE = "pt";

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
        System.out.println("---------------------------------");
        Element article = selectArticle(link);
        if (article != null) {
            String title = getArticleTitle(article);
            if (title == null || title.isEmpty()) {
                System.out.println("Empty title");
                return false;
            }

            String description = getArticleDescription(article);
            if (description == null || description.isEmpty()) {
                System.out.println("Empty description");
                return false;
            }

            String url = link;
            if (url == null || url.isEmpty()) {
                System.out.println("Empty url");
                return false;
            }

            String src = getArticleImageSource(article);
            if (src == null || src.isEmpty()) {
                System.out.println("Empty image");
                return false;
            }

            String imageCaption = getArticleImageCopyright(article);
            if (imageCaption == null || imageCaption.isEmpty()) {
                System.out.println("Empty image copyright");
                //return false;
            }

            String dateString = getArticleDate(article);
            if (dateString == null || dateString.isEmpty()) {
                System.out.println("Empty date");
                return false;
            }

            String author = getArticleAuthor(article);
            return true;
        }

        return false;

    }

    public NewsArticle toArticle(String articleUrl) {
        Element article = selectArticle(articleUrl);
        if (article != null) {
            String title = getArticleTitle(article);
            if (title == null || title.isEmpty()) {
                System.out.println("Empty title");
                return null;
            }

            String description = getArticleDescription(article);
            if (description == null || description.isEmpty()) {
                System.out.println("Empty description");
                return null;
            }

            String url = articleUrl;
            if (url == null || url.isEmpty()) {
                System.out.println("Empty url");
                return null;
            }

            String src = getArticleImageSource(article);
            if (src == null || src.isEmpty()) {
                System.out.println("Empty image");
                return null;
            }

            String imageCaption = getArticleImageCopyright(article);
            if (imageCaption == null || imageCaption.isEmpty()) {
                System.out.println("Empty image copyright");
                //return null;
            }

            String dateString = getArticleDate(article);
            if (dateString == null || dateString.isEmpty()) {
                System.out.println("Empty date");
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
                System.out.println(anchorTags.size());
                return anchorTags;
            } catch (IOException ex) {
                Logger.getLogger(BoomCorreioAngolense.class.getName()).log(Level.SEVERE, null, ex);
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
            Elements articles = body.select("div.single-post");
            String type = document.head().select("meta[property=og:type]").attr("content");
            if(type != null && type.equals("article")) {
                return articles.first();
            }
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
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("pt", "AO"));
            newsArticle.setPublishedAt(format.parse(dateString));
        } catch (ParseException ex) {
            System.err.println("Could't parse date string");
            return null;
        }
        newsArticle.setUrl(url);
        newsArticle.setImageUrl(imageUrl);
        newsArticle.setCountry(COUNTRY);
        newsArticle.setLanguage(LANGUAGE);
        newsArticle.setSourceId(SOURCE_ID);
        return newsArticle;
    }
    
    public NewsAuthor toAuthor(String articleUrl) {
        Element article = selectArticle(articleUrl);
        String author = getArticleAuthor(article);
        if(author != null) {
            return new NewsAuthor(author);
        }
        else {
            return new NewsAuthor(SOURCE_NAME);
        }
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
        System.out.println("SOURCE NAME -> " + source.getName());
        System.out.println("AUTHOR NAME -> " + author.getName());
        new NewsSourceService().save(source);
    }

    private boolean inDb(String next) {
        return new NewsArticleService().findArticlesWithUrl(next).iterator().hasNext();
    }

    private String getArticleTitle(Element article) {
        Elements titles = article.select("div.content > h3");
        if(titles != null && !titles.isEmpty()) {
            Element title = titles.first();
            if(title != null) {
                System.out.println("Found title " + title);
                return title.text();
            }
        }
        return null;
    }
    
    private String getArticleDescription(Element article) {
        Elements options = article.select("div>div>div>p>span");
        Element first = options.first();
        if(first != null) {
            String text = first.text();
            if(text != null && !text.isEmpty()) {
                System.out.println("Found description: " + text);
                return text;
            }
        }
        return null;
    }

    private String getArticleAuthor(Element article) {
        Elements authors = article.select("div.article-author h3");
        if(authors != null && !authors.isEmpty()) {
            Element author = authors.first();
            if(author != null) {
                System.out.println("Found author " + author);
                return author.text();
            }
        }
        return null;
    }

    private String getArticleDate(Element article) {
        Document document = getDocument(article);
        return document.select("head meta[property=article:published_time]").attr("content");
    }

    private String getArticleImageSource(Element article) {
        Elements images = getArticleImages(article);
        Element first = null;
        if (images != null && !images.isEmpty()) {
            first = images.first();
            if (first != null) {
                String src = first.attr("src");
                System.out.println("Found image " + src);
                return src;
            }
        }
        return null;
    }

    private Elements getArticleImages(Element article) {
        String selector1 = "div.img-container > img";
        Elements images1 = article.select(selector1);
        if(images1 != null && !images1.isEmpty()) {
            return images1;
        }
        return null;
    }

    private String getArticleImageCopyright(Element article) {
        return article.select("figure > figcaption").text();
    }

    private Document getDocument(Element article) {
        if(article instanceof Document) {
            return (Document) article;
        }
        else {
            return getDocument(article.parent());
        }
    }

}
