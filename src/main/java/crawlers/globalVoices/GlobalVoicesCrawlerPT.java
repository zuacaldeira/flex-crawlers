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

@Stateless public class GlobalVoicesCrawlerPT extends GlobalVoicesAbstractCrawler {

    public GlobalVoicesCrawlerPT() {
    }

    @Override
    public NewsSource getMySource() {
        String sourceId = "global-voices-pt";
        String name = "Global Voices (PT)";
        String description = "";
        String url = "https://pt.globalvoices.org";
        String category = "general";
        String language = "pt";
        String country = "PT";

        NewsSource source = new NewsSource(sourceId, name, description, url, category, language, country);
        source.setLogoUrl(Logos.getLogo("global-voices"));

        return source;
    }

    @Override
    
    public void crawl() {
        super.crawl(); //To change body of generated methods, choose Tools | Templates.
    }
}
