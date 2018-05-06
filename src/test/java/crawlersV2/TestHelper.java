/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlersV2;

import db.news.NewsArticle;
import java.util.Collection;
import java.util.TreeSet;

/**
 *
 * @author zua
 */
public class TestHelper {
   
    public static void print(String title, Collection<String> data) {
        System.out.println("--------------------------------------");
        System.out.println("LINKS FOUND: " + data.size());
        System.out.println("--------------------------------------");
        for(String d: data) {
            System.out.println("d:" + d);
        }
        System.out.println();
    }

    public static void print(String links, TreeSet<NewsArticle> data) {
        System.out.println("--------------------------------------");
        System.out.println("LINKS FOUND: " + data.size());
        System.out.println("--------------------------------------");
        for(NewsArticle d: data) {
            System.out.println("d:" + d.toString());
        }
        System.out.println();
    }
}
