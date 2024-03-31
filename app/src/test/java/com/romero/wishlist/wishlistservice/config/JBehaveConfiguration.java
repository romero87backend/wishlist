package com.romero.wishlist.wishlistservice.config;


import com.romero.wishlist.wishlistservice.steps.WishlistSteps;
import org.jbehave.core.configuration.MostUsefulConfiguration;

import org.jbehave.core.io.LoadFromClasspath;

import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class JBehaveConfiguration {

    @Bean
    public org.jbehave.core.configuration.Configuration configuration() {
        return new MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath())
                .useStoryReporterBuilder(
                        new StoryReporterBuilder().withDefaultFormats().withFormats(StoryReporterBuilder.ProvidedFormat.CONSOLE, StoryReporterBuilder.ProvidedFormat.TXT));
    }

    @Bean
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new WishlistSteps()); // YourSteps é a classe que contém os passos dos cenários
        //return new InstanceStepsFactory(configuration(), new YourSteps()); // YourSteps é a classe que contém os passos dos cenários
    }
}
