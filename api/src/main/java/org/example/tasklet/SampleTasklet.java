package org.example.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

public class SampleTasklet implements Tasklet, StepExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(SampleTasklet.class);

    @Value("${custom.app.message}")
    private String appData;

    @Value("${custom.module.message}")
    private String moduleData;

    @Override
    public RepeatStatus execute(final StepContribution contribution, final ChunkContext chunkContext) throws Exception {
        logger.info("-----ChunkContext-----");
        logger.info(chunkContext.getStepContext().getStepName());
        logger.info("appData : " + appData);
        logger.info("moduleData : " + moduleData);
        return RepeatStatus.FINISHED;
    }

    @Override
    public void beforeStep(final StepExecution stepExecution) {
        logger.info("logging before tasklet");
    }

    @Override
    public ExitStatus afterStep(final StepExecution stepExecution) {
        ExitStatus exitStatus = ExitStatus.COMPLETED;
        logger.info("loggin after tasklet end step :(" + exitStatus.getExitCode() + ")");

        return exitStatus;
    }


}
