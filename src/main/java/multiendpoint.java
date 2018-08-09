import org.apache.commons.lang.ObjectUtils;
import org.apache.jmeter.assertions.ResponseAssertion;
import org.apache.jmeter.assertions.gui.AssertionGui;
import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.modifiers.BSFPreProcessor;
import org.apache.jmeter.modifiers.BeanShellPreProcessor;
import org.apache.jmeter.protocol.http.control.Cookie;
import org.apache.jmeter.protocol.http.control.CookieManager;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.gui.CookiePanel;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.testelement.property.TestElementProperty;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;
//import org.apache.jorphan.collections.HashTree;


import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class multiendpoint {

    public static void main(String[] argv) throws Exception {

        //Set jmeter home for the jmeter utils to load
        File jmeterHome = new File("/users/bkancharla/Documents/apache-jmeter-4.0");
        String slash = System.getProperty("file.separator");

        if (jmeterHome.exists()) {
            File jmeterProperties = new File(jmeterHome.getPath() + slash + "bin" + slash + "jmeter.properties");
            if (jmeterProperties.exists()) {
                //JMeter Engine
                StandardJMeterEngine jmeter = new StandardJMeterEngine();
                Cookie ck = new Cookie();
                ck.setName("Cookie");
                ck.setValue("test_token=ew0KICAic2lkIjogIlRlc3RpbmciLA0KICAiYWlkIjogImFpZCIsDQogICJ1cyI6ICJTaWduZWRJbiIsDQogICJmaXJzdE5hbWUiOiAiVGVzdGluZyIsDQogICJleHAiOiAxNTE1Njk3MjEwLA0KICAiaWF0IjogMTUxNjYxMDgxMCwNCiAgImlzcyI6ICJNQVNUIC0gTCBCUkFORFMiDQp9");

                //JMeter initialization (properties, log levels, locale, etc)
                JMeterUtils.setJMeterHome(jmeterHome.getPath());
                JMeterUtils.loadJMeterProperties("./src/main/java/jmeter.properties");
                JMeterUtils.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
                JMeterUtils.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());

                // JMeter Test Plan, basically JOrphan HashTree
                HashTree testPlanTree = new HashTree();

                // First HTTP Sampler - open uttesh.com
                HTTPSamplerProxy httpRequest = new HTTPSamplerProxy();
                httpRequest.setDoMultipartPost(false);
                httpRequest.setDoMultipartPost(false);
                httpRequest.setDomain("api.victoriassecret.com");
                httpRequest.setProtocol("https");
                httpRequest.setPath("/auth/v1/create-account-vs");
                httpRequest.setMethod("POST");
                httpRequest.setAutoRedirects(false);
                httpRequest.setName("Create account page");
                httpRequest.setFollowRedirects(false);
                httpRequest.setPostBodyRaw(true);
                Arguments args = new Arguments();
                // args.addArgument(arg);
                httpRequest.hasArguments();
                HTTPArgument httparg = new HTTPArgument();
                httparg.setValue("{\"email\":\"${username}\",\"password\":\"${password}\",\"firstName\":\"Victoria\",\"countryCode\":\"US\",\"channel\":\"VSWEB\",\"recaptchaToken\":\"o9asfdno92hasdfo9u235\"}");
                args.addArgument(httparg);
                httpRequest.setArguments(args);
                httpRequest.setPostBodyRaw(true);
                httpRequest.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
                httpRequest.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());


//Header manager for delete
                HeaderManager managertwo = new HeaderManager();
                managertwo.setName("delete header");
                managertwo.add(new Header("Cookie", "test_token=ew0KICAic2lkIjogIlRlc3RpbmciLA0KICAiYWlkIjogImFpZCIsDQogICJ1cyI6ICJTaWduZWRJbiIsDQogICJmaXJzdE5hbWUiOiAiVGVzdGluZyIsDQogICJleHAiOiAxNTE1Njk3MjEwLA0KICAiaWF0IjogMTUxNjYxMDgxMCwNCiAgImlzcyI6ICJNQVNUIC0gTCBCUkFORFMiDQp9"));
                managertwo.add(new Header("Origin", "apitest.victoriassecret.com"));
                managertwo.setEnabled(true);
                managertwo.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
                managertwo.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());


                //THird Http header for Signout account
                HTTPSamplerProxy signout = new HTTPSamplerProxy();
                signout.setDoMultipartPost(false);
                signout.setDoMultipartPost(false);
                signout.setDomain("api.victoriassecret.com");
                signout.setProtocol("https");
                signout.setPath("/auth/v1/sign-out");
                signout.setMethod("POST");
                signout.setAutoRedirects(false);
                signout.setName("Sign account page");
                signout.setFollowRedirects(false);
                signout.setPostBodyRaw(true);
                signout.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
                signout.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());
                signout.setHeaderManager(managertwo);

                BeanShellPreProcessor bp = new BeanShellPreProcessor();
                System.out.println(bp.getThreadContext().getThreadNum());
                bp.setProperty(TestElement.TEST_CLASS, BeanShellPreProcessor.class.getName());
                bp.setProperty(TestElement.GUI_CLASS, BeanShellPreProcessor.GUI_CLASS);


                //second Http header for delete account

                HTTPSamplerProxy deleteRequest = new HTTPSamplerProxy();
                deleteRequest.setDoMultipartPost(false);
                deleteRequest.setDoMultipartPost(false);
                deleteRequest.setDomain("api.victoriassecret.com");
                deleteRequest.setProtocol("https");
                deleteRequest.setPath("/auth/v1/delete-account?email=" + bp.getThreadContext().getThreadNum());
                deleteRequest.setMethod("DELETE");
                deleteRequest.setAutoRedirects(false);
                deleteRequest.setName("Delete account page");
                deleteRequest.setFollowRedirects(false);
                deleteRequest.setPostBodyRaw(true);
                deleteRequest.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
                deleteRequest.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());
                deleteRequest.setHeaderManager(managertwo);
                // deleteRequest.getCookieManager().remove(0);
                //Cookie Manager


               // cm2.setCookiePolicy("ignoreCookies");
               // deleteRequest.getCookieManager();

               // cm2.setProperty(TestElement.TEST_CLASS, CookieManager.class.getName());
               // cm2.setProperty(TestElement.GUI_CLASS, CookiePanel.class.getName());

                //deleteRequest.setCookieManager(cm2);
                // deleteRequest.setCookieManager(cm1);
              // System.out.println(deleteRequest.getCookieManager());
                System.out.println("Current thread: " + "${__threadNum}");


                HeaderManager manager = new HeaderManager();
                manager.setName("header Info");
                manager.add(new Header("Origin", "apitest.victoriassecret.com"));
                manager.setEnabled(true);
                manager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
                manager.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());


                //Response assertion
                ResponseAssertion rs = new ResponseAssertion();
                rs.setTestFieldResponseCode();
                rs.addTestString("204");
                rs.isTestFieldResponseCode();
                rs.setCustomFailureMessage("Test");
                rs.setProperty(TestElement.TEST_CLASS, ResponseAssertion.class.getName());
                rs.setProperty(TestElement.GUI_CLASS, AssertionGui.class.getName());
                rs.setTestFieldResponseCode();


                //Cookie Manager

                CookieManager cm = new CookieManager();
                //cm.getClearEachIteration();
               // cm.add(ck);

                cm.setProperty(TestElement.TEST_CLASS, CookieManager.class.getName());
                cm.setProperty(TestElement.GUI_CLASS, CookiePanel.class.getName());
                // cm.setProperty(TestElement.GUI_CLASS, Cookie.class.getName());
                CookieManager cm2 = new CookieManager();
                //cm.getClearEachIteration();
                cm2.setName("Cookie");
                cm2.add(ck);

                // Loop Controller
                LoopController loopController = new LoopController();
                loopController.setLoops(1);
                loopController.setFirst(true);
                loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
                loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
                loopController.initialize();

                //parameter and below is the code to help read the csv file
                CSVDataSet csvDataSet =new CSVDataSet();
                String textPath = "/Users/bkancharla/Documents/jmeter-api-performance-poc/test.csv";
                Path path = Paths.get(textPath);
                csvDataSet.setProperty("filename",textPath);
                csvDataSet.setProperty("variableNames","username,password");
              // csvDataSet.setFilename("/Users/mmurugaiah/IdeaProjects/jmeter-api-performance-poc/test.csv");
                csvDataSet.setProperty("shareMode","shareMode.all");
                csvDataSet.setProperty("delimiter",",");
                csvDataSet.setProperty("ignoreFirstLine","false");
                csvDataSet.setProperty("quotedData","false");
                csvDataSet.setProperty("recycle","false");
                System.out.println("Here"+ csvDataSet.getFilename());
                csvDataSet.setName("csv data");
                csvDataSet.setProperty(TestElement.TEST_CLASS, CSVDataSet.class.getName());
                csvDataSet.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());




                // Thread Group
                ThreadGroup threadGroup = new ThreadGroup();
                threadGroup.setName("Sample Thread Group");
                threadGroup.setNumThreads(3);
                threadGroup.setRampUp(10);
                threadGroup.setSamplerController(loopController);
                threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
                threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());


                // Test Plan
                TestPlan testPlan = new TestPlan("Create JMeter Script From Java Code");

                testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
                testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
