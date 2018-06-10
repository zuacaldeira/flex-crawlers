/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import crawlersV2.BoomAngonoticias;
import crawlersV2.BoomAngop;
import crawlersV2.BoomCorreioAngolense;
import crawlersV2.BoomFolha8;
import crawlersV2.BoomIOLZA;
import crawlersV2.BoomJornalDeAngola;
import crawlersV2.BoomNovoJornal;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author zua
 */
public class BoomMain {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    public static void main(String[] args) {
        new BoomMain().schedule();
    }

    public void schedule() {

        scheduler.scheduleAtFixedRate(() -> {
            System.out.println(new Date().toString() + " Articles: " + new BoomCorreioAngolense().loadArticles());
            System.out.println(new Date().toString() + " Articles: " + new BoomIOLZA().loadArticles());
            System.out.println(new Date().toString() + " Articles: " + new BoomJornalDeAngola().loadArticles());
            System.out.println(new Date().toString() + " Articles: " + new BoomAngop().loadArticles());
            System.out.println(new Date().toString() + " Articles: " + new BoomAngonoticias().loadArticles());
            System.out.println(new Date().toString() + " Articles: " + new BoomFolha8().loadArticles());
            System.out.println(new Date().toString() + " Articles: " + new BoomNovoJornal().loadArticles());
        }, 0, 25, TimeUnit.MINUTES);
    }

}
