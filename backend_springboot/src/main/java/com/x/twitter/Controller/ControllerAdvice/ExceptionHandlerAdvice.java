package com.x.twitter.Controller.ControllerAdvice;

import com.x.twitter.Exceptions.TweetNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(TweetNotFoundException.class)
    public ResponseEntity<String> handleTweetNotFoundException(){
        return new ResponseEntity<String>("tweet not found",HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<CustomExceptionResponse> handleUsernameNotFoundException(){
        return new ResponseEntity<CustomExceptionResponse>(new CustomExceptionResponse(404,"Username cant be found"),HttpStatus.NOT_FOUND);
    }
}
