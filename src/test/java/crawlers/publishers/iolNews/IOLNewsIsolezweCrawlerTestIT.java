/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.iolNews;

import crawlers.publishers.iolNews.IOLNewsIsolezweCrawler;
import crawlers.AbstractCrawlerTestIT;
import crawlers.FlexNewsCrawler;
import crawlers.publishers.exceptions.ContentNotFoundException;
import org.junit.Test;

/**
 *
 * @author zua
 */
public class IOLNewsIsolezweCrawlerTestIT extends AbstractCrawlerTestIT {

    public IOLNewsIsolezweCrawlerTestIT() {
    }

    @Override
    protected FlexNewsCrawler getCrawler() {
        return new IOLNewsIsolezweCrawler();
    }

}