package org.chula.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.FormParam;
import org.chula.dto.KeycloakTokenResponse;

@RegisterRestClient(configKey = "keycloak-api")
public interface KeycloakServiceClient {

  @POST
  @Path("/realms/quarkus/protocol/openid-connect/token")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  KeycloakTokenResponse getToken(@FormParam("grant_type") String grantType,
                                 @FormParam("client_id") String clientId,
                                 @FormParam("client_secret") String clientSecret,
                                 @FormParam("username") String username,
                                 @FormParam("password") String password);
}
