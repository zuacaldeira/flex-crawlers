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

@Stateless public class GlobalVoicesCrawlerTET extends GlobalVoicesAbstractCrawler {

    public GlobalVoicesCrawlerTET() {
    }

    @Override
    public NewsSource getMySource() {
        String sourceId = "global-voices-tet";
        String name = "Global Voices (TET)";
        String description = "";
        String url = "https://tet.globalvoices.org";
        String category = "general";
        String language = "tet";
        String country = "TL";

        NewsSource source = new NewsSource(sourceId, name, description, url, category, language, country);
        source.setLogoUrl(Logos.getLogo("global-voices"));

        return source;
    }

    @Override
    
    public void crawl() {
        super.crawl(); //To change body of generated methods, choose Tools | Templates.
    }
}
