package ua.reed.urlshortener.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlComposer {

    public String buildUrl(final String hostUrl, final String shortUrl) {
        return hostUrl + "/" + shortUrl;
    }
}
