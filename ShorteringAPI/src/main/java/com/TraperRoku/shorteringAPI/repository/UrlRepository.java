package com.TraperRoku.shorteringAPI.repository;

import com.TraperRoku.shorteringAPI.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url,Long> {
    Url findByShortUrl(String shortUrl);
}
