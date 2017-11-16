/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers;

import crawlers.exceptions.DocumentNotFoundException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Ignore;
import org.junit.Test;
import services.NewsArticleService;
import services.NewsSourceService;

/**
 *
 * @author zua
 */
public abstract class AbstractCrawlerTestIT {
    
    protected abstract FlexNewsCrawler getCrawler();

    /**
     * Test of crawlWebsite method, of class TheBugleZACrawler.
     */
    @Test
    public void testCrawl() throws Exception {
        System.out.println("crawl");
        FlexNewsCrawler crawler = getCrawler();
        crawler.setArticlesService(new NewsArticleService());
        crawler.setSourcesService(new NewsSourceService());
        crawler.crawl();
    }
    
    public void testCrawlWebsite() {
        FlexNewsCrawler crawler = getCrawler();
        crawler.setArticlesService(new NewsArticleService());
        crawler.setSourcesService(new NewsSourceService());
        crawler.crawlWebsite("", crawler.getMySource());
    }
    
    @Test
    @Ignore
    public void testImportArticle() {
        System.out.println("importArticle");
        FlexNewsCrawler crawler = getCrawler();
        Document document = crawler.openDocument(crawler.getMySource().getUrl());
        Elements articles = crawler.getArticles(document);
        if(!articles.isEmpty()) {
            crawler.importArticle(articles.first(), crawler.getMySource());
        }
    }


}
