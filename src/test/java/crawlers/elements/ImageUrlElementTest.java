/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.elements;

import crawlers.elements.ImageUrlElement;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test for the {@code TitleElement} class.
 *
 * @author zua
 */
public class ImageUrlElementTest {

    public ImageUrlElementTest() {
    }

    @DataProvider
    public static Object[][] valueData() {
        Object[][] result = new Object[][]{
            {null}, {"Title"}, {""}
        };
        return result;
    }

    @Test(dataProvider = "valueData")
    public void testValue(String url) {
        ImageUrlElement te = new ImageUrlElement(url);
        assertEquals(url, te.getValue());
    }

}
