package com.projects.marketmosaic.common.utils;

import com.projects.marketmosaic.common.exception.MarketMosaicCommonException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserUtils {

	@Value("${jwt.cookie.name:JWT_SESSION}")
	private String jwtCookieName;

	private final JWTUtils jwtUtils;

	public Long getUserId(HttpServletRequest request) {
		return (Long) request.getAttribute("userId");
	}

	public String getRole(HttpServletRequest request) {
		return (String) request.getAttribute("role");
	}

	public void validateUser(HttpServletRequest request) {
		String token = extractJwtFromCookies(request);

		if(token == null) {
			throw new MarketMosaicCommonException("Session not found", 40000);
		}

		if(jwtUtils.isTokenExpired(token)) {
			throw new MarketMosaicCommonException("Session Expired", 401);
		}


		Long userId = jwtUtils.extractUserId(token);
		String role = jwtUtils.extractRole(token);
		request.setAttribute("userId", userId);
		request.setAttribute("role", role);
	}

	private String extractJwtFromCookies(HttpServletRequest request) {
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (jwtCookieName.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public String getCookie(HttpServletRequest request) {
		String jwtToken = extractJwtFromCookies(request);

		if(StringUtils.isNotBlank(jwtToken)) {
			return "JWT_SESSION=" + jwtToken;
		}

		return null;
	}

}
