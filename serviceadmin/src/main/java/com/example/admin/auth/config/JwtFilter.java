package com.example.admin.auth.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.admin.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter  extends OncePerRequestFilter
{
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private ApplicationContext applicationContext;
	//get details from user details class
		public UserDetails getUserDetails()
		{
		System.out.println("Display Name:"+ applicationContext.getDisplayName());
		return applicationContext.getBean(UserDetails.class);
		}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("do filter work..");
		try 
		{
			System.out.println("do filter internal");
			String authHeader= request.getHeader("Authorization");
			String token=null;
			String userName=null;
			
			//System.out.println("auth header..."+authHeader);
			if(authHeader!=null && authHeader.startsWith("Bearer ")) //header prefix Bearer
			{
				token = authHeader.substring(7);//remove bearer
				//System.out.println("tocken..."+token);
	            // Extracting username from the token
	            userName =applicationContext.getBean(JwtService.class).extractUserName(token);
	            		//jwtService.extractUserName(token);
			}
			
			// If username is extracted and there is no authentication in the current SecurityContext
	        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	        	
	        	//check the username should not null and securitycontext should be null
	        	
	            // Loading UserDetails by username extracted from the token
	            UserDetails userDetails = applicationContext.getBean(CustomUserService.class).loadUserByUsername(userName);
	            
	           // System.out.println(userDetails);
	            // Validating the token with loaded UserDetails
	            if (jwtService.verifyTocken(token, userDetails)) {
	            	
	                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	             
	                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                
	                SecurityContextHolder.getContext().setAuthentication(authToken);
	            }
	        }
	        filterChain.doFilter(request, response);	
		} catch (Exception e) {
			System.err.println(e);
			System.out.println("catch block from jwt filter..");
			response.setContentType("application/json");
			new ObjectMapper().writeValue(response.getOutputStream(), new ErrorResponse(e.getMessage(), 404));
		}
		
	}

	
	
}
