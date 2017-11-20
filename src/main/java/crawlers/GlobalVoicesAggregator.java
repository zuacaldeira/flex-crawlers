/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers;

import crawlers.globalVoices.GlobalVoicesCrawlerAM;
import crawlers.globalVoices.GlobalVoicesCrawlerAR;
import crawlers.globalVoices.GlobalVoicesCrawlerAYM;
import crawlers.globalVoices.GlobalVoicesCrawlerBG;
import crawlers.globalVoices.GlobalVoicesCrawlerBN;
import crawlers.globalVoices.GlobalVoicesCrawlerCA;
import crawlers.globalVoices.GlobalVoicesCrawlerCS;
import crawlers.globalVoices.GlobalVoicesCrawlerDA;
import crawlers.globalVoices.GlobalVoicesCrawlerDE;
import crawlers.globalVoices.GlobalVoicesCrawlerEL;
import crawlers.globalVoices.GlobalVoicesCrawlerEN;
import crawlers.globalVoices.GlobalVoicesCrawlerEO;
import crawlers.globalVoices.GlobalVoicesCrawlerES;
import crawlers.globalVoices.GlobalVoicesCrawlerFA;
import crawlers.globalVoices.GlobalVoicesCrawlerFIL;
import crawlers.globalVoices.GlobalVoicesCrawlerFR;
import crawlers.globalVoices.GlobalVoicesCrawlerHE;
import crawlers.globalVoices.GlobalVoicesCrawlerHI;
import crawlers.globalVoices.GlobalVoicesCrawlerHU;
import crawlers.globalVoices.GlobalVoicesCrawlerID;
import crawlers.globalVoices.GlobalVoicesCrawlerIT;
import crawlers.globalVoices.GlobalVoicesCrawlerJA;
import crawlers.globalVoices.GlobalVoicesCrawlerKM;
import crawlers.globalVoices.GlobalVoicesCrawlerKO;
import crawlers.globalVoices.GlobalVoicesCrawlerMG;
import crawlers.globalVoices.GlobalVoicesCrawlerMK;
import crawlers.globalVoices.GlobalVoicesCrawlerNE;
import crawlers.globalVoices.GlobalVoicesCrawlerOR;
import crawlers.globalVoices.GlobalVoicesCrawlerPA;
import crawlers.globalVoices.GlobalVoicesCrawlerPL;
import crawlers.globalVoices.GlobalVoicesCrawlerPS;
import crawlers.globalVoices.GlobalVoicesCrawlerPT;
import crawlers.globalVoices.GlobalVoicesCrawlerRO;
import crawlers.globalVoices.GlobalVoicesCrawlerRU;
import crawlers.globalVoices.GlobalVoicesCrawlerSQ;
import crawlers.globalVoices.GlobalVoicesCrawlerSR;
import crawlers.globalVoices.GlobalVoicesCrawlerSV;
import crawlers.globalVoices.GlobalVoicesCrawlerSW;
import crawlers.globalVoices.GlobalVoicesCrawlerTET;
import crawlers.globalVoices.GlobalVoicesCrawlerTR;
import crawlers.globalVoices.GlobalVoicesCrawlerUR;
import crawlers.globalVoices.GlobalVoicesCrawlerZHS;
import crawlers.globalVoices.GlobalVoicesCrawlerZHT;

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
            new GlobalVoicesCrawlerMG().crawl();
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
