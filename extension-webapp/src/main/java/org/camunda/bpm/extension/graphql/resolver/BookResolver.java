package org.camunda.bpm.extension.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.camunda.bpm.extension.graphql.model.Book;
import org.springframework.stereotype.Component;
import org.camunda.bpm.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
//import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import java.util.List;
import java.util.logging.Logger;

@Component
public class BookResolver implements GraphQLQueryResolver {

    private final static Logger LOGGER = Logger.getLogger(BookResolver.class.getName());

    @Autowired
    ProcessEngine processEngine;

    

    public Book getBook(String isbn){
        LOGGER.info("Book Resolver is called " );
        return new Book("test Title", "test ISBN");
    }
}
