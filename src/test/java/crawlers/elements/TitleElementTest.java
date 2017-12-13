/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.elements;

import crawlers.elements.TitleElement;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test for the {@code TitleElement} class.
 *
 * @author zua
 */
public class TitleElementTest {
    
    public TitleElementTest() {
    }

    @DataProvider
    public static Object[][] valueData() {
        Object[][] result = new Object[][]{
            {null}, {"Title"}, {""}  
        };
        return result;
    }
    
    @Test(dataProvider = "valueData")
    public void testValue(String title) {
        TitleElement te = new TitleElement(title);
        assertEquals(title, te.getValue());
    }

}
