/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.elements;

import crawlers.publishers.exceptions.TimeNotFoundException;
import java.util.Date;
import utils.MyDateUtils;

/**
 *
 * @author zua
 */
public class TimeElement extends ArticleElement {

    public TimeElement(String value, String language) {
        super(value);
    }

    public Date getDate() throws TimeNotFoundException {
        try {
            return MyDateUtils.parseDate(getValue());
        } catch (Exception ex) {
            return null;
        }
    }
}
