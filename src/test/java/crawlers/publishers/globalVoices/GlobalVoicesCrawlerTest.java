/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.globalVoices;

import crawlers.AbstractCrawlerTest;
import crawlers.FlexNewsCrawler;
import crawlers.Logos;
import db.news.NewsSource;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author zua
 */
public class GlobalVoicesCrawlerTest extends AbstractCrawlerTest {

    @DataProvider
    public static Object[][] languageProvider() {
        return new Object[][]{
            {"en"}
        };
    }

    @DataProvider
    public static Object[][] languageAndCountryProvider() {
        return new Object[][]{
            {"en", "GB"}
        };
    }

    public GlobalVoicesCrawlerTest() {
    }

    @Test
    @Override
    public void testGetMySource() {
        System.out.println("getMySource");
        FlexNewsCrawler crawler = getCrawler();
        NewsSource source = crawler.getMySource();
        assertEquals("global-voices-en", source.getSourceId());
        assertEquals("Global Voices (EN)", source.getName());
        assertEquals("en", source.getLanguage());
        assertEquals("GB", source.getCountry());
        assertEquals("https://en.globalvoices.org", source.getUrl());
        assertEquals(Logos.getLogo("global-voices"), source.getLogoUrl());
    }

    @Test(dataProvider = "languageProvider")
    public void testGetMySource(String language) {
        GlobalVoicesCrawlerEN crawler = (GlobalVoicesCrawlerEN) getCrawler();
        NewsSource source = crawler.getMySource();
        assertEquals("global-voices-" + language, source.getSourceId());
        assertEquals("Global Voices (EN)", source.getName());
        assertEquals(language, source.getLanguage());
        assertEquals("https://" + language + ".globalvoices.org", source.getUrl());
        assertEquals(Logos.getLogo("global-voices"), source.getLogoUrl());
    }

    @Test(dataProvider = "languageAndCountryProvider")
    public void testGetMySource(String language, String country) {
        GlobalVoicesCrawlerEN crawler = (GlobalVoicesCrawlerEN) getCrawler();
        NewsSource source = crawler.getMySource();
        assertEquals("global-voices-" + language, source.getSourceId());
        assertEquals("Global Voices (EN)", source.getName());
        assertEquals(language, source.getLanguage());
        assertEquals(country, source.getCountry());
        assertEquals("https://" + language + ".globalvoices.org", source.getUrl());
        assertEquals(Logos.getLogo("global-voices"), source.getLogoUrl());
    }

    @Override
    protected GlobalVoicesCrawlerEN getCrawler() {
        return new GlobalVoicesCrawlerEN();
    }

}
