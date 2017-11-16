/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import java.text.ParseException;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author zua
 */
@RunWith(DataProviderRunner.class)
public class MyDateUtilsTest {
    
    public MyDateUtilsTest() {
    }

    @DataProvider
    public static Object[][] locale2LanguageNameData() {
        return new Object[][]{
            {"pt_AO", "Portuguese"},
            {"pt_MZ", "Portuguese"},
            {"pt_PT", "Portuguese"},
            {"pt_", "Portuguese"},
            {"fil_", "Filipino"},
            {"pt_STP", "Portuguese"}
        };
    }

    @DataProvider
    public static Object[][] locale2CountryNameData() {
        return new Object[][]{
            {"pt_AO", "Angola"},
            {"pt_MZ", "Mozambique"},
            {"pt_PT", "Portugal"},
            {"pt_", "Portugal"},
            {"fil_PH", "Philippines"},
            {"pt_STP", "Sao Tome and Principe"}
        };
    }

    @DataProvider
    public static Object[][] locale2CountryCodeData() {
        return new Object[][]{
            {"pt_AO", "AO"},
            {"pt_MZ", "MZ"},
            {"pt_PT", "PT"},
            {"pt_", "PT"},
            {"pt_STP", "STP"}
        };
    }

    @DataProvider
    public static Object[][] locale2LanguageCodeData() {
        return new Object[][]{
            {"pt_AO", "pt"},
            {"pt_MZ", "pt"},
            {"pt_PT", "pt"},
            {"pt_", "pt"},            
            {"pt_STP", "pt"}
        };
    }

    @DataProvider
    public static Object[][] language2LanguageCodeData() {
        return new Object[][]{
            {"Portuguese", "pt"},
            {"English", "en"},
            {"German", "de"},
            {"Amharic", "am"},
        };
    }

    @DataProvider
    public static Object[][] country2CountryCodeData() {
        return new Object[][]{
            {"Portugal", "PT"},
            {"United Kingdom", "GB"},
            {"Germany", "DE"},
            {"Ethiopia", "ET"},
            {"Sao Tome and Principe", "ST"},
        };
    }

    @DataProvider
    public static Object[][] languageCode2LanguageNameData() {
        return new Object[][]{
            {"pt", "Portuguese"},
            {"en", "English"},
            {"de", "German"},
            {"am", "Amharic"}
        };
    }

    @DataProvider
    public static Object[][] countryCode2CountryNameData() {
        return new Object[][]{
            {"PT", "Portugal"},
            {"GB", "United Kingdom"},
            {"DE", "Germany"},
            {"ET", "Ethiopia"},
            {"STP", "Sao Tome and Principe"},
            {"ST", "Sao Tome and Principe"}
        };
    }

    @Test
    @UseDataProvider("locale2CountryNameData")
    public void testGetCountry(String localeString, String country) {
        assertEquals(country, MyDateUtils.getCountryNameFromPattern(localeString));
    }

    @Test
    @UseDataProvider("locale2LanguageNameData")
    public void testGetLanguage(String localeString, String language) {
        assertEquals(language, MyDateUtils.getLanguageNameFromPattern(localeString));
    }

    @Test
    @UseDataProvider("language2LanguageCodeData")
    public void testGetLanguageCode(String displayLanguage, String languageCode) {
        assertEquals(languageCode, MyDateUtils.getLanguageCode(displayLanguage));
    }

    @Test
    @UseDataProvider("country2CountryCodeData")
    public void testGetCountryCode(String displayCountry, String countryCode) {
        assertEquals(countryCode, MyDateUtils.getCountryCode(displayCountry));
    }
    
    @Test
    @UseDataProvider("languageCode2LanguageNameData")
    public void testGetLanguageName(String languageCode, String languageName) {
        assertEquals(languageName, MyDateUtils.getLanguage(languageCode));
    }

    @Test
    @UseDataProvider("countryCode2CountryNameData")
    public void testGetCountryName(String countryCode, String countryName) {
        assertEquals(countryName, MyDateUtils.getCountry(countryCode));
    }
    
    @DataProvider
    public static Object[][] parseDateData() {
        Date now = new Date();
        return new Object[][]{
            {"20 Jan 2017"},
            {"20 Feb 2017"}
        };
    }
    
    @Test
    @UseDataProvider("parseDateData")
    public void testParseDate(String value) throws ParseException {
        assertNotNull(MyDateUtils.parseDate(value));
    }
    
    @DataProvider
    public static Object[][] parseDateData2() {
        Date now = new Date();
        return new Object[][]{
            {"20 Jan 2017", "en"},
            {"20 Jan 2017", "pt"},
            {"20 Fev 2017", "pt"}
        };
    }
    
    @Test
    @UseDataProvider("parseDateData2")
    public void testParseDate2(String value, String language) throws ParseException {
        assertNotNull(MyDateUtils.parseDate(value, language));
    }
    

    /* 
    SART
    */    
    @Test
    @UseDataProvider("locale2CountryNameFailData")
    public void testGetCountryFail(String localeString) {
        assertTrue(MyDateUtils.getCountryNameFromPattern(localeString).isEmpty());
    }
    
    @DataProvider
    public static Object[][] locale2CountryNameFailData() {
        return new Object[][]{
            {"ar_001"},
            {"xx_XXX"},
        };
    }

    /* 
     * END
     */ 
    
    @Test
    @UseDataProvider("extractCountryData")
    public void testExtractCountry(String localeString, String country) {
        assertEquals(country, MyDateUtils.extractCountry(localeString));
    }
    
    @DataProvider
    public static Object[][] extractCountryData() {
        return new Object[][]{
            {"ar_001", "001"},
            {"xx_XXX", "XXX"},
            {"tet_TL", "TL"},
        };
    }
    
    @Test(expected=IllegalArgumentException.class)
    @UseDataProvider("extractCountryFailData")
    public void testExtractCountryFail(String localeString) {
        MyDateUtils.extractCountry(localeString);
    }
    
    @DataProvider
    public static Object[][] extractCountryFailData() {
        return new Object[][]{
            {null, "001"},
        };
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void testExtractLanguageFail() {
        MyDateUtils.extractLanguage(null);
    }

    @Test
    @UseDataProvider("extractLanguageData")
    public void testExtractLanguage(String localeString, String language) {
        assertEquals(language, MyDateUtils.extractLanguage(localeString));
    }
    
    @DataProvider
    public static Object[][] extractLanguageData() {
        return new Object[][]{
            {"ar_001", "ar"},
            {"xx_XXX", "xx"},
            {"tet_TL", "tet"},
        };
    }
    
    @Test
    public void testConstructor() {
        assertNotNull(new MyDateUtils());
    }
}
