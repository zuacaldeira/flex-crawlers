/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.elements;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

/**
 *
 * @author zua
 */
@RunWith(DataProviderRunner.class)
public class AuthorsElementTest {
    
    public AuthorsElementTest() {
    }

    /**
     * Test of getAuthors method, of class AuthorsElement.
     * @param names A single string with comma-separated list of names.
     * @param howMany The number of expected authors.
     */
    @Test
    @UseDataProvider("getData")
    public void testGetAuthors(String names, int howMany) {
        System.out.println("getAuthors");
        AuthorsElement instance = new AuthorsElement(names);
        Set<String> result = instance.getAuthors();
        assertEquals(howMany, result.size());
    }
    
    @DataProvider()
    public static Object[][] getData() {
        Object[][] result = {
            {null, 0},
            {"", 0},
            {"Alexandre Caldeira", 1},
            {"Alexandre Caldeira, Zua Caldeira", 2},
        };
        return result;
    }
}
