package ua.reed.urlshortener.controller.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ua.reed.urlshortener.controller.ShortUrlController;
import ua.reed.urlshortener.service.ClickStatsService;

@Component
public class RequestCountInterceptor implements HandlerInterceptor {

    private static final String SHORT_URL_PARAMETER = "shortUrl";

    private final ClickStatsService clickStatsService;

    public RequestCountInterceptor(final ClickStatsService clickStatsService) {
        this.clickStatsService = clickStatsService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (handler instanceof HandlerMethod hm
                && hm.getBeanType().isAssignableFrom(ShortUrlController.class)
                && request.getMethod().equals(HttpMethod.GET.name())
                && requestURI.endsWith("/short-url")) {
            String shortUrl = request.getParameter(SHORT_URL_PARAMETER);
            if (StringUtils.isNotEmpty(shortUrl)) {
                clickStatsService.registerClick(shortUrl);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
