/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author zua
 */
public class FlexCrawlerLogger {

    private boolean isOn = false;
    private Class<?> aClass;

    public FlexCrawlerLogger(Class<?> aClass) {
        this.aClass = aClass;
    }

    public void log(String format, Object... values) {
        sout(format, values);
    }

    public void info(String format, Object... values) {
        sout(format, values);
    }

    public void error(String format, Object... values) {
        serr(format, values);
    }

    public void on() {
        isOn = true;
    }

    public void off() {
        isOn = false;
    }

    public boolean isOn() {
        return isOn;
    }

    private void sout(String format, Object[] values) {
        String newFormat = "[%s] %25s: " + format;
        Object[] newValues = new Object[values.length + 2];
        newValues[0] = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        newValues[1] = aClass.getSimpleName();
        int i = 2;
        for (Object o : values) {
            newValues[i] = o;
            i++;
        }
        String result = String.format(newFormat, newValues);
        System.out.println(result);
    }

    private void serr(String format, Object[] values) {
        String newFormat = "[%s] %25s: " + format;
        Object[] newValues = new Object[values.length + 2];
        newValues[0] = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        newValues[1] = aClass.getSimpleName();
        int i = 2;
        for (Object o : values) {
            newValues[i] = o;
            i++;
        }
        String result = String.format(newFormat, newValues);
        System.err.println(result);
    }
}
