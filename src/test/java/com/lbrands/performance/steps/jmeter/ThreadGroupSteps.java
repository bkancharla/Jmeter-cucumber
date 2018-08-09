package com.lbrands.performance.steps.jmeter;

import cucumber.api.java8.En;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;

public class ThreadGroupSteps implements En {

    private ThreadGroup threadGroup;


    public ThreadGroupSteps() {

        Given("set the ThreadCount to {int} and rampUp to {int} seconds", (Integer threadCount, Integer rampUp) -> {
            threadGroup = new ThreadGroup();
            threadGroup.setName("Sample Thread Group");
            threadGroup.setNumThreads(threadCount);
            threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
            threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());
            threadGroup.setRampUp(rampUp);
        });


    }

    public ThreadGroup getThreadGroup() {
        return threadGroup;
    }
}



