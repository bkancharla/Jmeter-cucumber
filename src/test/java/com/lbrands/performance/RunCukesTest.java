package com.lbrands.performance;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com/lbrands/performance/steps",
        tags = {"@exec","not @ignore", "not @wip"}
)
public class RunCukesTest {
}