package net.seensin.springdockerswarmmanagementapi.common.security.controller;


import net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.security.InvalidCredentialException;
import net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.security.UserDisabledException;
import net.seensin.springdockerswarmmanagementapi.common.security.config.common.jwt.JwtRequest;
import net.seensin.springdockerswarmmanagementapi.common.security.config.common.jwt.JwtResponse;
import net.seensin.springdockerswarmmanagementapi.common.security.config.common.jwt.JwtTokenUtil;
import net.seensin.springdockerswarmmanagementapi.common.security.model.service.userdetailservice.SinaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private SinaUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest, HttpServletRequest request , HttpServletResponse response) throws Exception {
        System.out.println("entry authenticate");
        if (response.getStatus() == 200){
            System.out.println("200 authenticate");
            final String token = tokenService(authenticationRequest,request);
            return ResponseEntity.ok(new JwtResponse(token));
        }else {
            System.out.println("authentication failed");
            throw new Exception();
        }
    }

    ////SERVICE///////////////////////////////////////////////////////////////////
    private void authenticate(String username, String password) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new UserDisabledException();
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialException();
        }

    }

    public String tokenService(JwtRequest authenticationRequest, HttpServletRequest request) throws Exception {
        // authenticate user
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        // generate token
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        System.out.println(token + " from controller");

        // add login information to user session
        request.getSession().setAttribute("IP", request.getRemoteAddr());
        request.getSession().setAttribute("TOKEN", token);

        // setAuthentication(usernamePasswordAuthenticationToken)
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(

                userDetails, null, userDetails.getAuthorities());

        usernamePasswordAuthenticationToken

                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

// After setting the Authentication in the context, we specify

// that the current user is authenticated. So it passes the

// Spring Security Configurations successfully.

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        return token;
    }

}