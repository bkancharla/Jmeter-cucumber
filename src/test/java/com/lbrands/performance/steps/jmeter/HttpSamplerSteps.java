package com.lbrands.performance.steps.jmeter;


import com.lbrands.performance.steps.LoadProperties;
import cucumber.api.java8.En;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.testelement.TestElement;

public class HttpSamplerSteps implements En {


    private HTTPSamplerProxy httpRequest;


    public HttpSamplerSteps() {
        Given("configured GET http sampler for {string} page", (String page) -> {
            //need to bring url
            httpRequest = new HTTPSamplerProxy();
            httpRequest.setDoMultipartPost(false);
            httpRequest.setDoMultipartPost(false);
            httpRequest.setDomain("api.xyz.com");
            httpRequest.setProtocol("https");
            httpRequest.setPath(LoadProperties.getPage(page).getPath());
            httpRequest.setMethod("GET");
            httpRequest.setAutoRedirects(false);
            httpRequest.setName(page);
            httpRequest.setFollowRedirects(true);
            httpRequest.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
            httpRequest.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());
        });

        Given("configured POST http sampler for {string} api", (String api) -> {
            httpRequest = new HTTPSamplerProxy();
            httpRequest.setDoMultipartPost(false);
            httpRequest.setDoMultipartPost(false);
            httpRequest.setDomain("api.xyz.com");
            httpRequest.setProtocol("https");
            httpRequest.setPath(LoadProperties.getPage(api).getPath());
            httpRequest.setMethod("POST");
            httpRequest.setAutoRedirects(false);
            httpRequest.setName(api);
            httpRequest.setFollowRedirects(false);
            httpRequest.setPostBodyRaw(true);
            if (!LoadProperties.getPage(api).getRequest().equals(""))

            {
                Arguments args = new Arguments();
                httpRequest.hasArguments();
                HTTPArgument httparg = new HTTPArgument();
                httparg.setValue(LoadProperties.getPage(api).getRequest());
                args.addArgument(httparg);
                httpRequest.setArguments(args);
            }
            httpRequest.setPostBodyRaw(true);
            httpRequest.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
            httpRequest.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

        });

    }

    public HTTPSamplerProxy getHttpRequest() {
        return httpRequest;
    }
}
