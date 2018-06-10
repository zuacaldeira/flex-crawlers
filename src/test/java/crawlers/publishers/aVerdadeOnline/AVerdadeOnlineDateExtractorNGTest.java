/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.aVerdadeOnline;

import crawlers.FlexNewsCrawler;
import crawlers.elements.TimeElement;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.Set;
import org.jsoup.nodes.Document;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.MyDateUtils;

/**
 *
 * @author zua
 */
public class AVerdadeOnlineDateExtractorNGTest {

    public AVerdadeOnlineDateExtractorNGTest() {
    }

    public FlexNewsCrawler getCrawler() {
        return new AVerdadeOnlineCrawler();
    }

    @DataProvider
    public static Object[][] articleUrls() {
        return new Object[][]{
            {"http://www.verdade.co.mz/nacional/64537-estupradores-fazem-mais-de-110-vitimas-em-quase-duas-semanas-em-mocambique"},
            {"http://www.verdade.co.mz/internacional/64544-explosao-atinge-policia-na-capital-do-afeganistao-e-deixa-dezenas-mortos-e-feridos"},
            {"http://www.verdade.co.mz/ambiente/64265-mais-chuvas-para-cabo-delgado-nampula-e-niassa-ate-fevereiro"}
        };
    }

    @DataProvider
    public static Object[][] articleUrlsAndTitles() {
        return new Object[][]{
            {"http://www.verdade.co.mz/nacional/64537-estupradores-fazem-mais-de-110-vitimas-em-quase-duas-semanas-em-mocambique", "Estupradores fazem mais de 110 vítimas em quase duas semanas em Moçambique"},
            {"http://www.verdade.co.mz/internacional/64544-explosao-atinge-policia-na-capital-do-afeganistao-e-deixa-dezenas-mortos-e-feridos", "Explosão atinge polícia na capital do Afeganistão e deixa dezenas mortos e feridos"},
            {"http://www.verdade.co.mz/ambiente/64265-mais-chuvas-para-cabo-delgado-nampula-e-niassa-ate-fevereiro", "Mais chuvas para Cabo Delgado, Nampula e Niassa até Fevereiro"}
        };
    }

    @DataProvider
    public static Object[][] articleUrlsAndDescriptions() {
        return new Object[][]{
            {"http://www.verdade.co.mz/nacional/64537-estupradores-fazem-mais-de-110-vitimas-em-quase-duas-semanas-em-mocambique",
                "Um total de 115 crianças e mulheres adultas foram abusadas sexualmente entre 20 de Dezembro passado a 01 de Janeiro corrente. Metade das vítimas é de zero a 14 anos de idade. Porém, comparativamente ao anterior período das festividades, o número diminuiu em 30 casos, ao passar de 145, entre 2016/2017, para 115, de 2017 para 2018. Mesmo assim, as autoridades mostram-se preocupadas com situação, sobretudo por envolver vítimas recém-nascidas."},
            {"http://www.verdade.co.mz/internacional/64544-explosao-atinge-policia-na-capital-do-afeganistao-e-deixa-dezenas-mortos-e-feridos",
                "Um homem-bomba na capital do Afeganistão causou dezenas de mortes e deixou vários feridos nesta terça-feira após se explodir perto de um grupo de membros das forças da segurança que realizava uma operação contra comércio ilegal de drogas e álcool, disseram autoridades."},
            {"http://www.verdade.co.mz/ambiente/64265-mais-chuvas-para-cabo-delgado-nampula-e-niassa-ate-fevereiro",
                "A actualização da previsão climática sazonal para o período Dezembro-Janeiro-Fevereiro prevê mais chuvas nas províncias de Cabo Delgado, Nampula, Niassa e extremo norte da província da Zambézia e menos precipitação nas províncias de Maputo, Gaza, Inhambane, Sofala, e sul da província de Tete."}
        };
    }

    @DataProvider
    public static Object[][] articleUrlsAndImageUrls() {
        return new Object[][]{
            {"http://www.verdade.co.mz/nacional/64537-estupradores-fazem-mais-de-110-vitimas-em-quase-duas-semanas-em-mocambique",
                null},
            {"http://www.verdade.co.mz/internacional/64544-explosao-atinge-policia-na-capital-do-afeganistao-e-deixa-dezenas-mortos-e-feridos",
                null},
            {"http://www.verdade.co.mz/ambiente/64265-mais-chuvas-para-cabo-delgado-nampula-e-niassa-ate-fevereiro",
                "http://www.verdade.co.mz/images/stories/02017/chuva/inam%20clima%20djf.jpg"}
        };
    }

    @DataProvider
    public static Object[][] articleUrlsAndAuthors() {
        return new Object[][]{
            {"http://www.verdade.co.mz/nacional/64537-estupradores-fazem-mais-de-110-vitimas-em-quase-duas-semanas-em-mocambique",
                "Redação"},
            {"http://www.verdade.co.mz/internacional/64544-explosao-atinge-policia-na-capital-do-afeganistao-e-deixa-dezenas-mortos-e-feridos",
                "Agências"},
            {"http://www.verdade.co.mz/ambiente/64265-mais-chuvas-para-cabo-delgado-nampula-e-niassa-ate-fevereiro",
                "Adérito Caldeira"}
        };
    }

