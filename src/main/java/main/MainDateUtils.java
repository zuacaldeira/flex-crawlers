/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.text.ParseException;
import utils.MyDateUtils;

/**
 *
 * @author zua
 */
public class MainDateUtils {
    
    public void main(String... args) throws ParseException {
        System.out.println(MyDateUtils.parseDate(" 2016/09/15    11:09 "));
    }
    
}
