package com.lbrands.performance.steps.jmeter;

import cucumber.api.java8.En;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;

public class SummarySteps implements En {


    public SummarySteps() {
    }


    private ResultCollector defaultResultCollector() {
        String reportFile = "report.jtl";
        ResultCollector logger = new ResultCollector(new Summariser());
        logger.setFilename(reportFile);
        return logger;
    }


    public ResultCollector getSummarizer(){
        return defaultResultCollector();
    }
}
