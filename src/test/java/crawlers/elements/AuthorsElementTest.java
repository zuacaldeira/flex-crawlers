/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.elements;

import crawlers.elements.AuthorsElement;
import java.util.Set;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author zua
 */
public class AuthorsElementTest {
    
    public AuthorsElementTest() {
    }

    /**
     * Test of getAuthors method, of class AuthorsElement.
     * @param names A single string with comma-separated list of names.
     * @param howMany The number of expected authors.
     */
    @Test(dataProvider = "getData")
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
