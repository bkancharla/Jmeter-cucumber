package com.lbrands.performance.steps.jmeter;


import cucumber.api.java8.En;
import org.apache.jmeter.protocol.http.control.CookieManager;
import org.apache.jmeter.protocol.http.gui.CookiePanel;
import org.apache.jmeter.testelement.TestElement;

public class HttpCookieManagerSteps implements En {

    private CookieManager cookie;


    public HttpCookieManagerSteps() {
        defaultCookieManager();

        Given("Added  CookieManager to http Sampler", () -> {
            this.cookie = defaultCookieManager();
        });
    }

    private CookieManager defaultCookieManager() {
        cookie = new CookieManager();
        cookie.setName("cookie manager");
        cookie.setProperty(TestElement.TEST_CLASS, CookieManager.class.getName());
        cookie.setProperty(TestElement.GUI_CLASS, CookiePanel.class.getName());
        return cookie;
    }
    public CookieManager getCookie() {
        return cookie;
    }
}
