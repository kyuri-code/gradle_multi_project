package org.example.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Method1 extends AbstractFunction<Void>{
    
    private static final String PROCESS_NAME = "メソッド１";

    private final String message = "hello world from method1";

    private final Logger logger = LoggerFactory.getLogger(Method1.class);

    public Method1() {
        super(PROCESS_NAME);
    }

    @Override
    protected Void execute() throws Exception {
        logger.info(message);
        return null;
    }
}
