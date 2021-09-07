package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class MyUtil {

    public static void sleep(int millsec){
        try{
            Thread.sleep(millsec);
        }catch (Exception ex){}

    }

    public static <T> void checkExist(Optional<T> checkObj, String customMessage){
        if(!checkObj.isPresent()){
            //log.info("not found emp by {}", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    customMessage);
        }
    }
}
