package ua.reed.urlshortener.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class SimpleUrlShortenerService implements UrlShortenerService {

    private final ConcurrentMap<String, String> shortToInitialUrlMap = new ConcurrentHashMap<>();

    @Override
    public String shortenUrl(final String initialUrl) {
        if (StringUtils.isEmpty(initialUrl)) {
            throw new IllegalArgumentException("Parameter [initialUrl] must not be null or empty!");
        }
        String shortUrl = RandomStringUtils.randomAlphanumeric(10);
        shortToInitialUrlMap.put(shortUrl, initialUrl);
        return shortUrl;
    }

    @Override
    public String getInitialUrl(final String shortUrl) {
        if (StringUtils.isEmpty(shortUrl)) {
            throw new IllegalArgumentException("Parameter [shortUrl] must not be null or empty!");
        }
        return shortToInitialUrlMap.get(shortUrl);
    }
}
