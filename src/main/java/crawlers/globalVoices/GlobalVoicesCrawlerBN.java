/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.globalVoices;

import crawlers.Logos;
import db.NewsSource;
import javax.ejb.Stateless;

/**
 *
 * @author zua
 */

@Stateless public class GlobalVoicesCrawlerBN extends GlobalVoicesAbstractCrawler {

    public GlobalVoicesCrawlerBN() {
    }

    @Override
    public NewsSource getMySource() {
        String sourceId = "global-voices-bn";
        String name = "Global Voices (BN)";
        String description = "";
        String url = "https://bn.globalvoices.org";
        String category = "general";
        String language = "bn";
        String country = "BD";

        NewsSource source = new NewsSource(sourceId, name, description, url, category, language, country);
        source.setLogoUrl(Logos.getLogo("global-voices"));

        return source;
    }

    @Override
    
    public void crawl() {
        super.crawl(); //To change body of generated methods, choose Tools | Templates.
    }
}
