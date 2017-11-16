/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.neovisionaries.i18n.CountryCode;
import com.neovisionaries.i18n.LanguageCode;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.time.DateUtils;

/**
 *
 * @author zua
 */
public class MyDateUtils {

    public static String[] getParsePatterns() {
        return new String[]{
            getTelaNonPattern(),
            getANacaoCVPattern(),
            getAVerdadeOnlinePattern(),
            getGlobalVoicesPattern(),
            getMakaAngolaPattern(),
            getJornalDeAngolaPattern(),
            getIOLNewsZAPattern(),
            getTheBugleZAPattern(),
            getNewsOrgApiPattern(),
            getNewsOrgApiPattern2(),
            getNewsOrgApiPattern3(),
            getNewsOrgApiPattern4()
        };
    }

    public static String extractLanguage(String localeString) {
        if (localeString != null && localeString.contains("_")) {
            return localeString.split("_")[0];
        } else {
            throw new IllegalArgumentException("Expected data in format 'language_COUNTRY', but found " + localeString);
        }
    }

    public static String extractCountry(String localeString) {
        if (localeString != null && localeString.contains("_")) {
            String[] parts = localeString.split("_");
            if(parts.length > 1) {
                return parts[1];
            } else {
                return parts[0].toUpperCase();
            }
        }
        throw new IllegalArgumentException("Expected data in format 'language_COUNTRY', but found " + localeString);
    }

    public static String getLanguageNameFromPattern(String localeString) {
        LanguageCode code = LanguageCode.getByCodeIgnoreCase(extractLanguage(localeString));
        if(code != null) {
            return code.getName();
        }
        else {
            System.err.println("Could not get the language name for " + localeString);
            return Locale.forLanguageTag(extractLanguage(localeString)).getDisplayLanguage();
        }
    }

    public static String getCountryNameFromPattern(String localeString) {
        CountryCode code = CountryCode.getByCodeIgnoreCase(extractCountry(localeString));
        if(code != null) {
            return code.getName();
        }
        else {
            System.err.println("Could not get the language name for " + localeString);
            return Locale.forLanguageTag(extractLanguage(localeString)).getDisplayCountry();
        }
    }

    public static String getLanguageCode(String displayLanguage) {
        List<LanguageCode> codes = LanguageCode.findByName(displayLanguage);
        
        return codes.get(0).name();
    }

    public static String getCountryCode(String displayCountry) {
        List<CountryCode> codes = CountryCode.findByName(displayCountry);
        return codes.get(0).name();
    }
    
    public static String getLanguage(String languageCode) {
        LanguageCode code = LanguageCode.getByCodeIgnoreCase(languageCode);
        return code.getName();
    }

    public static String getCountry(String countryCode) {
        CountryCode code = CountryCode.getByCodeIgnoreCase(countryCode);
        return code.getName();
    }

    public static Date parseDate(String value, String language) throws ParseException {
        Locale locale = Locale.forLanguageTag(language);
        return DateUtils.parseDate(value, locale, getParsePatterns());
    }

    public static Date parseDate(String value) throws ParseException {
        return DateUtils.parseDate(value, getParsePatterns());
    }

    private static String getTelaNonPattern() {
        return "dd MMM yyyy";
    }
    
    private static String getANacaoCVPattern() {
        return "dd MMMM, yyyy";
    }

    private static String getAVerdadeOnlinePattern() {
        return "dd MMMM yyyy";
    }

    private static String getGlobalVoicesPattern() {
        return "yyyy-MM-dd HH:mm";
    }

    private static String getMakaAngolaPattern() {
        return "dd MMMM yyyy";
    }

    private static String getNewsOrgApiPattern() {
        return "yyyy-MM-dd'T'HH:mm:ss'Z'";
    }

    private static String getNewsOrgApiPattern2() {
        return "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    }

    private static String getNewsOrgApiPattern3() {
        return "yyyy-MM-dd'T'HH:mm:ss";
    }

    private static String getNewsOrgApiPattern4() {
        return "yyyy-MM-dd'T'HH:mm:ssXXX";
    }

    private static String getIOLNewsZAPattern() {
        return "yyyy-MM-dd'T'HH:mm";
    }
    
    private static String getJornalDeAngolaPattern() {
        return "yyyy-MM-dd HH:mm:ss";
    }

    private static String getTheBugleZAPattern() {
        return "yyyy-MM-dd";
    }

}
