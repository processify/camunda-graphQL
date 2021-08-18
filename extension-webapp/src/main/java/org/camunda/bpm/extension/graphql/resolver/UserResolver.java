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
import org.camunda.bpm.engine.identity.UserQuery;
import java.util.List;
import java.util.logging.Logger;

@Component
public class UserResolver implements GraphQLQueryResolver {

    private final static Logger LOGGER = Logger.getLogger(UserResolver.class.getName());


    @Autowired
    IdentityService identityService;

    public UserResolver() {

    }

    public User user(String userId) {
        LOGGER.info("User Resolver is called  . User ID is " + userId);
        if(userId != null) {
            return identityService.createUserQuery().userId(userId).singleResult();
        } else {
            return null;
        }
    }

    public List<User> users(String firstName, String firstNameLike, String groupId){
        UserQuery query = identityService.createUserQuery();
        query = (firstName != null)      ? query.userFirstName(firstName)           : query;
        query = (firstNameLike != null)  ? query.userFirstNameLike(firstNameLike)   : query;
        query = (groupId != null)  ? query.memberOfGroup(groupId)       : query;

        return query.list();
    }

    public List<Group> groups(User user) {
        GroupQuery groupQuery = identityService.createGroupQuery();
        groupQuery.groupMember(user.getId());
        return groupQuery.list();
    }

    public Group group(String groupId) {
        if(groupId != null) {
            return identityService.createGroupQuery().groupId(groupId).singleResult();
        } else {
            return null;
        }
    }

    public List<Group> groups(String groupName, String groupNameLike, String groupType) {
        GroupQuery query = identityService.createGroupQuery();
        query = (groupName != null)      ? query.groupName(groupName)          : query;
        query = (groupNameLike != null)  ? query.groupNameLike(groupNameLike)  : query;
        query = (groupType != null)      ? query.groupType(groupType)          : query;
        return query.list();
    }

}
