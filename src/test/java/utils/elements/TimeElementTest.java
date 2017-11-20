/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.elements;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import crawlers.exceptions.TimeNotFoundException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

/**
 * Tests a time element.
 *
 * @author zua
 */
@RunWith(DataProviderRunner.class)
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
    @Test
    @UseDataProvider("timeData")
    public void testGetDate(String dateString, String language) throws TimeNotFoundException {
        System.out.println("getDate");
        TimeElement instance = new TimeElement(dateString, language);
        assertNotNull(instance.getDate());
    }

    @Test(expected=Exception.class)
    @UseDataProvider("timeFailData")
    public void testGetDateFail(String dateString, String language) throws TimeNotFoundException {
        System.out.println("getDate");
        TimeElement instance = new TimeElement(dateString, language);
        assertNull(instance.getDate());
    }
}
