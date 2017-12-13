/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.elements;

import crawlers.publishers.exceptions.TimeNotFoundException;
import java.util.Date;
import backend.utils.MyDateUtils;

/**
 *
 * @author zua
 */
public class TimeElement extends ArticleElement {

    private final String language;

    public TimeElement(String value, String language) {
        super(value);
        this.language = language;
    }

    public Date getDate() throws TimeNotFoundException {
        try {
            if (language != null && getValue() != null) {
                return MyDateUtils.parseDate(getValue(), language);
            } else if (getValue() != null) {
                return MyDateUtils.parseDate(getValue());
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }
}
