package com.lethien.elearning.filter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class JwtAuthenticateFilter extends BasicAuthenticationFilter{
	private AuthenticationManager authenticationManager;
	private UserDetailsService userDetailsService;

	public SecretKey convertStringToSecretKeyto(String encodedKey) {
		byte[] keyBytes = Decoders.BASE64.decode(encodedKey);
		SecretKey originalKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");
		return originalKey;
	}
	public JwtAuthenticateFilter( AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.authenticationManager=authenticationManager;
		this.userDetailsService = userDetailsService;
		// TODO Auto-generated constructor stub
	}
	private Claims getAllClaimsFromToken(String token) {
		SecretKey key = convertStringToSecretKeyto("Test123456789ioqedfhoeifhouwhfowfhowuvboiwbuvgoouhoguhf");
		return Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// B1: Lấy token từ request
		try {
		String authorization = request.getHeader("Authorization");
//		System.out.println(authorization);
//		System.out.println(authorization.startsWith("Bearer"));
//		 && authorization.startsWith("Bearer")
		if(authorization!=null && authorization.startsWith("Bearer ")) {
			// B2: Giải ngược Token => Lấy email đã lưu vào token ở bước đăng nhập
			
			String token = authorization.replace("Bearer ","");
//			String token = authorization;
//			System.out.println(token);
			/*String email = Jwts.parser()
			.setSigningKey("ChuoiBiMat")
			.parseClaimsJws(token)
			.getBody()
			.getSubject();*/
			String email = getAllClaimsFromToken(token).getSubject();
			// B3: Truy vấn DB lấy thông tin user (sử dụng email vừa lấy từ token)
			UserDetails userDetails = userDetailsService.loadUserByUsername(email);
			
			// B4: Lưu thông tin user vào SecurityContext (Để phân quyền)
			SecurityContextHolder
			.getContext()
			.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
		}
		
//		response.setStatus(401);
		chain.doFilter(request, response);
	} catch (Exception e) {
		response.setStatus(401);
	}
}
}