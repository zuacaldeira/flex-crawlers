/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.exceptions;

import crawlers.publishers.exceptions.UrlNotFoundException;
import crawlers.publishers.exceptions.JsoupElementNotFoundException;
import org.junit.Test;
import static org.junit.Assert.*;

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
