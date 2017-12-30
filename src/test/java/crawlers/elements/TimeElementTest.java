/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.elements;

import crawlers.publishers.exceptions.TimeNotFoundException;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests a time element.
 *
 * @author zua
 */
public class TimeElementTest {

    
    @DataProvider
    public static Object[][] timeData() {
        Object[][] result = new Object[][]{
            {"20 Jan 2017", "pt"},
            {"20 Jan 2017", null},
        };
        return result;
    }
    
    @DataProvider
    public static Object[][] timeFailData() {
        Object[][] result = new Object[][]{
            {null, "pt"},
        };
        return result;
    }

    public TimeElementTest() {
    }

    /**
     * Test of getDate method, of class TimeElement.
     */
    @Test(dataProvider = "timeData")
    public void testGetDate(String dateString, String language) throws TimeNotFoundException {
        System.out.println("getDate()");
        TimeElement instance = new TimeElement(dateString, language);
        assertNotNull(instance.getDate());
    }

    @Test(dataProvider = "timeFailData")
    public void testGetDateFail(String dateString, String language) throws TimeNotFoundException {
        System.out.println("getDateFail()");
        TimeElement instance = new TimeElement(dateString, language);
        assertNull(instance.getDate());
    }
}
