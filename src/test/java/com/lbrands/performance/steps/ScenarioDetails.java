package com.lbrands.performance.steps;


import cucumber.api.Scenario;
import cucumber.api.java.Before;

public class ScenarioDetails {


    private Scenario scenario;

    @Before
    public void getScenario(Scenario scenario){
        this.scenario = scenario;
    }

    public Scenario getScenario() {
        return scenario;
    }

}
