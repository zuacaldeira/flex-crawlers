/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers;

import crawlers.publishers.globalVoices.GlobalVoicesCrawlerAM;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerAR;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerAYM;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerBG;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerBN;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerCA;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerCS;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerDA;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerDE;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerEL;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerEN;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerEO;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerES;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerFA;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerFIL;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerFR;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerHE;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerHI;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerHU;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerID;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerIT;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerJA;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerKM;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerKO;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerMG;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerMK;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerNE;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerOR;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerPA;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerPL;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerPS;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerPT;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerRO;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerRU;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerSQ;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerSR;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerSV;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerSW;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerTET;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerTR;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerUR;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerZHS;
import crawlers.publishers.globalVoices.GlobalVoicesCrawlerZHT;

/**
 *
 * @author zua
 */


public class GlobalVoicesAggregator {

    public void aggregate() {
        try {
            new GlobalVoicesCrawlerAM().crawl();
            new GlobalVoicesCrawlerAR().crawl();
            new GlobalVoicesCrawlerAYM().crawl();
            new GlobalVoicesCrawlerBG().crawl();
            new GlobalVoicesCrawlerBN().crawl();
            new GlobalVoicesCrawlerCA().crawl();
            new GlobalVoicesCrawlerCS().crawl();
            new GlobalVoicesCrawlerDA().crawl();
            new GlobalVoicesCrawlerDE().crawl();
            new GlobalVoicesCrawlerEL().crawl();
            new GlobalVoicesCrawlerEN().crawl();
            new GlobalVoicesCrawlerEO().crawl();
            new GlobalVoicesCrawlerES().crawl();
            new GlobalVoicesCrawlerFA().crawl();
            new GlobalVoicesCrawlerFIL().crawl();
            new GlobalVoicesCrawlerFR().crawl();
            new GlobalVoicesCrawlerHE().crawl();
            new GlobalVoicesCrawlerHI().crawl();
            new GlobalVoicesCrawlerHU().crawl();
            new GlobalVoicesCrawlerID().crawl();
            new GlobalVoicesCrawlerIT().crawl();
            new GlobalVoicesCrawlerJA().crawl();
            new GlobalVoicesCrawlerKM().crawl();
            new GlobalVoicesCrawlerKO().crawl();
            //new GlobalVoicesCrawlerMG().crawl();
            new GlobalVoicesCrawlerMK().crawl();
            new GlobalVoicesCrawlerNE().crawl();
            new GlobalVoicesCrawlerOR().crawl();
            new GlobalVoicesCrawlerPA().crawl();
            new GlobalVoicesCrawlerPL().crawl();
            new GlobalVoicesCrawlerPS().crawl();
            new GlobalVoicesCrawlerPT().crawl();
            new GlobalVoicesCrawlerRO().crawl();
            new GlobalVoicesCrawlerRU().crawl();
            new GlobalVoicesCrawlerSQ().crawl();
            new GlobalVoicesCrawlerSR().crawl();
            new GlobalVoicesCrawlerSV().crawl();
            new GlobalVoicesCrawlerSW().crawl();
            new GlobalVoicesCrawlerTET().crawl();
            new GlobalVoicesCrawlerTR().crawl();
            new GlobalVoicesCrawlerUR().crawl();
            new GlobalVoicesCrawlerZHS().crawl();
            new GlobalVoicesCrawlerZHT().crawl();
        } catch(Throwable t) {}
    }
}
