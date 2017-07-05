package it.codingjam.configs;

import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.sql.DataSource;

/**
 * See https://projects.spring.io/spring-security-oauth/docs/oauth2.html
 *
 * Created by acomo on 25/03/17.
 */
@Component
@EnableAuthorizationServer
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    @Inject
    private DataSource dataSource;

    @Inject
    private UserDetailsService userDetailsService;

    @Inject
    private TokenStore tokenStore;

    @Inject
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    /**
     * The grant types supported by the AuthorizationEndpoint can be configured via the AuthorizationServerEndpointsConfigurer
     *
     * @param endpoints config
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //endpoints.tokenGranter(new CompositeTokenGranter(grants()));
        endpoints
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);

    }

//    private List<TokenGranter> grants() {
//        return Arrays.asList(
//                new RefreshTokenGranter(servicesConfig.authServerTokenServices(), servicesConfig.clientDetailsService(), servicesConfig.oAuth2RequestFactory()),
//                new AuthorizationCodeTokenGranter(servicesConfig.authServerTokenServices(), servicesConfig.authorizationCodeServices(), servicesConfig.clientDetailsService(), servicesConfig.oAuth2RequestFactory()),
//                new ImplicitTokenGranter(servicesConfig.authServerTokenServices(), servicesConfig.clientDetailsService(), servicesConfig.oAuth2RequestFactory()),
//                new ResourceOwnerPasswordTokenGranter(servicesConfig.authenticationManager(), servicesConfig.authServerTokenServices(), servicesConfig.clientDetailsService(), servicesConfig.oAuth2RequestFactory()),
//                new ClientCredentialsTokenGranter(servicesConfig.authServerTokenServices(), servicesConfig.clientDetailsService(), servicesConfig.oAuth2RequestFactory()));
//    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }
}
