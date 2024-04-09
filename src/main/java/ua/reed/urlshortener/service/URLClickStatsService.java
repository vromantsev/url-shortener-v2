package ua.reed.urlshortener.service;

import org.apache.commons.lang3.StringUtils;
import ua.reed.urlshortener.dto.ClickStatsDto;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class URLClickStatsService implements ClickStatsService {

    private final ConcurrentMap<String, Long> shortUrlToClicksMap = new ConcurrentHashMap<>();

    @Override
    public void registerClick(final String shortUrl) {
        if (StringUtils.isEmpty(shortUrl)) {
            throw new IllegalArgumentException("Short url must not be null or empty!");
        }
        Long clicks = shortUrlToClicksMap.get(shortUrl);
        if (clicks == null) {
            clicks = 1L;
        } else {
            clicks++;
        }
        shortUrlToClicksMap.put(shortUrl, clicks);
    }

    @Override
    public ClickStatsDto getStatsForUrl(final String shortUrl) {
        Long clicks = shortUrlToClicksMap.get(shortUrl);
        return new ClickStatsDto(shortUrl, clicks == null ? 0 : clicks);
    }
}
