package com.projects.arch_ref.interfaces.http.inbound.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RequestLogger {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void logRequest(HttpServletRequest httpServletRequest) throws IOException {
        try {
            Map<String, String> headers = buildHeaders(httpServletRequest);
            Map<String, String> parameters = buildParameterMap(httpServletRequest);

            String logMessage = "Request method=[" + httpServletRequest.getMethod() + "] path=[" + httpServletRequest.getRequestURI() + "] headers = [ " + objectMapper.writeValueAsString(headers) + "]";

            if (!(parameters.isEmpty())) {
                logMessage += " parameters=[ " + objectMapper.writeValueAsString(parameters) + "]";
            }

            log.info(logMessage);
        } catch (Exception e) {
            throw e;
        }
    }


    private static Map<String, String> buildParameterMap(HttpServletRequest request) {
        Map<String, String> parameter = new HashMap<>();

        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String k = parameterNames.nextElement();
            String v = request.getParameter(k);
            parameter.put(k, v);
        }

        return parameter;
    }

    private static Map<String, String> buildHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String k = headerNames.nextElement();
            String v = request.getHeader(k);
            headers.put(k, v);
        }

        return headers;
    }

}
