package com.example.ClonePayloads.config;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.*;
@Component
public class RequestLimitsFilter extends OncePerRequestFilter {
    private static final int MAX_BYTES=65536;
    @Override protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain chain) throws ServletException,IOException {
        response.setHeader("X-Content-Type-Options","nosniff");
        response.setHeader("X-Frame-Options","DENY");
        response.setHeader("Referrer-Policy","no-referrer");
        if (!"POST".equals(request.getMethod()) || !"/api/generate".equals(request.getServletPath())) {chain.doFilter(request,response);return;}
        if (request.getContentLengthLong()>MAX_BYTES) {reject(response);return;}
        byte[] body=request.getInputStream().readNBytes(MAX_BYTES+1);
        if (body.length>MAX_BYTES) {reject(response);return;}
        chain.doFilter(new HttpServletRequestWrapper(request) {
            @Override public ServletInputStream getInputStream() {
                ByteArrayInputStream input=new ByteArrayInputStream(body);
                return new ServletInputStream() {
                    public int read(){return input.read();}
                    public boolean isFinished(){return input.available()==0;}
                    public boolean isReady(){return true;}
                    public void setReadListener(ReadListener listener){throw new UnsupportedOperationException();}
                };
            }
            @Override public BufferedReader getReader(){return new BufferedReader(new InputStreamReader(getInputStream(),java.nio.charset.StandardCharsets.UTF_8));}
        },response);
    }
    private void reject(HttpServletResponse response) throws IOException {
        response.setStatus(413);response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"error\":\"A requisição excede o limite de 64 KiB.\"}");
    }
}
