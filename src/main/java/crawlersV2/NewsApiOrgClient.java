/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlersV2;

import com.fasterxml.jackson.databind.ObjectMapper;
import crawlers.utils.FlexCrawlerLogger;
import db.news.NewsArticle;
import db.news.NewsSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.MultipleArticlesResponse;
import json.MultipleSourcesResponse;
import json.SingleArticleResponse;
import json.SingleSourceResponse;

/**
 *
 * @author zua
 */
public class NewsApiOrgClient {

    private final String API_KEY = "a948013d33444ec9bb4ab8409821a3ef";
    private final String SOURCES_URL = "http://newsapi.org/v2/sources";
    private final String ARTICLES_URL = "http://newsapi.org/v2/everything?from=2018-04-25&apiKey=" + API_KEY;

    private final FlexCrawlerLogger logger = new FlexCrawlerLogger(getClass());

    public String requestApiSources() {
        String url = SOURCES_URL + "?apiKey=" + API_KEY;
        logger.info("Calling service... " + url);
        try (InputStream is = new URL(url).openConnection().getInputStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            return readAllData(rd);
        } catch (Exception e) {
            logger.error(String.format("%s", "Error calling news api..." + e.getMessage()));
            return null;
        }
    }

    public TreeSet<NewsSource> loadSources() {
        String apiSources = requestApiSources();
        if (apiSources != null) {
            try {
                MultipleSourcesResponse multipleSources = jsoToMultipleSourcesResponse(apiSources);
                return toTreeSet(multipleSources);
            } catch (IOException ex) {
                Logger.getLogger(NewsApiOrgClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new TreeSet<>();
    }

    protected MultipleSourcesResponse jsoToMultipleSourcesResponse(String json) throws IOException {
        return new ObjectMapper().readValue(json, MultipleSourcesResponse.class);
    }

    public String loadArticles() {
        String url = ARTICLES_URL;
        logger.info("Calling service... " + url);
        try (InputStream is = new URL(url).openConnection().getInputStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            return readAllData(rd);
        } catch (Exception e) {
            logger.error(String.format("%s", "Error calling news api..." + e.getMessage()));
            throw new RuntimeException(url);
        }
    }

    private String readAllData(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private TreeSet<NewsSource> toTreeSet(MultipleSourcesResponse multipleSources) {
        TreeSet<NewsSource> set = new TreeSet<NewsSource>();
        if (multipleSources.getStatus().equals("ok")) {
            Iterator<SingleSourceResponse> it = multipleSources.getSources().iterator();
            while (it.hasNext()) {
                SingleSourceResponse single = it.next();
                set.add(single.convert2NewsSource());
            }
        }
        return set;
    }

    public TreeSet<NewsArticle> loadArticlesFromSource(NewsSource source) {
        try {
            String apiArticles = requestApiArticles(source);
            MultipleArticlesResponse multipleArticles = jsoToMultipleArticlesResponse(apiArticles);
            return toTreeSet(multipleArticles, source);
        } catch (IOException ex) {
            Logger.getLogger(NewsApiOrgClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new TreeSet<>();
    }

    private String requestApiArticles(NewsSource source) {
        String url = "http://newsapi.org/v2/everything?sources=" + source.getSourceId() + "&apiKey=" + API_KEY;
        logger.info("Calling articles service... " + url);
        try (InputStream is = new URL(url).openConnection().getInputStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            return readAllData(rd);
        } catch (Exception e) {
            logger.error(String.format("%s", "Error calling news api..." + e.getMessage()));
            throw new RuntimeException(url);
        }
    }

    private MultipleArticlesResponse jsoToMultipleArticlesResponse(String json) throws IOException {
        return new ObjectMapper().readValue(json, MultipleArticlesResponse.class);
    }

    private TreeSet<NewsArticle> toTreeSet(MultipleArticlesResponse multipleArticles, NewsSource source) {
        TreeSet<NewsArticle> set = new TreeSet<NewsArticle>();
        if (multipleArticles.getStatus().equals("ok")) {
            Iterator<SingleArticleResponse> it = multipleArticles.getArticles().iterator();
            while (it.hasNext()) {
                SingleArticleResponse single = it.next();
                set.add(single.convert2NewsArticle(source));
            }
        }
        return set;
    }

}
