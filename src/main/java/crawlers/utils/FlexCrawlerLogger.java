/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.utils;

/**
 *
 * @author zua
 */
public class FlexCrawlerLogger {

    private boolean isOn = false;

    public FlexCrawlerLogger(Class<?> aClass) {
    }

    public void log(String message) {
        if (isOn()) {
            sout(message);
        }
    }

    public void info(String message) {
        sout(message);
    }

    public void error(String message) {
        serr(message);
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

    private void sout(String message) {
        System.out.println(message);
    }

    private void serr(String message) {
        System.err.println(message);
    }
}
