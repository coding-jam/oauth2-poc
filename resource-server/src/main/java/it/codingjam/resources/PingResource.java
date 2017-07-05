package it.codingjam.resources;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by pizzo on 25/03/17.
 */
@Path("/v1/ping")
@PermitAll
public class PingResource {

    @GET
    public String ping() {
        return "pong";
    }
}
