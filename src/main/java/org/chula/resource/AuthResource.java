package org.chula.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.chula.client.KeycloakServiceClient;
import org.chula.dto.AuthRequest;
import org.chula.dto.KeycloakTokenResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

@Path("/v2/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

  @Inject
  @RestClient
  KeycloakServiceClient keycloakServiceClient;

  @POST
  @Path("/token")
  public Response getToken(AuthRequest request) {
    try {
      KeycloakTokenResponse tokenResponse = keycloakServiceClient.getToken("password", "backend-service", "secret",
          request.getUsername(), request.getPassword());
      return Response.ok(tokenResponse).build();
    } catch (ClientWebApplicationException e) {
      if (e.getResponse().getStatus() == 401) {
        return Response.status(Response.Status.UNAUTHORIZED)
            .entity("{\"error\": \"Invalid username or password\"}")
            .build();
      }
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity("{\"error\": \"An unexpected error occurred\"}")
          .build();
    }
  }

}
