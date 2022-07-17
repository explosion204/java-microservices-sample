package com.epam.microserviceslearning.song.component;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:feature",
        glue = "com.epam.microserviceslearning.song.component"
)
public class SongServiceTest {
}
