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

 public class GlobalVoicesCrawlerSV extends GlobalVoicesAbstractCrawler {

    public GlobalVoicesCrawlerSV() {
    }

    @Override
    public NewsSource getMySource() {
        String sourceId = "global-voices-sv";
        String name = "Global Voices (SV)";
        String description = "";
        String url = "https://sv.globalvoices.org";
        String category = "general";
        String language = "sv";
        String country = "SE";

        NewsSource source = new NewsSource();
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
