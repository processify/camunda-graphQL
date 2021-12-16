package org.camunda.bpm.extension.graphql.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.GroupQuery;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.GroupQuery;
import org.camunda.bpm.engine.identity.UserQuery;
import java.util.List;
import java.util.logging.Logger;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.extension.graphql.common.Util;

@Component
public class ProcessInstanceQueryResolver implements GraphQLQueryResolver {

    private final static Logger LOGGER = Logger.getLogger(ProcessInstanceQuery.class.getName());


    @Autowired
    RuntimeService runtimeService;

    public ProcessInstanceQueryResolver() {

    }

    public List<ProcessInstance> processInstances(String businessKey,String definitionId) {
    	ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
    	processInstanceQuery = (businessKey != null) ? processInstanceQuery.processInstanceBusinessKey(businessKey):processInstanceQuery;
        processInstanceQuery = (definitionId != null) ? processInstanceQuery.processDefinitionId(definitionId):processInstanceQuery;
        return processInstanceQuery.list();
    }

}
