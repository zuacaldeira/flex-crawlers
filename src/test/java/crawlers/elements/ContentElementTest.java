/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.elements;

import crawlers.elements.ContentElement;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author zua
 */
public class ContentElementTest {
    
    public ContentElementTest() {
    }

    @DataProvider
    public static Object[][] valueData() {
        Object[][] result = new Object[][]{
            {null}, {"Title"}, {""}  
        };
        return result;
    }
    
    @Test(dataProvider = "valueData")
    public void testValue(String content) {
        ContentElement te = new ContentElement(content);
        assertEquals(content, te.getValue());
    }
    
}
