package com.TraperRoku.shorteringAPI.service;

import com.TraperRoku.shorteringAPI.dto.UrlDto;
import com.TraperRoku.shorteringAPI.model.Url;
import com.TraperRoku.shorteringAPI.repository.UrlRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class UrlService {
    private final UrlRepository urlRepository;
    @Autowired
    public UrlService(UrlRepository urlRepository){
        this.urlRepository = urlRepository;
    }
    private String encodeUrl(String url){
        return Hashing.murmur3_32()
                .hashString(url.concat(LocalDateTime.now().toString()), StandardCharsets.UTF_8)
                .toString();
    }

    public Url getShortUrl(UrlDto urlDto){

    if(!StringUtils.isEmpty(urlDto.getUrl())) {
        Url urlToPersist = new Url();
        String urlShort = encodeUrl(urlDto.getUrl());

        urlToPersist.setOriginalUrl(urlDto.getUrl());
        urlToPersist.setShortUrl(urlShort);
        urlToPersist.setCreationDate(LocalDateTime.now());
        urlToPersist.setExpirationDate(setTime(urlDto.getExpirationDate(), urlToPersist.getCreationDate()));
        Url savedUrl = saveUrl(urlToPersist);

        if (savedUrl == null) {
            return null;
        }
        return savedUrl;
    }
    return null;
    }

    private LocalDateTime setTime (String expirationTime , LocalDateTime urlToPersistGetCreationDate){
        if(StringUtils.isEmpty(expirationTime)){
        return urlToPersistGetCreationDate.plusSeconds(60);
        }
        return LocalDateTime.parse(expirationTime);
    }

    public Url saveUrl(Url url){
        return urlRepository.save(url);
    }

    public Url findUrlByShortVersionUrl(String shortUrl){
        return urlRepository.findByShortUrl(shortUrl);
    }

    public void deleteUrl(Url url){
        urlRepository.delete(url);
    }
}
