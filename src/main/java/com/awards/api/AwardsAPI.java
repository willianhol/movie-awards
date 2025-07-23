package com.awards.api;

import com.awards.api.dto.ApiResponseAdapter;
import com.awards.api.dto.IntervalResultDTO;
import com.awards.service.AnalysePerformance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/producers")
@Tag(name = "Producers", description = "Producers API")
public class AwardsAPI {

    public final AnalysePerformance service;

    public AwardsAPI(AnalysePerformance service) {
        this.service = service;
    }

    @GET
    @Path("/intervals")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Get the intervals of awards won by producers",
            description = "Returns the minimum and maximum intervals between awards won by producers."
    )
    @APIResponse(responseCode = "200", description = "Intervals calculated successfully",
            content = @org.eclipse.microprofile.openapi.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON ))
    @APIResponse(responseCode = "404", description = "Resource not found",
            content = @org.eclipse.microprofile.openapi.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON))
    @APIResponse(responseCode = "500", description = "Internal server error",
            content = @org.eclipse.microprofile.openapi.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON))
    public IntervalResultDTO getIntervals() {
        return ApiResponseAdapter.convertReturn(service.calculateIntervals());
    }
}
