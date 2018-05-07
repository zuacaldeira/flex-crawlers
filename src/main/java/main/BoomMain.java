/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import crawlersV2.BoomAngonoticias;
import crawlersV2.BoomAngop;
import crawlersV2.BoomFolha8;
import crawlersV2.BoomIOLSA;
import crawlersV2.BoomJornalDeAngola;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author zua
 */
public class BoomMain {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        new BoomMain().schedule();
    }

    public void schedule() {
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println(new Date().toString() + " Articles: " + new BoomIOLSA().loadArticles());
        }, 1, 25, TimeUnit.MINUTES);
        
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println(new Date().toString() + " Articles: " + new BoomJornalDeAngola().loadArticles());
        }, 1, 25, TimeUnit.MINUTES);
        
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println(new Date().toString() + " Articles: " + new BoomAngop().loadArticles());
        }, 1, 25, TimeUnit.MINUTES);

        scheduler.scheduleAtFixedRate(() -> {
            System.out.println(new Date().toString() + " Articles: " + new BoomAngonoticias().loadArticles());
        }, 1, 25, TimeUnit.MINUTES);

        scheduler.scheduleAtFixedRate(() -> {
            System.out.println(new Date().toString() + " Articles: " + new BoomFolha8().loadArticles());
        }, 1, 25, TimeUnit.MINUTES);

        /*scheduler.scheduleAtFixedRate(() -> {
            NewsApiOrgClient client = new NewsApiOrgClient();
            TreeSet<NewsSource> sources = client.loadSources();
            while (!sources.isEmpty()) {
                NewsSource next = sources.pollFirst();
                if(next != null) {
                    scheduler.scheduleAtFixedRate(() -> {
                        System.out.println("Source  : " + next);
                        System.out.println(new Date().toString() + " Articles: " + client.loadArticlesFromSource(next));
                    }, 0, 60, TimeUnit.MINUTES);
                }
            }
        }, 0, 1, TimeUnit.DAYS);*/

    }

}
