package org.camunda.bpm.extension.graphql.subscription;
import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.extension.graphql.common.Util;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;

import java.util.*;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class TaskSubscriptionResolver implements GraphQLSubscriptionResolver {

    private final static Logger LOGGER = Logger.getLogger(TaskSubscriptionResolver.class.getName());


    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    public TaskSubscriptionResolver() {

    }

    public Publisher<List<Task>> tasksubscription(){
        LOGGER.info("Subscriber scanner is called 1" );
        TaskQuery taskQuery = taskService.createTaskQuery();
        LOGGER.info("Subscriber scanner is called 2" );

        return  subscriber -> Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            LOGGER.info("Subscriber scanner is called 3" );
          //  taskQuery.taskName("Approve Invoice");
            taskQuery.initializeFormKeys();
            LOGGER.info("Task Query Init " );
          //Get all task
            //  List<Task> tasks= taskQuery.list();
            //Get only unassigned task
            List<Task> tasks= taskQuery.taskUnassigned().list();
            LOGGER.info("Task Query executed" );
            subscriber.onNext(tasks);
        },0,2, TimeUnit.SECONDS);
    }

    public Publisher<List<Task>> recentassignedtasks(){
        LOGGER.info("Subscriber scanner is called 1" );
        TaskQuery taskQuery = taskService.createTaskQuery();
        LOGGER.info("Subscriber scanner is called 2" );


        return  subscriber -> Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {

            LOGGER.info("Subscriber scanner is called 3" );
            //  taskQuery.taskName("Approve Invoice");
            taskQuery.initializeFormKeys();
            LOGGER.info("Task Query Init " );
            //Get all task
            //  List<Task> tasks= taskQuery.list();
            //Get only unassigned task
            List<Task> unassignedTasks= taskQuery.taskUnassigned().list();

            Collections.sort(unassignedTasks, new Comparator<Task>() {
                public int compare(Task o1, Task o2) {
                    return o2.getCreateTime().compareTo(o1.getCreateTime());
                }
            });



            List<Task> recentUnassignedTasks = unassignedTasks.stream().limit(8).collect(Collectors.toList());

            LOGGER.info("Task Query executed" );
            subscriber.onNext(recentUnassignedTasks);

        },0,2, TimeUnit.SECONDS);
    }
}
