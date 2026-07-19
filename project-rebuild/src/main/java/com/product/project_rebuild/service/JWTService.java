package com.product.project_rebuild.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	
	private String strKey = "";
	
	public JWTService() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
			strKey = Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public String generateToken(String username) {
		
		Map<String, Object> claims = new HashMap<>();
		Date now = new Date(System.currentTimeMillis());
		Date exp = new Date(System.currentTimeMillis() + 60 * 60 * 30);
		return Jwts.builder()
				.claims()
				.add(claims)
				.subject(username)
				.issuedAt(now)
				.expiration(exp)
				.and()
				.signWith(getKey())
				.compact();
				
	}

	private SecretKey getKey() {
		byte[] bytesKey = Decoders.BASE64.decode(strKey);
		return Keys.hmacShaKeyFor(bytesKey);
	}

	public String extractUsername(String token) {
		// extract the username from the jwt token
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolve) {
		final Claims claims = extractAllClaims(token);
		return claimsResolve.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		Date expDate = extractClaim(token, Claims::getExpiration);
		return expDate.before(new Date());
	}

}
