package com.example.admin.exception;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class ExceptionHandling {

	@org.springframework.web.bind.annotation.ExceptionHandler(value =BadCredentialsException.class)
	public ErrorResponse badCredicialException(BadCredentialsException badCredicialException)
	{
	return new ErrorResponse("Bad credencials",403);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(value = ExpiredJwtException.class)
	public ErrorResponse expiredjwtTocken(ExpiredJwtException expiredJwtException)
	{
		return new ErrorResponse("jwt tocken expired..",404);
	}
	
//	@org.springframework.web.bind.annotation.ExceptionHandler(value = NoResourceFoundException.class)
//	public ErrorResponse noResource(NoResourceFoundException noResourceFoundException)
//	{
//		return new ErrorResponse("NoResourceFoundException.. ",404);
//	}
}
