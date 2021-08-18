package org.camunda.bpm.extension.graphql.common;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.logging.Logger;

@Component
public class GraphQLExceptionHandlers {

    private final static Logger LOGGER = Logger.getLogger(GraphQLExceptionHandlers.class.getName());

    @ExceptionHandler(Exception.class)
    public CustomGraphQLError handle(Exception exception) {
        exception.printStackTrace();

        LOGGER.info("Detailed Exception is "  + exception) ;

        return new CustomGraphQLError(exception, exception.toString(), "fail");
    }

    
}