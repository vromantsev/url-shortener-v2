package ua.reed.urlshortener.service;

public interface UrlShortenerService {

    String shortenUrl(String initialUrl);

    String getInitialUrl(String shortUrl);

}
