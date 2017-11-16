/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zua
 */
public class LogosTest {

    public LogosTest() {
    }

    /**
     * Test of getLogos method, of class Logos.
     */
    @Test
    public void testGetLogos() {
        System.out.println("getLogos");
        assertFalse(Logos.getLogos().isEmpty());
    }
    
    @Test
    public void testConstructor() {
        assertNotNull(new Logos());
    }

}