    @DataProvider
    public static Object[][] articleUrlsAndDate() {
        return new Object[][]{
            {"http://www.verdade.co.mz/nacional/64537-estupradores-fazem-mais-de-110-vitimas-em-quase-duas-semanas-em-mocambique",
                "05 Janeiro 2018"},
            {"http://www.verdade.co.mz/internacional/64544-explosao-atinge-policia-na-capital-do-afeganistao-e-deixa-dezenas-mortos-e-feridos",
                "05 Janeiro 2018"},
            {"http://www.verdade.co.mz/ambiente/64265-mais-chuvas-para-cabo-delgado-nampula-e-niassa-ate-fevereiro",
                "08 Dezembro 2017"},
            {"http://www.verdade.co.mz/tema-de-fundo/35-themadefundo/64526-endividamento-publico-interno-atingiu-101-bilioes-em-mocambique-e-vai-aumentar-em-2018", 
                "04 Janeiro 2018"}
        };
    }

    @Test(dataProvider = "articleUrls")
    public void testFindArticle(String articleUrl) {
        assertNotNull(articleUrl, "Article Url cannot be null!");
        assertFalse(articleUrl.isEmpty(), "Article Url cannot be an empty string!");

        // Open a non null document
        Document document = getCrawler().openDocument(articleUrl);
        assertNotNull(document, "Document cannot be null for url: " + articleUrl);
    }

    @Test(dataProvider = "articleUrlsAndTitles")
    public void testFindTitle(String articleUrl, String articleTitle) {
        assertNotNull(articleUrl, "Article Url cannot be null!");
        assertFalse(articleUrl.isEmpty(), "Article Url cannot be an empty string!");

        // Open a non null document
        Document document = getCrawler().openDocument(articleUrl);
        assertNotNull(document, "Document cannot be null for url: " + articleUrl);

        // Extract it's title
        assertNotNull(articleTitle, "Title cannot be null");
        assertFalse(articleTitle.isEmpty(), "Title cannot be an empty string");
        String title = getCrawler().getTitle(document);
        assertEquals(title, articleTitle, "Title do not match");
    }

    @Test(dataProvider = "articleUrlsAndDescriptions")
    public void testFindDescription(String articleUrl, String articleDescription) {
        assertNotNull(articleUrl, "Article Url cannot be null!");
        assertFalse(articleUrl.isEmpty(), "Article Url cannot be an empty string!");

        assertNotNull(articleDescription, "Description cannot be null");
        assertFalse(articleDescription.isEmpty(), "Description cannot be an empty string");

        // Open a non null document
        Document document = getCrawler().openDocument(articleUrl);
        assertNotNull(document, "Document cannot be null for url: " + articleUrl);

        // Extract it's description
        String description = getCrawler().getContent(document);
        assertEquals(description, articleDescription, "Description do not match");
    }

    @Test(dataProvider = "articleUrlsAndImageUrls")
    public void testFindImageUrl(String articleUrl, String articleImageUrl) throws UnsupportedEncodingException {
        assertNotNull(articleUrl, "Article Url cannot be null!");
        assertFalse(articleUrl.isEmpty(), "Article Url cannot be an empty string!");

        // Open a non null document
        Document document = getCrawler().openDocument(articleUrl);
        assertNotNull(document, "Document cannot be null for url: " + articleUrl);

        // Extract an image url
        String imageUrl = getCrawler().getImageUrl(document);
        imageUrl = (imageUrl == null) ? null : imageUrl.replace(" ", "%20");

        assertEquals(imageUrl, articleImageUrl, "Image links do not match");
    }
    
    @Test(dataProvider = "articleUrlsAndAuthors")
    public void testFindAuthors(String articleUrl, String articleAuthor) throws UnsupportedEncodingException {
        assertNotNull(articleUrl, "Article Url cannot be null!");
        assertFalse(articleUrl.isEmpty(), "Article Url cannot be an empty string!");

        // Open a non null document
        Document document = getCrawler().openDocument(articleUrl);
        assertNotNull(document, "Document cannot be null for url: " + articleUrl);

        // Extract an image url
        Set<String> authors = getCrawler().getAuthors(document);
        assertNotNull(authors);
        assertFalse(authors.isEmpty());
        
        //
        assertTrue(authors.contains(articleAuthor), "Missing a known author.");
    }
    
    @Test(dataProvider = "articleUrlsAndDate")
    public void testFindDate(String articleUrl, String date) throws UnsupportedEncodingException, ParseException {
        assertNotNull(articleUrl, "Article Url cannot be null!");
        assertFalse(articleUrl.isEmpty(), "Article Url cannot be an empty string!");

        // Open a non null document
        Document document = getCrawler().openDocument(articleUrl);
        assertNotNull(document, "Document cannot be null for url: " + articleUrl);

        // Extract an image url
        TimeElement time = getCrawler().getTimeElement(document);
        assertNotNull(time, "Time element cannot be null");
        assertNotNull(time.getValue(), "Time value cannot be null");
        assertFalse(time.getValue().isEmpty(), "Time value cannot be empty");

        
        assertEquals(time.getValue(), date, "Time do not match");
        
        Date toDate = MyDateUtils.parseDate(date, "pt");
        assertEquals(getCrawler().getPublishedAt(document), toDate, "Time do not match");
    }

    private String transform(String string) {
        System.out.println("CONTAINS WHITESPACE " + string.contains("\u00a0"));
        System.out.println("BEFORE " + string);
        String result = string.replace("\u00a0", "T");
        System.out.println("AFTER " + result);
        return result;
    }

}
