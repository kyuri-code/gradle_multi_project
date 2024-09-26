package org.example.config;

import org.example.tasklet.SampleTasklet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.listener.JobParameterExecutionContextCopyListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

// @Configuration
// @Import({NoArgsFunction.class})
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        // Using in-memory H2 database to simulate no persistence
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
                .build();
    }

    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    Job job(final JobRepository jobRepository,
            @Qualifier("step") final Step step) {
        logger.info("job");
        return new JobBuilder("job", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    Step step(final JobRepository jobRepository,
              @Qualifier("sampleTasklet") final Tasklet sampleTasklet,
              final PlatformTransactionManager transactionManager) {
        logger.info("step");
        logger.info(sampleTasklet.getClass().getName());
        return new StepBuilder("step", jobRepository)
                .tasklet(sampleTasklet, transactionManager)
                .listener(new JobParameterExecutionContextCopyListener())
                .build();
    }

    @Bean
    SampleTasklet sampleTasklet() {
        return new SampleTasklet();
    }
}