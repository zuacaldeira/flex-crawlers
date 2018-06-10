/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import crawlersV2.NewsApiOrgClient;
import db.news.NewsSource;
import java.util.TreeSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author zua
 */
public class BoomMain2 {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    public static void main(String[] args) {
        new BoomMain2().schedule();
    }

    public void schedule() {

        scheduler.scheduleAtFixedRate(() -> {
            NewsApiOrgClient client = new NewsApiOrgClient();
            TreeSet<NewsSource> sources = client.loadSources();
            while (!sources.isEmpty()) {
                NewsSource next = sources.pollFirst();
                if (!"Axios".equals(next.getName()) && !"NBC News".equals(next.getName())) {
                    System.out.println("------------------------------");
                    System.out.println("News APi Source  : " + next);
                    System.out.println(sources.size() + " to go!");
                    client.loadArticlesFromSource(next);
                    System.out.println("------------------------------");
                }
            }
        }, 0, 60, TimeUnit.MINUTES);
    }

}
