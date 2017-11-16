/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.dbCompletion;

import crawlers.Logos;
import db.NewsSource;
import java.util.List;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import services.NewsSourceService;
import services.NewsSourceServiceInterface;
import utils.FlexCrawlerLogger;

/**
 *
 * @author zua
 */
@Singleton
@Lock(LockType.READ)
public class LogoCompletionWorker {

    private final FlexCrawlerLogger logger = new FlexCrawlerLogger(LogoCompletionWorker.class);

    private NewsSourceServiceInterface sourcesService = new NewsSourceService();

    public void crawl() {
        try {
            List<NewsSource> sources = (List<NewsSource>) sourcesService.findAllSources();
            logger.info("FFFFFFFFFF Found %d sources", sources.size());

            for (NewsSource s : sources) {
                if (s.getLogoUrl() == null || s.getLogoUrl().isEmpty()) {
                    logger.info("FFFFFFFFFF Found %s without logo", s.getSourceId());
                    s.setLogoUrl(Logos.getLogo(s.getSourceId()));
                    try {
                        sourcesService.save(s);
                    } catch (Exception e) {
                        logger.error("Found Exception %s", e);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Found Exception %s", e);
        }
    }

    public void setSourcesService(NewsSourceServiceInterface sourcesService) {
        this.sourcesService = sourcesService;
    }

}
