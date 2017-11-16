/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.exceptions;

import crawlers.exceptions.TitleNotFoundException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zua
 */
public class TitleNotFoundExceptionTest {
    
    public TitleNotFoundExceptionTest() {
    }

    @Test
    public void testSomeMethod() {
        assertNotNull(new TitleNotFoundException());
    }
    
}
