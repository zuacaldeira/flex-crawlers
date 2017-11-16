/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.elements;

import utils.elements.ContentElement;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import utils.elements.ContentElement;

/**
 *
 * @author zua
 */
@RunWith(DataProviderRunner.class)
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
    
    @Test
    @UseDataProvider("valueData")
    public void testValue(String content) {
        ContentElement te = new ContentElement(content);
        assertEquals(content, te.getValue());
    }
    
}
