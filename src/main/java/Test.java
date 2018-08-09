import java.io.File;
import java.io.FileOutputStream;

import org.apache.jmeter.assertions.ResponseAssertion;
import org.apache.jmeter.assertions.gui.AssertionGui;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSampleResult;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

public class Test {

    public static void main(String[] argv) throws Exception {

        //Set jmeter home for the jmeter utils to load
        File jmeterHome = new File("/path/apache-jmeter-4.0");
        String slash = System.getProperty("file.separator");

        if (jmeterHome.exists()) {
            File jmeterProperties = new File(jmeterHome.getPath() + slash + "bin" + slash + "jmeter.properties");
            if (jmeterProperties.exists()) {
                //JMeter Engine
                StandardJMeterEngine jmeter = new StandardJMeterEngine();

                //JMeter initialization (properties, log levels, locale, etc)
                JMeterUtils.setJMeterHome(jmeterHome.getPath());
                JMeterUtils.loadJMeterProperties("./src/main/java/jmeter.properties");
                JMeterUtils.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
                JMeterUtils.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
                JMeterUtils.setProperty("jmeter.save.saveservice.output_format","xml");
                JMeterUtils.setProperty("jmeter.save.saveservice.response_data","true");


                // JMeter Test Plan, basically JOrphan HashTree
                HashTree testPlanTree = new HashTree();

                // First HTTP Sampler - open uttesh.com
                HTTPSamplerProxy httpRequest = new HTTPSamplerProxy();
                httpRequest.setDoMultipartPost(false);
                httpRequest.setDoMultipartPost(false);
                httpRequest.setDomain("api.xyz.com");
                httpRequest.setProtocol("https");
                httpRequest.setPath("/collections/v2/xyz/page");
                httpRequest.setMethod("GET");
                httpRequest.setAutoRedirects(false);
                httpRequest.setName("Collection page");
                httpRequest.setFollowRedirects(true);

                httpRequest.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
                httpRequest.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

// header

                HeaderManager manager = new HeaderManager();
                manager.setName("header Info");
                manager.add(new Header("Origin", "apitest.victoriassecret.com"));
                manager.setEnabled(true);
                manager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
                manager.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());


                //Response assertion
                ResponseAssertion rs = new ResponseAssertion();
                rs.setTestFieldResponseCode();
                rs.addTestString("200");
                rs.isTestFieldResponseCode();
                rs.setCustomFailureMessage("hello");
                rs.setProperty(TestElement.TEST_CLASS, ResponseAssertion.class.getName());
                rs.setProperty(TestElement.GUI_CLASS,AssertionGui.class.getName());
                rs.setTestFieldResponseCode();



                // Loop Controller
                LoopController loopController = new LoopController();
                loopController.setLoops(2);
                loopController.setFirst(true);
                loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
                loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
                loopController.initialize();

                // Thread Group
                ThreadGroup threadGroup = new ThreadGroup();
                threadGroup.setName("Sample Thread Group");
                threadGroup.setNumThreads(1);
                threadGroup.setRampUp(1);
                threadGroup.setSamplerController(loopController);
                threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
                threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());

                // Test Plan
                TestPlan testPlan = new TestPlan("Create JMeter Script From Java Code");

                testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
                testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());

                // HTTP Request Sampler and Header Manager
                HashTree httpRequestTree = new HashTree();
                httpRequestTree.add(httpRequest, manager);
                httpRequestTree.add(rs);

                // Construct Test Plan from previously initialized elements
                testPlanTree.add(testPlan);
                HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
                threadGroupHashTree.add(httpRequestTree);


                // save generated test plan to JMeter's .jmx file format
                SaveService.saveTree(testPlanTree, new FileOutputStream("jmeter_api1_sample.jmx"));

                //add Summarizer output to get test progress in stdout like:
                // summary =      2 in   1.3s =    1.5/s Avg:   631 Min:   290 Max:   973 Err:     0 (0.00%)
                Summariser summer = null;
                String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
                if (summariserName.length() > 0) {
                    summer = new Summariser(summariserName);
                }



                // Store execution results into a .jtl file, we can save file as csv also
                String reportFile = "report.jtl";
                String csvFile = "report.csv";
                ResultCollector logger = new ResultCollector(summer);
                logger.setFilename(reportFile);
                ResultCollector csvlogger = new ResultCollector(summer);
                csvlogger.setFilename(csvFile);
                testPlanTree.add(testPlanTree.getArray()[0], logger);
                testPlanTree.add(testPlanTree.getArray()[0], csvlogger);
                // Run Test Plan
                jmeter.configure(testPlanTree);
                jmeter.run();
                JMeterUtils.setProperty("jmeter.save.saveservice.response_data","false");
//assertions
                System.out.println("Test completed. See " + jmeterHome + slash + "report.jtl file for results");
                System.out.println("JMeter .jmx script is available at " + jmeterHome + slash + "jmeter_api1_sample.jmx");
                System.exit(0);

            }
        }


    }
}
