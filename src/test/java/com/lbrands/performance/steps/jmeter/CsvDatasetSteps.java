package com.lbrands.performance.steps.jmeter;


import com.lbrands.performance.steps.LoadProperties;
import cucumber.api.java8.En;
import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CsvDatasetSteps implements En {

    private CSVDataSet csvDataSet;


    public CsvDatasetSteps() {

        Given("read the csv data file for {string} api", (String page) -> {
            this.csvDataSet = csvDataSet(page);
        });
    }

    private CSVDataSet csvDataSet(String page) {
        csvDataSet = new CSVDataSet();
        csvDataSet.setProperty("filename", LoadProperties.getPage(page).getCsvpath());
        csvDataSet.setProperty("variableNames", LoadProperties.getPage(page).getVariables());
        csvDataSet.setProperty("shareMode", "shareMode.all");
        csvDataSet.setProperty("delimiter", ",");
        csvDataSet.setProperty("quotedData", "false");
        csvDataSet.setProperty("recycle", "true");
        csvDataSet.setName("csv data");
        csvDataSet.setProperty(TestElement.TEST_CLASS, CSVDataSet.class.getName());
        csvDataSet.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());
        return csvDataSet;
    }

    public CSVDataSet getcsvDataSet() {
        return csvDataSet;
    }
}
