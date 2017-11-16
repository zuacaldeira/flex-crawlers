/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.exceptions;

/**
 *
 * @author zua
 */
public class ApiCallException extends Exception {

    private static final long serialVersionUID = -5887750051165568060L;

    public ApiCallException(String url) {
        super(url);
    }
    
}
