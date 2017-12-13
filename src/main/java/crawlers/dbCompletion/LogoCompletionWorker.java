/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.dbCompletion;

import crawlers.Logos;
import db.news.NewsSource;
import org.neo4j.ogm.session.Session;
import crawlers.utils.FlexCrawlerLogger;
import crawlers.utils.Neo4jSessionFactoryForCrawlers;

/**
 *
 * @author zua
 */

public class LogoCompletionWorker {

    private final FlexCrawlerLogger logger = new FlexCrawlerLogger(LogoCompletionWorker.class);
    private Session session = Neo4jSessionFactoryForCrawlers.getInstance().getNeo4jSession();

    public void crawl() {
        try {
            Iterable<NewsSource> sources = findAllSources();
            for (NewsSource s : sources) {
                if (s.getLogoUrl() == null || s.getLogoUrl().isEmpty()) {
                    logger.info("FFFFFFFFFF Found %s without logo", s.getSourceId());
                    s.setLogoUrl(Logos.getLogo(s.getSourceId()));
                    try {
                        saveSource(s);
                    } catch (Exception e) {
                        logger.error("Found Exception %s", e);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Found Exception %s", e);
        }
    }

    private Iterable<NewsSource> findAllSources() {
        return session.loadAll(NewsSource.class);
    }

    private void saveSource(NewsSource s) {
        session.save(s);
    }
}
