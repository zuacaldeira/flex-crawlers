/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.globalVoices;

import crawlers.Logos;
import db.news.NewsSource;


/**
 *
 * @author zua
 */

 public class GlobalVoicesCrawlerUR extends GlobalVoicesAbstractCrawler {

    public GlobalVoicesCrawlerUR() {
    }

    @Override
    public NewsSource getMySource() {
        String sourceId = "global-voices-ur";
        String name = "Global Voices (UR)";
        String description = "";
        String url = "https://ur.globalvoices.org";
        String category = "general";
        String language = "ur";
        String country = "IN";

        db.news.NewsSource source = new db.news.NewsSource();
        source.setCategory(category);
        source.setCountry(country);
        source.setDescription(description);
        source.setLanguage(language);
        source.setLogoUrl(Logos.getLogo(sourceId));
        source.setName(name);
        source.setSourceId(sourceId);
        source.setUrl(url);

        source.setLogoUrl(Logos.getLogo("global-voices"));
        return source;
    }

    @Override
    
    public void crawl() {
        super.crawl(); //To change body of generated methods, choose Tools | Templates.
    }
}
