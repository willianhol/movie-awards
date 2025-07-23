package com.awards.api.exceptions;

import com.awards.exceptions.NoDataException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class NoDataExceptionHandler implements ExceptionMapper<NoDataException> {

    @Override
    public Response toResponse(NoDataException exception) {
        return Response.status(Response.Status.NO_CONTENT)
                .entity(exception.getMessage())
                .build();
    }
}
