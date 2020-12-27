package net.seensin.springdockerswarmmanagementapi.modules.security.config;

import net.seensin.springdockerswarmmanagementapi.modules.security.config.common.jwt.JwtAuthenticationEntryPoint;
import net.seensin.springdockerswarmmanagementapi.modules.security.config.common.jwt.JwtRequestFilter;
import net.seensin.springdockerswarmmanagementapi.modules.security.model.entity.Role;
import net.seensin.springdockerswarmmanagementapi.modules.security.model.entity.User;
import net.seensin.springdockerswarmmanagementapi.modules.security.model.repository.RoleRepository;
import net.seensin.springdockerswarmmanagementapi.modules.security.model.repository.UserRepository;
import net.seensin.springdockerswarmmanagementapi.modules.security.model.service.userdetailservice.SinaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;
import java.util.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${sinarole.hierarchy}")
    private String ROLE_HIERARCHY ;

    @Value("${sinarole.roles}")
    private Set<String> ROLES;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private SinaUserDetailsService jwtUserDetailsService;

    @Autowired
    UserRepository userRepo;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    @Autowired
    private RoleHierarchy roleHierarchy;

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(ROLE_HIERARCHY);
        return roleHierarchy;
    }


    private SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy);
        return defaultWebSecurityExpressionHandler;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource () {

        UrlBasedCorsConfigurationSource source;
        source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration configuration = new CorsConfiguration();
        List<String> all = Collections.singletonList("*");
//        configuration.setAllowedOrigins(all);
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:4200","https://192.168.3.41:8080"));
        configuration.setAllowedMethods(all);
        configuration.setAllowedHeaders(all);
        configuration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public GrantedAuthoritiesMapper authoritiesMapper() {
        SimpleAuthorityMapper authorityMapper = new SimpleAuthorityMapper();
        authorityMapper.setConvertToUpperCase(true);
        authorityMapper.setPrefix("");
        authorityMapper.setDefaultAuthority("USER");
        return authorityMapper;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example
        httpSecurity.csrf().disable().cors().and()
                // dont authenticate this particular request
                .authorizeRequests().antMatchers("/authenticate" , "/*.js" ,"/*.css","/*.favicon.ico","/login" , "/" ,"/index.html","/home/**").permitAll()
//                .antMatchers("/", "/public/**", "/resources/**", "/resources/public/**").permitAll()
                // all other requests need to be authenticated
                .anyRequest().authenticated()
                // make sure we use stateless session; session won't be used to
                // store user's state.

        .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

//         Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }



    @PostConstruct
    void initalUserAndRoles() {

        for (String role : ROLES) {
            if (!roleRepository.existsByRoleName(role)){
                roleRepository.save(new Role(role));
            }
        }

        if (!userRepo.existsByUsername("root")) {
            Role role = roleRepository.findByRoleName("ADMIN");
            userRepo.save(new User("root", "P@$$w0rld", "root","", "", "", "", "", true, true, true, true, new HashSet<Role>(Arrays.asList(role))));
        }
    }
}
