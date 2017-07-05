package it.codingjam.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.sql.DataSource;

/**
 * Created by acomo on 26/03/17.
 */
@Component
public class OAuth2ServicesConfig {

    @Inject
    private DataSource dataSource;

//    @Inject
//    public AuthorizationServerTokenServices authServerTokenServices() {
//        return new DefaultTokenServices();
//    }

    @Inject
    public ClientDetailsService clientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

//    @Inject
//    public OAuth2RequestFactory oAuth2RequestFactory() {
//        return new DefaultOAuth2RequestFactory(clientDetailsService());
//    }

    @Inject
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        authenticationManager.setTokenServices(tokenServices);
        authenticationManager.setClientDetailsService(clientDetailsService());
        authenticationManager.setResourceId("auth-server");

        tokenServices.setAuthenticationManager(authenticationManager);
        tokenServices.setClientDetailsService(clientDetailsService());
        tokenServices.setTokenStore(tokenStore());
        return authenticationManager;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

}
