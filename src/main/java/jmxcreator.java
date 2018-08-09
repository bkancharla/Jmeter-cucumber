import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarLog;
import net.lightbody.bmp.core.har.HarNameValuePair;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.CookieManager;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.testelement.property.BooleanProperty;
import org.apache.jmeter.testelement.property.StringProperty;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;

import com.google.common.base.Splitter;

public class jmxcreator {

    public static StandardJMeterEngine jmeter;

    public static void generateJmeterScript(HarLog harLog) throws IOException {
        initializeJMeter();


        TestPlan testPlan = makeTestPlan("Testplan");
        ThreadGroup threadGroup = makeThreadGroup("Thread Gruppe", 30, 1, 2);
        GenericController genericController = makeGenericController("Testskript");
        CookieManager cookieManager = makeCookieManager();

        HashTree testPlanHashTree = new ListedHashTree(testPlan);
        HashTree threadGroupHashTree = new ListedHashTree(threadGroup);
        HashTree genericControllerHashTree = new ListedHashTree(genericController);
        HashTree cookieManagerHashTree = new ListedHashTree(cookieManager);

        for (HarEntry entry : harLog.getEntries()) {
            System.out.println(entry);
            genericControllerHashTree.add(genericController, makeHttpSampler(entry));
        }

        threadGroupHashTree.add(threadGroup, genericControllerHashTree);
        threadGroupHashTree.add(threadGroup, cookieManagerHashTree);
        testPlanHashTree.add(testPlan, threadGroupHashTree);

        writeJMeterFile(testPlanHashTree, "loadScript.jmx");
runJmeter(testPlanHashTree);

    }

    private static void initializeJMeter() {
        jmeter = new StandardJMeterEngine();
        JMeterUtils.loadJMeterProperties("./src/main/java/jmeter.properties");
        JMeterUtils.setJMeterHome("/Users/mmurugaiah/Documents/apache-jmeter-4.0");
        JMeterUtils.initLocale();
        System.out.println("initialized sucessfully");
    }

    private static TestPlan makeTestPlan(String name) {
        TestPlan testPlan = new TestPlan();
        testPlan.setName(name);
        testPlan.setProperty(new BooleanProperty(TestElement.ENABLED, true));
        testPlan.setProperty(new StringProperty(TestElement.GUI_CLASS, "org.apache.jmeter.control.gui.TestPlanGui"));
        testPlan.setProperty(new StringProperty(TestElement.TEST_CLASS, "org.apache.jmeter.testelement.TestPlan"));
        testPlan.setFunctionalMode(false);
        testPlan.setSerialized(false);

        Arguments arguments = new Arguments();
        arguments.setProperty(new BooleanProperty(TestElement.ENABLED, true));
        arguments.setProperty(new StringProperty(TestElement.GUI_CLASS, "org.apache.jmeter.config.gui.ArgumentsPanel"));
        arguments.setProperty(new StringProperty(TestElement.TEST_CLASS, "org.apache.jmeter.config.Arguments"));
        testPlan.setUserDefinedVariables(arguments);
        System.out.println("created test plan sucessfully");
        return testPlan;
    }

    private static ThreadGroup makeThreadGroup(String name, int numberOfThreads, int rampUp, int loops) {
        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setName(name);
        threadGroup.setProperty(new BooleanProperty(TestElement.ENABLED, true));
        threadGroup.setProperty(new StringProperty(TestElement.GUI_CLASS, "org.apache.jmeter.threads.gui.ThreadGroupGui"));
        threadGroup.setProperty(new StringProperty(TestElement.TEST_CLASS, "org.apache.jmeter.threads.ThreadGroup"));
        threadGroup.setNumThreads(numberOfThreads);
        threadGroup.setRampUp(rampUp);

        LoopController loopController = new LoopController();
        loopController.setProperty(new BooleanProperty(TestElement.ENABLED, true));
        loopController.setProperty(new StringProperty(TestElement.GUI_CLASS, "org.apache.jmeter.control.gui.LoopControlPanel"));
        loopController.setProperty(new StringProperty(TestElement.TEST_CLASS, "org.apache.jmeter.control.LoopController"));
        loopController.setLoops(loops);
        loopController.setContinueForever(false);
        threadGroup.setSamplerController(loopController);
        System.out.println("thread group sucessfully");
        return threadGroup;
    }

    private static GenericController makeGenericController(String name) {
        GenericController genericController = new GenericController();
        genericController.setName(name);
        genericController.setProperty(new BooleanProperty(TestElement.ENABLED, true));
        genericController.setProperty(new StringProperty(TestElement.GUI_CLASS, "org.apache.jmeter.control.gui.LogicControllerGui"));
        genericController.setProperty(new StringProperty(TestElement.TEST_CLASS, "org.apache.jmeter.control.GenericController"));
        System.out.println("Genneric controller sucessfully");
        return genericController;
    }