/*
                HashTree httpRequestTree = new HashTree();
                httpRequestTree.add(cm);
                httpRequestTree.add(httpRequest,manager);
                httpRequestTree.add(deleteRequest,cm2);
                System.out.println(httpRequestTree.list());

*/

                // HTTP Request Sampler and Header Manager
                HashTree httpRequestTree = new HashTree();
               // httpRequestTree.add(httpRequest);
                httpRequestTree.add(httpRequest, manager);
                httpRequestTree.add(httpRequest, rs);
               // httpRequestTree.add(signout);
                httpRequestTree.add(signout, manager);
                httpRequestTree.add(cm);
               // httpRequestTree.add(deleteRequest);
                httpRequestTree.add(deleteRequest, managertwo);

                //  httpRequestTree.add(deleteRequest, cm2);
                //  httpRequestTree.add(deleteRequest, bp);




                //System.out.println(httpRequestTree.getTree(deleteRequest));


                // Construct Test Plan from previously initialized elements
                testPlanTree.add(testPlan);
                HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
                threadGroupHashTree.add(csvDataSet);
                threadGroupHashTree.add(httpRequestTree);
                threadGroupHashTree.add(bp);




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
//assertions
                System.out.println("Test completed. See " + jmeterHome + slash + "report.jtl file for results");
                System.out.println("JMeter .jmx script is available at " + jmeterHome + slash + "jmeter_api1_sample.jmx");
                System.exit(0);

            }
        }


    }
}
