package com.lbrands.performance.steps.jmeter;

import cucumber.api.java8.En;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.testelement.TestElement;

public class LoopControlerSteps implements En {
    private LoopController loopController;


    public LoopControlerSteps() {
        getDefaultLoopController();
        Given("repeat the script in {int} loops", (Integer loops) -> {
            this.loopController.setLoops(loops);

        });

    }

    private void getDefaultLoopController() {
        loopController = new LoopController();
        loopController.setFirst(true);
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
        loopController.initialize();
    }

    public LoopController getLoopController() {
        return loopController;
    }
}
