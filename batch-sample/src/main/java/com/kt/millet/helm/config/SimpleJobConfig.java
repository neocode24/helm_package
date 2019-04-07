package com.kt.millet.helm.config;

import com.kt.millet.helm.bean.BatchBean;
import com.kt.millet.helm.repository.FieldBatchRepository;
import com.kt.millet.helm.repository.FieldRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.StreamSupport;

@Slf4j
@Configuration
public class SimpleJobConfig {

    @Autowired
    FieldRepository fieldRepository;

    @Autowired
    FieldBatchRepository    batchRepository;


    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public SimpleJobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob")
                .start(simpleStep1(null))
                .next(simpleStep2())
                .build();
    }

    @Bean
    @JobScope
    public Step simpleStep1(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("simpleStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.debug("step1");
                    log.debug("requestData:" + requestDate);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step simpleStep2() {
        return stepBuilderFactory.get("simpleStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    log.debug("step2");

                    long count = StreamSupport.stream(fieldRepository.findAll().spliterator(), false).count();
                    batchRepository.save(BatchBean.builder().count(count).build());

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
