/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.theBugleZa;

import crawlers.FlexNewsCrawler;
import crawlers.Logos;
import crawlers.exceptions.TimeNotFoundException;
import crawlers.exceptions.ImageNotFoundException;
import crawlers.exceptions.TitleNotFoundException;
import crawlers.exceptions.UrlNotFoundException;
import crawlers.exceptions.AuthorsNotFoundException;
import crawlers.exceptions.ContentNotFoundException;
import crawlers.exceptions.ArticlesNotFoundException;
import db.NewsSource;
import javax.ejb.Stateless;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author zua
 */

@Stateless public class TheBugleZACrawler extends FlexNewsCrawler {

    public TheBugleZACrawler() {
        super();
    }

    private String getUrl() {
        return "http://thebugle.co.za/home.php";
    }

    @Override
    
    public void crawl() {
        try {
            crawlWebsite(getUrl(), getMySource());
        } catch(Exception e) {
            getLogger().error("Exception thrown: %s", e.getMessage());
        }
    }

    @Override
    public NewsSource getMySource() {
        String sourceId = "the-bugle";
        String name = "The Bugle";
        String description
                = "The Bugle is a tabloid format weekly community magazine that has been in circulation for 17 years and has a print order of 40 000 copies per week.\n"
                + "\n"
                + "The Bugle is distributed house to house between Umgeni River in Durban North and Tugela River on the North Coast, comprising the Sugar Coast and Dolphin Coast. Including Westville, Morningside, Durban Central, Berea, Durban North, Morningside, Berea, Upper and Lower La Lucia, Upper and Lower Umhlanga, Sunningdale, Prestondale, Glen Anil, Mt. Edgecombe, Glenashley, La Mercy, Mt Moreland, Umdloti, Tongaat, Salt Rock, Ballito,Umhlali, Shakaskraal, Stanger, Zinkwazi and Blythedale.";
        String url = getUrl();
        String category = "lifestyle";
        String language = "en";
        String country = "ZA";

        NewsSource source = new NewsSource(sourceId, name, description, url, category, language, country);
        source.setLogoUrl(Logos.getLogo(sourceId));

        return source;
    }

    @Override
    protected Elements getArticles(Document document) throws ArticlesNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements articles = document.select("div.post");
        if (!articles.isEmpty()) {
            return articles;
        }
        throw new ArticlesNotFoundException();
    }

    @Override
    protected String getUrlValue(Element article) throws UrlNotFoundException {
        if (article == null) {
            throw new IllegalArgumentException("Article cannot be null.");
        }
        Elements links = article.select("a");
        if (!links.isEmpty() && links.first() != null && !links.first().absUrl("href").isEmpty()) {
            return links.first().absUrl("href");
        }
        throw new UrlNotFoundException();
    }

    @Override
    protected String getTitleValue(Document document) throws TitleNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements elements = document.select("body h1.title");
        if(!elements.isEmpty() && !elements.text().isEmpty()) {
            return elements.text();
        }
        throw new TitleNotFoundException();
    }

    @Override
    protected String getImageUrlValue(Document document) throws ImageNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements images = document.select("div.top > div.image > a > img[src]");
        if (!images.isEmpty() && images.first() != null && !images.first().absUrl("src").isEmpty()) {
            getLogger().log("%s", "Found image " + images.first().absUrl("src"));
            return images.first().absUrl("src");
        }
        throw new ImageNotFoundException();
    }

    @Override
    protected String getContentValue(Document document) throws ContentNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements contents = document.select("div.body.wrap-text > p");
        if(!contents.isEmpty() && contents.first() != null && !contents.first().text().isEmpty()) {
            return contents.first().text();
        }
        throw new ContentNotFoundException();
    }

    @Override
    protected String getAuthorsValue(Document document) throws AuthorsNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements times = document.select("div.info > b");
        if (!times.isEmpty()) {
            Element time = null;
            if(times.size() == 2) {
                time = times.get(0);
            }
            if(time != null && !time.text().isEmpty()) {
                return time.text();
            }
        }
        throw new AuthorsNotFoundException();
    }

    @Override
    protected String getTimeValue(Document document) throws TimeNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements times = document.select("div.info > b");
        if (!times.isEmpty()) {
            Element time = null;
            if(times.size() == 1) {
                time = times.get(0);
            }
            else{
                time = times.get(1);
            }
            if(time != null && !time.text().isEmpty()) {
                return time.text();
            }
        }
        throw new TimeNotFoundException();
    }
}