    private static CookieManager makeCookieManager() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setName("Cookie Manager");
        cookieManager.setProperty(new BooleanProperty(TestElement.ENABLED, true));
        cookieManager.setProperty(new StringProperty(TestElement.GUI_CLASS, "org.apache.jmeter.protocol.http.gui.CookiePanel"));
        cookieManager.setProperty(new StringProperty(TestElement.TEST_CLASS, "org.apache.jmeter.protocol.http.control.CookieManager"));
        cookieManager.setClearEachIteration(true);
        System.out.println("Cookie manager created sucessfully");
        return cookieManager;
    }

    private static HashTree makeHttpSampler(HarEntry entry) {
        URL url;
        try {
            url = new URL(entry.getRequest().getUrl());
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + entry.getRequest().getUrl());
            throw new RuntimeException(e);
        }

        HTTPSamplerProxy sampler = new HTTPSamplerProxy();
        sampler.setName(entry.getPageref() + "-" + entry.getRequest().getUrl());
        sampler.setProperty(new BooleanProperty(TestElement.ENABLED, true));
        sampler.setProperty(new StringProperty(TestElement.GUI_CLASS, "org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui"));
        sampler.setProperty(new StringProperty(TestElement.TEST_CLASS, "org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy"));

        sampler.setDomain(url.getHost());
        sampler.setPath(url.getPath());
        sampler.setFollowRedirects(false);
        sampler.setImplementation("HttpClient4");
        sampler.setUseKeepAlive(true);
        sampler.setMethod(entry.getRequest().getMethod());

        if (url.getProtocol().equals("https")) {
            sampler.setProtocol("https");
        }

        if (url.getQuery() != null) {
            sampler.setArguments(makeArguments(url));
        }

        HashTree headerManagerHashTree = makeHeaderManager(entry.getRequest().getHeaders());
        HashTree samplerHashTree = new ListedHashTree(sampler);
        samplerHashTree.add(sampler, headerManagerHashTree);
        System.out.println("Sampler created sucessfully");
        return samplerHashTree;
    }

    private static HashTree makeHeaderManager(List<HarNameValuePair> headers) {
        HeaderManager headerManager = new HeaderManager();
        headerManager.setName("Header Manager");
        headerManager.setProperty(new BooleanProperty(TestElement.ENABLED, true));
        headerManager.setProperty(new StringProperty(TestElement.GUI_CLASS, "org.apache.jmeter.protocol.http.gui.HeaderPanel"));
        headerManager.setProperty(new StringProperty(TestElement.TEST_CLASS, "org.apache.jmeter.protocol.http.control.HeaderManager"));
        for (HarNameValuePair header : headers) {
            if (!"Cookie".equals(header.getName())) {
                headerManager.add(new Header(header.getName(), header.getValue()));
            }
        }
        HashTree headerManagerHashTree = new ListedHashTree(headerManager);
        System.out.println("Header manager sucessfully");
        return headerManagerHashTree;
    }

    private static Arguments makeArguments(URL url) {
        Arguments arguments = new Arguments();
        arguments.setProperty(new BooleanProperty(TestElement.ENABLED, true));
        arguments.setProperty(new StringProperty(TestElement.GUI_CLASS, "org.apache.jmeter.protocol.http.gui.HTTPArgumentsPanel"));
        arguments.setProperty(new StringProperty(TestElement.TEST_CLASS, "org.apache.jmeter.config.Arguments"));

        final Map<String, String> params = Splitter.on('&').trimResults().withKeyValueSeparator("=").split(url.getQuery());

        for (String paramName : params.keySet()) {
            HTTPArgument argument = new HTTPArgument();
            argument.setAlwaysEncoded(true);
            argument.setName(paramName);
            argument.setValue(params.get(paramName));
            argument.setMetaData("=");
            argument.setUseEquals(true);
            arguments.addArgument(argument);
        }
        System.out.println("Arguments added sucessfully");
        return arguments;
    }

    private static void writeJMeterFile(HashTree testPlanHashTree, String filename) throws FileNotFoundException, IOException {
        FileOutputStream jmxFileOut = new FileOutputStream(filename);
        SaveService.saveTree(testPlanHashTree, jmxFileOut);
        jmxFileOut.flush();
        jmxFileOut.close();
        System.out.println("Jmeter file sucessfully");
    }


    private static void runJmeter(HashTree testPlanTree ) {
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

        System.out.println("Result collector have initiated successfully");
        StandardJMeterEngine jmeter = new StandardJMeterEngine();
        jmeter.configure(testPlanTree);
        jmeter.run();

        System.out.println("Test completed. See test.jtl file for results");
        System.out.println("JMeter .jmx script is available at test.jmx");
        System.exit(0);
    }

}

