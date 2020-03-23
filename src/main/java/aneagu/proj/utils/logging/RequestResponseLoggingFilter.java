package aneagu.proj.utils.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class RequestResponseLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        log.info(
                "Logging request: {}, URL: {}", req.getMethod(),
                req.getRequestURI());
        chain.doFilter(request, response);
        long duration = System.currentTimeMillis() - start;
        log.info(
                "Logging response: {}, STATUS: {}, duration: {} ms \n",
                res.getContentType(), res.getStatus(), duration);
    }
}
