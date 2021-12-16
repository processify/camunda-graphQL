package org.camunda.bpm.extension.graphql.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.TaskService;
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
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.extension.graphql.types.KeyValuePair;
import java.util.Collection;
import org.camunda.bpm.application.ProcessApplicationContext;

@Component
public class TaskQueryResolver implements GraphQLQueryResolver {

    private final static Logger LOGGER = Logger.getLogger(TaskQuery.class.getName());


    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    public TaskQueryResolver() {

    }

    public List<Task> tasks(int offset, int limit, String assignee, String name, String nameLike) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery = (assignee != null) ? taskQuery.taskAssignee(assignee):taskQuery;
        taskQuery = (name != null) ? taskQuery.taskName(name):taskQuery;
        taskQuery = (nameLike != null) ? taskQuery.taskNameLike(nameLike):taskQuery;
        taskQuery.initializeFormKeys();
        return taskQuery.listPage(offset,limit);
    }

    public Task task(String id) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery = taskQuery.taskId(id);
        taskQuery.initializeFormKeys();
        return taskQuery.singleResult();
    }

    public List<KeyValuePair> taskVariables(String taskId, Collection<String> names ) {
        List<KeyValuePair> keyValuePairs;

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String pdid = task.getProcessDefinitionId();
        if (pdid == null)
            return null;

        try {
            //Util.switchContext(repositoryService, pdid, processEngineConfiguration);
            VariableMap variableMap = taskService.getVariablesTyped(taskId, names, true);
            keyValuePairs = Util.getKeyValuePairs(variableMap);

        } finally {
            ProcessApplicationContext.clear();
        }

        return keyValuePairs;
    }

}
