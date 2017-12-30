/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.exceptions;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

/**
 *
 * @author zua
 */
public class UrlNotFoundExceptionTest {
    
    public UrlNotFoundExceptionTest() {
    }

    @Test
    public void testSomeMethod() {
        assertNotNull(new UrlNotFoundException());
        assertTrue(new UrlNotFoundException() instanceof JsoupElementNotFoundException);
    }
    
}
