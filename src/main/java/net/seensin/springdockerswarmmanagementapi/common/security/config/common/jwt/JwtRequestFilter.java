package net.seensin.springdockerswarmmanagementapi.common.security.config.common.jwt;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.SneakyThrows;
import net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.security.BearerException;
import net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.security.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws SignatureException {
        System.out.println("#######################NEW_REQUEST#######################");

        String requestUri = request.getRequestURI();
        System.out.println("["+request.getMethod()+"] "+requestUri);

        if (!requestUri.equals("/authenticate")) {
            nonAuthenticateRequestFilter(request,response,chain);
        } else {
            if (SecurityContextHolder.getContext().getAuthentication() != null)
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "User Already Logged In");


            chain.doFilter(request, response);
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    public void nonAuthenticateRequestFilter(HttpServletRequest request, HttpServletResponse response , FilterChain chain) {

        final String requestTokenHeader = request.getHeader("Authorization");

        try {
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

                String jwtToken = requestTokenHeader.substring(7);

                    // jwt token is available,not expired and username extracted from it , so now we need to validate jwt then check user session in compatible with this jwt and request ip
                    if (isValidCredential(jwtToken,request).equals(true)) {
                        // user access approved
                        chain.doFilter(request, response);
                    }else {
                        throw new UnauthorizedException();
                    }

            } else {
                System.out.println("[ACCESS DENIED] JWT Token does not begin with Bearer String");
                throw new BearerException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("[ACCESS DENIED] Unable to get JWT Token");
            resolver.resolveException(request, response, null, e);
        } catch (ExpiredJwtException e) {
            System.out.println("[ACCESS DENIED] JWT Token has expired");
            resolver.resolveException(request, response, null, e);
        }catch (SignatureException e){
            System.out.println("[ACCESS DENIED] JWT Token not correct");
            resolver.resolveException(request, response, null, e);
        }catch (UnauthorizedException e){
            System.out.println("Unauthorized");
            resolver.resolveException(request, response, null, e);
        } catch (Exception e){
            System.out.println("Unauthorized :: " + e.getClass().getName());
            resolver.resolveException(request, response, null, e);
        }

    }





    //[PER REQUEST] validate user credential's (session - jwt)
    public Boolean isValidCredential(String jwtToken , HttpServletRequest request) throws Exception {
// Once we get the token and session .. validate it.

            // means user no already registered and using an un authenticated session
            // may be session fixation attack
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                System.out.println("[SUSPICIOUS REQUEST]-["+request.getRemoteAddr()+"] not a authenticated session but jwt is acceptable ");
                return false;
            }else {
                // get UserDetails from SecurityContextHolder
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (jwtTokenUtil.validateToken(jwtToken, userDetails )){
                    // jwt is valid , now check the session
                    String sessionIp = (String) request.getSession().getAttribute("IP");
                    String sessionJwt = (String) request.getSession().getAttribute("TOKEN");
                    if (sessionIp != null && sessionJwt != null && sessionIp.equals(request.getRemoteAddr()) && sessionJwt.equals(jwtToken)){
                        System.out.println("[SESSION VALIDATION APPROVED]");
                        return true;
                    }else {
                        System.out.println("[SUSPICIOUS REQUEST]-["+request.getRemoteAddr()+"] user ip address or jwt not match");
                        System.out.println("-------details:");
                        System.out.println("---------------storedIp ["+sessionIp+"] != requestIp["+request.getRemoteAddr()+"]");
                        System.out.println("---------------storedJwt["+sessionJwt+"] != requestJWT["+jwtToken+"]");
                        return false;
                    }
                }else {
                    System.out.println("[ERROR] jwt is not correct");
                    return false;
                }

            }
    }

}

