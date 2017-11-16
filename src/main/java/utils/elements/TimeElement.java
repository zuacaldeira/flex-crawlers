/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.elements;

import crawlers.exceptions.TimeNotFoundException;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MyDateUtils;

/**
 *
 * @author zua
 */
public class TimeElement extends ArticleElement {

    private String language;

    public TimeElement(String value, String language) {
        super(value);
        this.language = language;
    }

    public Date getDate() throws TimeNotFoundException {
        try {
            return MyDateUtils.parseDate(getValue(), language);
        } catch (Exception e) {
            try {
                return MyDateUtils.parseDate(getValue());
            } catch (ParseException ex) {
                return null;
            }
        }
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
