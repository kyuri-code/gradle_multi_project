package org.example.function;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.function.Supplier;

public abstract class AbstractFunction<O> implements Supplier<O>{

    private String processName;

    private LocalDateTime processDate;

    private final Logger looger = LoggerFactory.getLogger(AbstractFunction.class);

    public AbstractFunction(final String processName) {
        this.processName = processName;
    }

    public O get() {
        this.processDate = LocalDateTime.now();
        looger.info(processName + " starts at " + processDate.toString());
        try {
            O output = this.execute();
            return output;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    protected abstract O execute() throws Exception;
}
