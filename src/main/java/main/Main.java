package main;

import crawlers.FlexNewsAggregator;
import crawlers.GlobalVoicesAggregator;
import crawlers.NewsOrgApiAggregator;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author zua
 */
public class Main {

    public static void main(String... args) {
        int deltaT = 2 * 60 * 1000;
        while (true) {
            new NewsOrgApiAggregator().aggregate();
            try {
                Thread.sleep(deltaT);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            new FlexNewsAggregator().aggregate();
            try {
                Thread.sleep(deltaT);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            new GlobalVoicesAggregator().aggregate();
            try {
                Thread.sleep(deltaT);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
