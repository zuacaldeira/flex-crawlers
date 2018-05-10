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
public class BoomNovoJornal {

    private final String SOURCES_URL = "http://novojornal.co.ao/";
    private final String SOURCE_ID = "novo-jornal";
    private final String SOURCE_NAME = "Novo Jornal";
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
                connection.validateTLSCertificates(false);
                Document document = connection.get();
                Elements anchorTags = document.body().select("a");
                System.out.println(anchorTags.size());
                return anchorTags;
            } catch (IOException ex) {
                Logger.getLogger(BoomNovoJornal.class.getName()).log(Level.SEVERE, null, ex);
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
            Elements articles = body.select("div.article-with-photo-metadata");
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
            SimpleDateFormat format = new SimpleDateFormat("dd 'de' MMMM yyyy HH:mm", new Locale("pt", "AO"));
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
        String author = article.select("div.author-full-name").text();
        if (author == null || author.isEmpty()) {
            System.out.println("Empty author");
            author = SOURCE_NAME;
        }
        return author;
    }

    private String getArticleDate(Element article) {
        String date = article.select("div.published").first().text().trim();
        date = date.replace("\u00a0", " ");
        String time = article.select("div.last-updated").text().trim();
        time = time.replace("Actualizado às ", "");
        System.out.println("Time = " + time);
        String fulldate = date + " " + time;
        System.out.println("Full date = " + fulldate);
        return  fulldate;
    }

    private String getArticleDescription(Element article) {
        String text = article.select("div.lead").text();
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
        String selector1 = "figure img";
        Elements images1 = article.select(selector1);
        if(images1 != null && !images1.isEmpty()) {
            return images1;
        }

        String selector2 = "figure > div > div > div.galleria-stage > div.galleria-images > div:nth-child(2) > img";
        Elements images2 = article.select(selector2);
        if(images2 != null && !images2.isEmpty()) {
            return images2;
        }
                
        return null;
    }

    private String getArticleImageCopyright(Element article) {
        return article.select("figure > figcaption").text();
    }

}
