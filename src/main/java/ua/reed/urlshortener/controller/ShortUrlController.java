package ua.reed.urlshortener.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.reed.urlshortener.dto.ClickStatsDto;
import ua.reed.urlshortener.dto.CreateShortUrlRequest;
import ua.reed.urlshortener.service.ClickStatsService;
import ua.reed.urlshortener.service.UrlShortenerService;
import ua.reed.urlshortener.utils.UrlComposer;

import java.net.URI;

@Controller
@RequiredArgsConstructor
public class ShortUrlController {

    private final UrlShortenerService urlShortenerService;
    private final ClickStatsService clickStatsService;

    @GetMapping
    public String index(final Model model) {
        model.addAttribute("createShortUrlRequest", new CreateShortUrlRequest(""));
        return "index";
    }

    @PostMapping("/short-url")
    public String generateShortUrl(@ModelAttribute("createShortUrlRequest") final CreateShortUrlRequest createShortUrlRequest,
                                   final HttpServletRequest request,
                                   final Model model) {
        var shortUrl = this.urlShortenerService.shortenUrl(createShortUrlRequest.initialUrl());
        model.addAttribute("shortUrl", UrlComposer.buildUrl(request.getRequestURL().toString(), shortUrl));
        return "index";
    }

    @GetMapping("/short-url")
    public ResponseEntity<?> redirectByShortUrl(@RequestParam("shortUrl") final String shortUrl) {
        String initialUrl = this.urlShortenerService.getInitialUrl(shortUrl);
        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(initialUrl))
                .build();
    }

    @GetMapping("/short-url/{shortUrl}")
    public ResponseEntity<ClickStatsDto> getStatsForUrl(@PathVariable("shortUrl") final String shortUrl) {
        ClickStatsDto stats = clickStatsService.getStatsForUrl(shortUrl);
        return ResponseEntity.ok()
                .body(stats);
    }
}
