package it.codingjam.config;

import it.codingjam.resources.PingResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.springframework.stereotype.Component;

/**
 * Created by acomo on 25/03/17.
 */
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        packages(PingResource.class.getPackage().getName());
        register(RolesAllowedDynamicFeature.class);
    }

}
