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
        } catch (Exception e) {
            getLogger().error(String.format("Exception thrown %s", e.getMessage()));
        }
    }

    @Override
    public NewsSource getMySource() {
        String sourceId = "a-nacao";
        String name = "A Nação";
        String description = "";
        String url = "http://anacao.cv";
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
    public Elements getArticles(Document document) throws ArticlesNotFoundException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements articles = document.select("div.masonry_post");
        if (!articles.isEmpty()) {
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
            return paragraphs.last().text();
        }
        return getMySource().getName();
    }

    @Override
    protected String getTimeValue(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        Elements times = document.select("div.post_meta_line div.post_time");
        if (!times.isEmpty() && times.first() != null && !times.first().text().isEmpty()) {
            return times.first().text();
        }
        return null;
    }

}
