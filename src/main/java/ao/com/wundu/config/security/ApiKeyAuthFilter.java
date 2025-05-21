package ao.com.wundu.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(ApiKeyAuthFilter.class);
    private static final String HEADER_NAME = "X-API-KEY";

    @Value("${api.key}")
    private String apiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestKey = request.getHeader(HEADER_NAME);
        logger.debug("X-API-KEY recebida: {}", requestKey);

        if (Objects.isNull(requestKey) || !requestKey.trim().equals(apiKey.trim())) {
            logger.warn("Tentativa de acesso com API Key inválida ou ausente para o endpoint: {}", request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"API Key inválida ou ausente\"}");
            response.setContentType("application/json");
            return;
        }

        // Definir contexto de autenticação
        PreAuthenticatedAuthenticationToken authToken = new PreAuthenticatedAuthenticationToken(
                "api-key-user", null, null);
        authToken.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authToken);

        logger.debug("Autenticação via API Key bem-sucedida para o endpoint: {}", request.getRequestURI());
        filterChain.doFilter(request, response);

    }

}
