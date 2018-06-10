/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.dbCompletion;

import crawlers.Logos;
import db.news.NewsSource;
import java.util.logging.Logger;
import services.news.NewsSourceService;

/**
 *
 * @author zua
 */

public class LogoCompletionWorker {

    private static final Logger LOGGER = Logger.getLogger(LogoCompletionWorker.class.getSimpleName());

    public static void main(String[] args) {
        new LogoCompletionWorker().complete();
    }
    
    public void complete() {
        try {
            Iterable<NewsSource> sources = new NewsSourceService().findAllSources();
            for (NewsSource s : sources) {
                if (s.getLogoUrl() == null || s.getLogoUrl().isEmpty()) {
                    LOGGER.info(String.format("Found %s without logo", s.getSourceId()));
                    s.setLogoUrl(Logos.getLogo(s.getSourceId()));
                    try {
                        new NewsSourceService().save(s);
                    } catch (Exception e) {
                        LOGGER.info(String.format("Found Exception %s", e));
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info(String.format("Found Exception %s", e));
        }
    }

}
