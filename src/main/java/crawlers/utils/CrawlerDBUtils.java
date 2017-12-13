/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.utils;

/**
 *
 * @author zua
 */
public class CrawlerDBUtils {

    private static CrawlerDBUtils instance;
    
    private CrawlerDBUtils() {
    }

    public static CrawlerDBUtils getInstance() {
        if (instance == null) {
            instance = new CrawlerDBUtils();
        }
        return instance;
    }

    public String wrapUp(String string) {
        if (string == null) {
            return null;
        }

        if (string.isEmpty()) {
            return "";
        }

        if (string.contains("\"")) {
            string = string.replace("\"", "");
        }
        return "\"" + string + "\"";
    }

}
