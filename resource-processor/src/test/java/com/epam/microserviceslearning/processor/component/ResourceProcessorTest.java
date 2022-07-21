package com.epam.microserviceslearning.processor.component;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:feature",
        glue = "com.epam.microserviceslearning.processor.component"
)
public class ResourceProcessorTest {

}
