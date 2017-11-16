/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers;

import crawlers.exceptions.ApiCallException;
import utils.json.MultipleSourcesResponse;
import utils.json.MultipleArticlesResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.NewsArticle;
import db.NewsAuthor;
import db.NewsSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import utils.json.SingleArticleResponse;
import utils.json.SingleSourceResponse;
import services.NewsArticleService;
import services.NewsArticleServiceInterface;
import services.NewsSourceService;
import services.NewsSourceServiceInterface;
import utils.FlexCrawlerLogger;

/**
 *
 * @author zua
 */
@Singleton
@Startup
public class NewsOrgApiAggregator {

    private final String API_KEY = "5b4e00f3046843138d8368211777a4f2";
    private final String SOURCES_URL = "http://newsapi.org/v1/sources?";
    private final String ARTICLES_URL = "http://newsapi.org/v1/articles?";

    private ObjectMapper objectMapper;

    private NewsArticleServiceInterface articlesService;
    private NewsSourceServiceInterface sourcesService;

    private FlexCrawlerLogger logger;

    public NewsOrgApiAggregator() {
        objectMapper = new ObjectMapper();
        logger = new FlexCrawlerLogger(NewsOrgApiAggregator.class);
        articlesService = new NewsArticleService();
        sourcesService = new NewsSourceService();
    }

    @Schedule(hour = "*", minute = "*/30")
    public void aggregate() {
        try {
            loadAllData();
        } catch (final Exception e) {
            logger.error("Found exception %s", e);
        }
    }

    public String getSourcesQuery(String category, String language2Letter, String country) {
        boolean hasCategory = category != null && !category.isEmpty();
        boolean haslanguage = language2Letter != null && !language2Letter.isEmpty();
        boolean hasCountry = country != null && !country.isEmpty();
        boolean hasApiKey = API_KEY != null && !API_KEY.isEmpty();

        StringBuilder builder = new StringBuilder(SOURCES_URL);

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
            builder.append("source=");
            builder.append(sourceId);
            builder.append("&");
        }

        if (hasApiKey) {
            builder.append("apiKey=");
            builder.append(API_KEY);
        }

        return builder.toString();
    }

    public String makeApiCall(String url) throws ApiCallException {
        try (InputStream is = new URL(url).openConnection().getInputStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            return readAllData(rd);
        } catch (Exception e) {
            logger.error("%s", "Error calling news api..." + e.getMessage());
            throw new ApiCallException(url);
        }
    }

    protected void loadAllData() throws IOException, ApiCallException {
        logger.info("%s", "Start loading data from " + SOURCES_URL);
        String result = makeApiCall(getSourcesQuery(null, null, null));

        MultipleSourcesResponse sourcesResponse = read(result);
        if ("ok".equals(sourcesResponse.getStatus())) {
            for (SingleSourceResponse ssr : sourcesResponse.getSources()) {
                NewsSource source = ssr.convert2NewsSource();
                loadAllArticles(source);
                saveReturnSource(source);
            }
        }
        logger.info("%s", "Finished: " + SOURCES_URL);
    }

    public void loadAllArticles(NewsSource source) {
        try {
            String result = makeApiCall(getArticlesQuery(source.getSourceId()));

            MultipleArticlesResponse articlesResponse = objectMapper.readValue(result, MultipleArticlesResponse.class);
            if ("ok".equals(articlesResponse.getStatus())) {
                logger.info("%s", "Processing source " + source.getName());
                for (SingleArticleResponse sar : articlesResponse.getArticles()) {
                    NewsArticle article = sar.convert2NewsArticle(source);
                    boolean shouldSave = article.getTitle() != null
                            && !article.getTitle().isEmpty()
                            && articlesService.find(article.getTitle()) == null;
                    if (shouldSave) {
                        logger.info("%s", "\tSaved new article " + article.getTitle());
                        NewsAuthor author = sar.convert2NewsAuthor(source);
                        author.addArticle(article);
                        source.addCorrespondent(author);
                    }
                }
            }
        } catch (ApiCallException | IOException ex) {
            logger.error("%s", ex.getClass().getSimpleName());
        }
    }

    /*    protected void saveArticle(NewsArticle article) {
        if (articlesService != null) {
            articlesService.save(article);
        }
    }
     */
    protected NewsSource saveReturnSource(NewsSource source) {
        sourcesService.save(source);
        return sourcesService.find(source);
    }

    private String readAllData(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    void setNewsArticlesService(NewsArticleServiceInterface newsArticleService) {
        articlesService = newsArticleService;
    }

    void setNewsSourcesService(NewsSourceServiceInterface newsSourceService) {
        sourcesService = newsSourceService;
    }

    protected MultipleSourcesResponse read(String result) throws IOException {
        return objectMapper.readValue(result, MultipleSourcesResponse.class);
    }

}
