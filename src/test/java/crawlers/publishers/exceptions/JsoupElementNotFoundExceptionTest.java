/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.exceptions;

import static org.testng.Assert.assertNotNull;
import org.testng.annotations.Test;

/**
 *
 * @author zua
 */
public class JsoupElementNotFoundExceptionTest {
    
    public JsoupElementNotFoundExceptionTest() {
    }

    @Test
    public void testSomeMethod() {
        assertNotNull(new JsoupElementNotFoundException());
    }
    
}
