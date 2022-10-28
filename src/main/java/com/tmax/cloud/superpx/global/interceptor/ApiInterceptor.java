package com.tmax.cloud.superpx.global.interceptor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmax.cloud.superpx.global.error.exception.BusinessException;
import com.tmax.cloud.superpx.global.error.exception.ErrorCode;
import com.tmax.cloud.superpx.global.utils.JwtTokenProvider;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.*;

@Slf4j
public class ApiInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtTokenProvider jwtService;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        if (activeProfile.equals("dev") || activeProfile.equals("local")) {
            return true;
        }

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = request.getHeader("JWT-TOKEN");

        if (token != null && token.length() > 0) {
            if (!jwtService.validateToken(token)) {
                throw new BusinessException(ErrorCode.INVALID_TOKEN);
            }

            // check authorization sample
            if (!jwtService.getClaimFromJwtToken(token).getId().equals("admin id")) {
                throw new BusinessException(ErrorCode.HANDLE_ACCESS_DENIED);
            }
        }

        return true;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler, Exception ex)
            throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;

        Map<String, String> queryParamMap = new HashMap<>();
        String queryString = "";

        if (request.getQueryString() != null) {
            queryString += URLDecoder.decode(request.getQueryString(), "UTF-8");
            String[] queryParams = queryString.split("&");
            for (String param : queryParams) {
                String name = param.split("=")[0];
                String value = "";
                if (param.split("=").length > 1) {
                    value = param.split("=")[1];
                }
                queryParamMap.put(name, value);
            }
        }

        JsonNode requestBody = null;
        JsonNode responseBody = null;
        String logString = String.format("\nFROM:%s, [%s] %s%s",
                request.getRemoteAddr(), request.getMethod(), request.getRequestURL(),
                queryString.equals("") ? "" : "?" + queryString);


        if (cachingRequest.getContentType() != null && cachingRequest.getContentType().contains("application/json")) {
            cachingRequest.getContentAsByteArray();
            if (cachingRequest.getContentAsByteArray().length != 0) {
                requestBody = objectMapper.readTree(cachingRequest.getContentAsByteArray());
                logString += String.format("\nREQUEST: %s", requestBody);
            }
        }

        if (cachingResponse.getContentType() != null && cachingResponse.getContentType().contains("application/json")) {
            cachingResponse.getContentAsByteArray();
            if (cachingResponse.getContentAsByteArray().length != 0) {
                responseBody = objectMapper.readTree(cachingResponse.getContentAsByteArray());
                if (response.getStatus() == 200) {
                    logString += "\nOK";
                    logString += String.format("\nRESPONSE: %s", responseBody);
                } else {
                    logString += String.format("\nERROR RESPONSE: %s", responseBody);
                }
            }
        }

        logString += "\n";

        log.info(logString);
    }
}