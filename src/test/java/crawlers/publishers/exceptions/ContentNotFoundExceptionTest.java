/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.exceptions;

import crawlers.publishers.exceptions.ContentNotFoundException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zua
 */
public class ContentNotFoundExceptionTest {
    
    public ContentNotFoundExceptionTest() {
    }

    @Test
    public void testSomeMethod() {
        assertNotNull(new ContentNotFoundException());
    }
    
}