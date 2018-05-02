package com.github.mkopylec.oauth2.logger.interceptor;

import org.slf4j.Logger;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.stream.Collectors.joining;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpRequest;

public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger log = getLogger(LoggingFilter.class);

    private LoggerInterceptorProperties properties;
    private RestTemplate loggerWebClient = new RestTemplate();
    private AntPathMatcher matcher = new AntPathMatcher();

    public LoggingFilter(LoggerInterceptorProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RequestData requestData = new RequestData()
                .setTime(now().format(ofPattern("HH:mm:ss.SSS")))
                .setApplication(properties.getApplicationName())
                .setMethod(getMethod(request))
                .setUrl(getUrl(request))
                .setAuthorizationHeader(getAuthorizationHeader(request))
                .setBody(getBody(request));
        filterChain.doFilter(request, response);
        requestData.setResponseStatus(response.getStatus());
        try {
            if (hasToLogRequest(request)) {
                sendRequestLog(requestData);
            }
        } catch (Exception ex) {
            log.warn("Sending request: {} log to logger-web failed", requestData, ex);
        }
    }

    private boolean hasToLogRequest(HttpServletRequest request) {
        return properties.getExcludedUriPatterns().stream()
                .noneMatch(excludedUriPattern -> matcher.match(excludedUriPattern, request.getRequestURI()));
    }

    private void sendRequestLog(RequestData requestData) {
        loggerWebClient.put("http://localhost:8082/logger/request-log", requestData);
    }

    private String getMethod(HttpServletRequest request) {
        return request.getMethod();
    }

    private String getUrl(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        if (request.getQueryString() != null) {
            url.append('?').append(request.getQueryString());
        }
        return url.toString();
    }

    private String getAuthorizationHeader(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION);
    }

    private String getBody(HttpServletRequest request) {
        MultiValueMap<String, String> queryParameters = fromHttpRequest(new ServletServerHttpRequest(request))
                .build()
                .getQueryParams();
        return request.getParameterMap().entrySet().stream()
                .filter(parameter -> !queryParameters.containsKey(parameter.getKey()))
                .map(parameter -> parameter.getKey() + "=" + parameter.getValue()[0])
                .collect(joining(" & "));
    }
}
