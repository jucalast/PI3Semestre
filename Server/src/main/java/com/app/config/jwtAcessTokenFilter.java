//package com.app.config;
//
//import com.app.DTO.TokenType;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.net.http.HttpHeaders;
//
//@RequiredArgsConstructor
//@Slf4j
//public class JwtAccessTokenFilter extends OncePerRequestFilter {
//
//    private final RSAKeyRecord rsaKeyRecord;
//    private final JwtTokenUtils jwtTokenUtils;
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain
//    ) throws ServletException, IOException {
//
//        try {
//            log.info("[JwtAccessTokenFilter:doFilterInternal] :: Started");
//            log.info("[JwtAccessTokenFilter:doFilterInternal]Filtering the Http Request:{}", request.getRequestURI());
//
//            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//            JwtDecoder jwtDecoder = NimbusJwtDecoder.withPublicKey(rsaKeyRecord.rsaPublicKey()).build();
//
//            if(!authHeader.startsWith(TokenType.Bearer.name())) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            final String token = authHeader.substring(7);
//            final Jwt jwtToken = jwtDecoder.decode(token);
//            final String username = JwtTokenUtils.getUsername(jwtToken);
//        }
//    }
//}
