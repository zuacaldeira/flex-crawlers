/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.iolNews;

import crawlers.Logos;
import db.news.NewsSource;
import db.news.Tag;


/**
 *
 * @author zua
 */

 public class IOLNewsZACrawler extends AbstactIOLNewsCrawler {

    public IOLNewsZACrawler() {
        super();
    }

    @Override
    protected String getUrl() {
        return "https://www.iol.co.za/news";
    }
    
    @Override
    public NewsSource getMySource() {
        String sourceId = "iol-news-za";
        String name = "IOL News South Africa";
        String description = "";
        String url = getUrl();
        String category = "General";
        String language = "en";
        String country = "ZA";

        NewsSource source = new NewsSource();
        source.setCategory(new Tag(category));
        source.setCountry(country);
        source.setDescription(description);
        source.setLanguage(language);
        source.setLogoUrl(Logos.getLogo(sourceId));
        source.setName(name);
        source.setSourceId(sourceId);
        source.setUrl(url);
        source.setLogoUrl(Logos.getLogo(sourceId));

        return source;
    }

    @Override
    
    public void crawl() {
        super.crawl(); //To change body of generated methods, choose Tools | Templates.
    }
    

}
