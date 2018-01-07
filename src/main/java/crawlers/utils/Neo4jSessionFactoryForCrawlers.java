/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.utils;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

/**
 *
 * @author zua
 */
public class Neo4jSessionFactoryForCrawlers {

    private static Neo4jSessionFactoryForCrawlers factory;

    private final SessionFactory sessionFactory;

    private Neo4jSessionFactoryForCrawlers() {
        // We pass it as the first argument to a SessionFactory instance
        Configuration configuration = new Configuration();
        configuration.driverConfiguration().setURI(System.getenv("GRAPHENEDB_URL"));
        configuration.autoIndexConfiguration().setAutoIndex("assert");
        sessionFactory = new SessionFactory(configuration, "db");
    }

    public static Neo4jSessionFactoryForCrawlers getInstance() {
        if (factory == null) {
            factory = new Neo4jSessionFactoryForCrawlers();
        }
        return factory;
    }

    public Session getNeo4jSession() {
        return sessionFactory.openSession();
    }

}
