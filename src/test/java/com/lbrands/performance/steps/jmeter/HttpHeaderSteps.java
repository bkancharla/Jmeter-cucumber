package com.lbrands.performance.steps.jmeter;


import cucumber.api.java8.En;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.testelement.TestElement;

public class HttpHeaderSteps implements En {

    private HeaderManager manager;


    public HttpHeaderSteps() {
        defaultHeader();

        Given("Added  header to http Sampler", () -> {
            this.manager = defaultHeader();
        });
    }

    private HeaderManager defaultHeader() {
        manager = new HeaderManager();
        manager.setName("header Info");
        manager.add(new Header("Origin", "apitest.victoriassecret.com"));
        manager.setEnabled(true);
        manager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
        manager.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());
        return manager;
    }
    public HeaderManager getManager() {
        return manager;
    }
}
