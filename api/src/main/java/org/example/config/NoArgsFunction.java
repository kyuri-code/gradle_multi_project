package org.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.UUID;
import java.util.function.Supplier;

@Configuration
public class NoArgsFunction {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    Job job;

    @Bean
    public Supplier<Void> nonArgsSupplier() {
        return () -> {
            try {
                JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
                jobParametersBuilder.addString("uuid", UUID.randomUUID().toString());
                TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
                jobLauncher.setJobRepository(jobRepository);
                jobLauncher.afterPropertiesSet();
                jobLauncher.run(job, jobParametersBuilder.toJobParameters());
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        };
    }
}
