/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers;

import crawlers.publishers.exceptions.ApiCallException;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.news.NewsArticle;
import db.news.NewsSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import crawlers.utils.CrawlerDBUtils;
import crawlers.utils.FlexCrawlerLogger;
import crawlers.utils.Neo4jSessionFactoryForCrawlers;
import db.news.NewsAuthor;
import json.MultipleArticlesResponse;
import json.MultipleSourcesResponse;
import json.SingleArticleResponse;
import json.SingleSourceResponse;

/**
 *
 * @author zua
 */
public class NewsOrgApiAggregator {

    private final String API_KEY = "a948013d33444ec9bb4ab8409821a3ef";
    private final String SOURCES_URL = "http://newsapi.org/v2/sources";
    private final String ARTICLES_URL = "http://newsapi.org/v2/everything?";

    private ObjectMapper objectMapper;
    private FlexCrawlerLogger logger;
    private final Neo4jSessionFactoryForCrawlers sessionFactory;

    public NewsOrgApiAggregator() {
        objectMapper = new ObjectMapper();
        logger = new FlexCrawlerLogger(NewsOrgApiAggregator.class);
        sessionFactory = Neo4jSessionFactoryForCrawlers.getInstance();
    }

    public void aggregate() {
        try {
            loadAllData();
        } catch (final Exception e) {
            logger.error(String.format("Found exception %s", e));
        }
    }

    public String getSourcesQuery(String category, String language2Letter, String country) {
        boolean hasCategory = category != null && !category.isEmpty();
        boolean haslanguage = language2Letter != null && !language2Letter.isEmpty();
        boolean hasCountry = country != null && !country.isEmpty();
        boolean hasApiKey = API_KEY != null && !API_KEY.isEmpty();

        StringBuilder builder = new StringBuilder(SOURCES_URL);
        if(hasCategory || haslanguage || hasCountry || hasApiKey) {
            builder.append("?");
        }
        
        if (hasCategory) {
            builder.append("category=");
            builder.append(category);
            builder.append("&");
        }

        if (haslanguage) {
            builder.append("language=");
            builder.append(language2Letter);
            builder.append("&");
        }

        if (hasCountry) {
            builder.append("country=");
            builder.append(country);
            builder.append("&");
        }

        if (hasApiKey) {
            builder.append("apiKey=");
            builder.append(API_KEY);
        }

        return builder.toString();
    }

    public String getArticlesQuery(String sourceId) {
        boolean hasSourceId = sourceId != null && !sourceId.isEmpty();
        boolean hasApiKey = API_KEY != null && !API_KEY.isEmpty();

        StringBuilder builder = new StringBuilder(ARTICLES_URL);

        if (hasSourceId) {
            builder.append("sources=");
            builder.append(sourceId);
            builder.append("&");
        }

        if (hasApiKey) {
            builder.append("apiKey=");
            builder.append(API_KEY);
        }

        return builder.toString();
    }

    public String makeApiCall(String url) throws IllegalArgumentException, ApiCallException {
        if (url == null) {
            throw new IllegalArgumentException();
        }
        try (InputStream is = new URL(url).openConnection().getInputStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            return readAllData(rd);
        } catch (Exception e) {
            logger.error(String.format("%s", "Error calling news api..." + e.getMessage()));
            throw new ApiCallException(url);
        }
    }

    protected void loadAllData() throws IOException, ApiCallException {
        logger.info(String.format("%s", "Start loading data from " + SOURCES_URL));
        String result = makeApiCall(getSourcesQuery(null, null, null));
        logger.info(String.format("%s", "Answer: " + result));

        MultipleSourcesResponse sourcesResponse = read(result);
        if ("ok".equals(sourcesResponse.getStatus())) {
            for (SingleSourceResponse ssr : sourcesResponse.getSources()) {
                NewsSource source = ssr.convert2NewsSource();
                if (!source.getSourceId().equals("the-hindu")) {
                    loadAllArticles(source);
                    saveReturnSource(source);
                }
            }
        }
        logger.info(String.format("%s", "Finished: " + SOURCES_URL));
    }

    public void loadAllArticles(NewsSource source) {
        try {
            String result = makeApiCall(getArticlesQuery(source.getSourceId()));

            MultipleArticlesResponse articlesResponse = objectMapper.readValue(result, MultipleArticlesResponse.class);
            if ("ok".equals(articlesResponse.getStatus())) {
                logger.info(String.format("%s", "Processing source " + source.getName()));
                for (SingleArticleResponse sar : articlesResponse.getArticles()) {
                    NewsArticle article = sar.convert2NewsArticle(source);
                    NewsAuthor author = sar.convert2NewsAuthor(source);
                    boolean shouldSave = article.getTitle() != null
                            && !article.getTitle().isEmpty()
                            && notInBd(article.getTitle());
                    if (shouldSave) {
                        source.getAuthors().add(author);
                        author.getAuthored().add(article);
                        logger.info(String.format("%s", "\tSaved new article " + article.getTitle()));
                    }
                }
            }
        } catch (ApiCallException | IOException ex) {
            logger.error(String.format("%s", ex.getClass().getSimpleName()));
        }
    }

    protected NewsSource saveReturnSource(NewsSource source) {
        sessionFactory.getNeo4jSession().save(source);
        String query = "MATCH (s:NewsSource) WHERE s.sourceId='" + source.getSourceId() + "' RETURN s";
        return sessionFactory.getNeo4jSession().queryForObject(NewsSource.class, query, new HashMap<>());
    }

    private String readAllData(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    protected MultipleSourcesResponse read(String result) throws IOException {
        return objectMapper.readValue(result, MultipleSourcesResponse.class);
    }

    private boolean notInBd(String title) {
        String query = "MATCH (n:NewsArticle) WHERE n.title=" + CrawlerDBUtils.getInstance().wrapUp(title) + " RETURN n";
        NewsArticle t = sessionFactory.getNeo4jSession().queryForObject(NewsArticle.class, query, new HashMap<String, Object>());
        return t == null;
    }

}
