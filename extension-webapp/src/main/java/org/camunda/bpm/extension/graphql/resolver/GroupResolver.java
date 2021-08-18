package org.camunda.bpm.extension.graphql.resolver;

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
import java.util.List;
import java.util.logging.Logger;
import com.coxautodev.graphql.tools.GraphQLResolver;

@Component
public class GroupResolver implements GraphQLResolver<Group> {

    private final static Logger LOGGER = Logger.getLogger(GroupResolver.class.getName());

   

    @Autowired
    IdentityService identityService;

    public GroupResolver() {

    }




    public List<User> members (Group group) {

        String groupId = group.getId();
        LOGGER.info("groupId: " + groupId);

        if(groupId != null) {
            List<User> members = identityService.createUserQuery()
                    .memberOfGroup(groupId)
                    .list();
            return members;
        } else {
            return null;
        }


    }

}
