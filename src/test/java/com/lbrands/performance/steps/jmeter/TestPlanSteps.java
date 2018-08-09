package com.lbrands.performance.steps.jmeter;

import com.lbrands.performance.steps.ScenarioDetails;
import cucumber.api.java8.En;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.picocontainer.annotations.Inject;

import java.io.File;
import java.io.FileOutputStream;

public class TestPlanSteps implements En {

    @Inject
    private HttpHeaderSteps headerSteps;

    @Inject
    private LoopControlerSteps loopControlerSteps;

    @Inject
    private ThreadGroupSteps threadGroupSteps;

    @Inject
    private HttpSamplerSteps httpSamplerSteps;

    private StandardJMeterEngine jmeter = new StandardJMeterEngine();

    @Inject
    private ScenarioDetails scenario;

    @Inject
    private SummarySteps summary;
    @Inject
    private HttpCookieManagerSteps cookieManagerSteps;
    @Inject
    private CsvDatasetSteps csvDatasetSteps;


    private HashTree httpRequestTree = new HashTree();

    public TestPlanSteps() {

        Given("add sampler request to TestPlan", () -> {
            httpRequestTree.add(httpSamplerSteps.getHttpRequest());
        });


        Given("add CookieManager to TestPlan", () -> {
            httpRequestTree.add(cookieManagerSteps.getCookie());

        });
        Given("add HeaderManager to TestPlan", () -> {
            httpRequestTree.add(headerSteps.getManager());

        });
        Given("add CsvDataset to the TestPlan", () -> {
            httpRequestTree.add(csvDatasetSteps.getcsvDataSet());

        });


        Then("run this script", () -> {
            TestPlan testPlan = getTestPlan();
            HashTree testPlanTree = new HashTree();
            testPlanTree.add(testPlan);
            HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroupSteps.getThreadGroup());
            threadGroupHashTree.add(httpRequestTree);
            threadGroupSteps.getThreadGroup().setSamplerController(loopControlerSteps.getLoopController());
            setDefaultJmeterProperties();
            SaveService.saveTree(testPlanTree, new FileOutputStream("jmeter_apiq_sample.jmx"));
            testPlanTree.add(testPlanTree.getArray()[0], summary.getSummarizer());
            jmeter.configure(testPlanTree);
            jmeter.run();
        });
    }

    private void setDefaultJmeterProperties() {
        File jmeterHome = new File("/Users/mmurugaiah/Documents/apache-jmeter-4.0");

        JMeterUtils.setJMeterHome(jmeterHome.getPath());
        JMeterUtils.loadJMeterProperties("./src/main/java/jmeter.properties");
        JMeterUtils.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        JMeterUtils.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
    }

    private TestPlan getTestPlan() {
        TestPlan testPlan = new TestPlan(scenario.getScenario().getName());

        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        return testPlan;
    }
}
