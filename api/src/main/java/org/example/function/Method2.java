package org.example.function;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Method2 extends AbstractFunction<Void>{
// public class Method2 implements Supplier<Void>{

    private static final String PROCESS_NAME = "メソッド２";

    private final String message = "hello world from method2";

    private final Logger logger = LoggerFactory.getLogger(Method2.class);

    @Override
    public Void get() {
        logger.info(message);
        return null;
    }

    public Method2() {
        super(PROCESS_NAME);
    }

    @Override
    protected Void execute() throws Exception {
        logger.info(message);
        return null;
    }
}