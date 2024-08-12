package com.TraperRoku.shorteringAPI.controller;

import com.TraperRoku.shorteringAPI.dto.UrlDto;
import com.TraperRoku.shorteringAPI.dto.UrlErrorDto;
import com.TraperRoku.shorteringAPI.dto.UrlResponseDto;
import com.TraperRoku.shorteringAPI.model.Url;
import com.TraperRoku.shorteringAPI.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
public class UrlController {
    private UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService){
        this.urlService = urlService;
    }

    @PostMapping("/generateShortUrl")
    public ResponseEntity<?> generateShortUrl(@RequestBody  UrlDto urlDto){
        Url shortUrl = urlService.getShortUrl(urlDto);

        if(shortUrl == null){
            return createUrlErrorDto("400","Failed to generate short URL",HttpStatus.BAD_GATEWAY);
        }
       return  createUrlResponseDto(shortUrl.getOriginalUrl(),shortUrl.getExpirationDate(),shortUrl.getShortUrl(),HttpStatus.OK);
    }

    @GetMapping("{shortUrl}")
    public ResponseEntity<?> getOriginalUrl(@PathVariable String shortUrl, HttpServletResponse response)throws IOException {
        if(StringUtils.isEmpty(shortUrl)){
           return createUrlErrorDto("400","Invalid URL",HttpStatus.BAD_REQUEST);
        }
        Url originalUrl = urlService.findUrlByShortVersionUrl(shortUrl);
        if(originalUrl == null){
            return createUrlErrorDto("400","There is not exist that URL",HttpStatus.BAD_REQUEST);
        }
        if(originalUrl.getExpirationDate().isBefore(LocalDateTime.now())){
           return createUrlErrorDto("400","URL expired, please do the new one",HttpStatus.BAD_REQUEST);
        }

        response.sendRedirect(originalUrl.getOriginalUrl());
        return createUrlResponseDto(originalUrl.getOriginalUrl(),originalUrl.getExpirationDate(),originalUrl.getShortUrl(),HttpStatus.OK);
    }





    private ResponseEntity<?> createUrlErrorDto(String error, String status, HttpStatus httpStatus){
        UrlErrorDto urlErrorDto = new UrlErrorDto();
        urlErrorDto.setError(error);
        urlErrorDto.setStatus(status);

        return new ResponseEntity<>(urlErrorDto,httpStatus);
    }

    private ResponseEntity<?> createUrlResponseDto(String orignalUrl, LocalDateTime expirationTime,String shortUrl,HttpStatus httpStatus){
        UrlResponseDto urlResponseDto = new UrlResponseDto();
        urlResponseDto.setOriginalUrl(orignalUrl);
        urlResponseDto.setShortUrl(shortUrl);
        urlResponseDto.setExpirationDate(expirationTime);

        return new ResponseEntity<>(urlResponseDto,httpStatus);
    }


}
