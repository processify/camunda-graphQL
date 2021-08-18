package org.camunda.bpm.extension.graphql.common;

import graphql.kickstart.spring.error.ThrowableGraphQLError;

public class CustomGraphQLError extends ThrowableGraphQLError {
    private static final long serialVersionUID = 1L;

    private String type;

    public CustomGraphQLError(Throwable throwable, String message, String type) {
        super(throwable, message);

        this.type = type;
    }

    @Override
    public String getType() {
        return type == null ? super.getType() : type;
    }
}