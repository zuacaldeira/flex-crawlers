/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.globalVoices;

import crawlers.Logos;
import db.news.NewsSource;
import db.news.Tag;
import db.relationships.TaggedSourceAs;


/**
 *
 * @author zua
 */

 public class GlobalVoicesCrawlerEN extends GlobalVoicesAbstractCrawler {

    public GlobalVoicesCrawlerEN() {
    }

    @Override
    public NewsSource getMySource() {
        String sourceId = "global-voices-en";
        String name = "Global Voices (EN)";
        String description = "";
        String url = "https://en.globalvoices.org";
        String category = "general";
        String language = "en";
        String country = "GB";

        NewsSource source = new NewsSource();
        TaggedSourceAs tagged = new TaggedSourceAs();
        tagged.setSource(source);
        tagged.setTag(new Tag(category));
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
