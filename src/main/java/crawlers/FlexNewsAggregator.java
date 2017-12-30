/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers;

import crawlers.publishers.aNacaoCv.ANacaoCVCrawler;
import crawlers.publishers.aVerdadeOnline.AVerdadeOnlineCrawler;
import crawlers.publishers.dn.DiarioDeNoticiasCrawler;
import crawlers.publishers.iolNews.IOLNewsIsolezweCrawler;
import crawlers.publishers.iolNews.IOLNewsZACrawler;
import crawlers.publishers.jornalDeAngola.JornalDeAngolaCrawler;
import crawlers.publishers.makaAngola.MakaAngolaCrawler;
import crawlers.publishers.telaNon.TelaNonCrawler;
import crawlers.publishers.theBugleZa.TheBugleZACrawler;

/**
 *
 * @author zua
 */
public class FlexNewsAggregator {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void aggregate() {
        try {
            // Our crawlers and aggregators of crawlers 
            new ANacaoCVCrawler().crawl();
            new AVerdadeOnlineCrawler().crawl();
            new DiarioDeNoticiasCrawler().crawl();
            new IOLNewsIsolezweCrawler().crawl();
            new IOLNewsZACrawler().crawl();
            new JornalDeAngolaCrawler().crawl();
            new MakaAngolaCrawler().crawl();
            new TelaNonCrawler().crawl();
            new TheBugleZACrawler().crawl();
        } catch (Throwable t) {
        }
    }
}
