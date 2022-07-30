package com.example.hellospringbatch.job;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@Slf4j
public class TestJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job testJob(Step testStep) {
        return jobBuilderFactory.get("testJob")
                .incrementer(new RunIdIncrementer())
                .start(testStep)
                .build();
    }

    @JobScope
    @Bean
    public Step testStep(Tasklet testTasklet) {
        return stepBuilderFactory.get("testStep")
                .tasklet(testTasklet)
                .build();
    }

    @StepScope
    @Bean
    public Tasklet testTasklet() {
        return ((contribution, chunkContext) -> {
            log.info("[TestJobConfig] excuted testTasklet");
            return RepeatStatus.FINISHED;
        });
    }
}
