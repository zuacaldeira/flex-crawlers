/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.dbCompletion;

import com.google.common.collect.Lists;
import crawlers.Logos;
import db.NewsSource;
import java.util.List;
import org.neo4j.ogm.session.Session;
import utils.FlexCrawlerLogger;
import utils.Neo4jSessionFactoryForCrawlers;

/**
 *
 * @author zua
 */

public class LogoCompletionWorker {

    private final FlexCrawlerLogger logger = new FlexCrawlerLogger(LogoCompletionWorker.class);
    private Session session = Neo4jSessionFactoryForCrawlers.getInstance().getNeo4jSession();

    public void crawl() {
        try {
            List<NewsSource> sources = findAllSources();
            logger.info("FFFFFFFFFF Found %d sources", sources.size());

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

    private List<NewsSource> findAllSources() {
        return Lists.newArrayList(session.loadAll(NewsSource.class));
    }

    private void saveSource(NewsSource s) {
        session.save(s);
    }
}
