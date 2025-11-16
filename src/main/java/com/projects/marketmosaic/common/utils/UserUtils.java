package com.projects.marketmosaic.common.utils;

import com.projects.marketmosaic.common.exception.MarketMosaicCommonException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
		request.setAttribute("userId", userId);
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

}
