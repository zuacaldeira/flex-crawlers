/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.elements;

/**
 *
 * @author zua
 */
public abstract class ArticleElement {

    private final String value;

    public ArticleElement(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
    
    
    
}
