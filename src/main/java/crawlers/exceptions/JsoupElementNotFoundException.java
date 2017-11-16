/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.exceptions;

import javax.ejb.ApplicationException;

/**
 *
 * @author zua
 */
@ApplicationException()
public class JsoupElementNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 356756979576692047L;
    
}
