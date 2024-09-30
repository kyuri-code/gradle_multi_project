package org.example.function;

import java.util.List;

import org.example.entity.SampleEntity;
import org.example.service.DatabaseQueryServicve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Method2 extends AbstractFunction<Void>{

    private static final String PROCESS_NAME = "メソッド２";

    private final String message = "hello world from method2";

    private final Logger logger = LoggerFactory.getLogger(Method2.class);

    private final DatabaseQueryServicve databaseQueryServicve;

    public Method2(DatabaseQueryServicve databaseQueryServicve) {
        super(PROCESS_NAME);
        this.databaseQueryServicve = databaseQueryServicve;
    }

    @Override
    protected Void execute() throws Exception {
        logger.info(message);
        // String name = databaseQueryServicve.getDataFromDatabase();
        // logger.info("name column : " + name);
        List<SampleEntity> samples = databaseQueryServicve.getAllSamples();
        logger.info("name column : " + samples.get(0).getName());
        return null;
    }
}