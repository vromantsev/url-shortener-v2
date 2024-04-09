package ua.reed.urlshortener.service;

import ua.reed.urlshortener.dto.ClickStatsDto;

public interface ClickStatsService {

    void registerClick(String shortUrl);

    ClickStatsDto getStatsForUrl(String shortUrl);

}
