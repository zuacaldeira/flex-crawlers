/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.aNacaoCv;

import crawlers.publishers.exceptions.ArticlesNotFoundException;
import crawlers.publishers.exceptions.AuthorsNotFoundException;
import crawlers.FlexNewsCrawler;
import crawlers.Logos;
import db.news.NewsSource;
import db.news.Tag;
import db.relationships.TaggedSourceAs;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author zua
 */

public class ANacaoCVCrawler extends FlexNewsCrawler {

    public ANacaoCVCrawler() {
    }

    @Override
    public void crawl() {
        try {
            crawlWebsite(getMySource().getUrl(), getMySource());
        } catch(Exception e) {
            getLogger().error("Exception thrown %s", e.getMessage());
        }
    }

    @Override
    public NewsSource getMySource() {
        String sourceId = "a-nacao";
        String name = "A Nação";
        String description = "";
        String url = "http://anacao.cv/";
        String category = "geral";
        String language = "pt";
        String country = "CV";

        NewsSource source = new NewsSource();
        source.setCountry(country);
        source.setDescription(description);
        source.setLanguage(language);
        source.setLogoUrl(Logos.getLogo(sourceId));
        source.setName(name);
        source.setSourceId(sourceId);
        source.setUrl(url);

        TaggedSourceAs tagged = new TaggedSourceAs();
        tagged.setSource(source);
        tagged.setTag(new Tag(category));

        return source;
    }

    @Override
    protected Elements getArticles(Document document) throws ArticlesNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements articles = document.select("div.masonry_post");
        if (!articles.isEmpty()) {
            getLogger().log("%s %d %s", "Found ", articles.size(), " articles");
            return articles;
        }
        throw new ArticlesNotFoundException();
    }

    @Override
    protected String getUrlValue(Element article) {
        if (article == null) {
            throw new IllegalArgumentException("Article cannot be null");
        }
        Elements urls = article.select("a.link_title");
        if (!urls.isEmpty() && !urls.attr("href").isEmpty()) {
            getLogger().log("%s %s", "Found url ", urls.attr("href"));
            return urls.attr("href");
        }
        return null;
    }

    @Override
    protected String getTitleValue(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements titles = document.select("div.single_title > h1");
        if (!titles.isEmpty() && !titles.text().isEmpty()) {
            getLogger().log("%s %s", "Found Titles ", titles.text());
            return titles.text();
        }
        return null;
    }

    @Override
    protected String getImageUrlValue(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements images = document.select("div.single_thumbnail > a");
        if (!images.isEmpty() && !images.attr("href").isEmpty()) {
            getLogger().log("%s %s", "Found Images ", images.attr("href"));
            return images.attr("href");
        }
        return null;
    }

    @Override
    protected String getContentValue(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements contents = document.select("#single_excerpt_post_title");
        if (!contents.isEmpty() && !contents.text().isEmpty()) {
            getLogger().log("%s %s", "Found content ", contents.text());
            return contents.text();
        }
        return null;
    }

    @Override
    protected String getAuthorsValue(Document document) throws AuthorsNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements paragraphs = document.select("div.single_text > p");
        if (!paragraphs.isEmpty() && paragraphs.last() != null && !paragraphs.last().text().isEmpty()) {
            getLogger().log("%s %s", "Found author", paragraphs.last().text());
            return paragraphs.last().text();
        }
        return getMySource().getName();
    }

    @Override
    protected String getTimeValue(Document document)  {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements times = document.select("div.post_meta_line div.post_time");
        if (!times.isEmpty() && times.first() != null && !times.first().text().isEmpty()) {
            getLogger().log("%s %s", "Found time ", times.first().text());
            return times.first().text();
        }
        return null;
    }
    
    

}
