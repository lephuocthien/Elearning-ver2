package com.lethien.elearning.api;

import java.util.Base64;
import java.util.Date;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.lethien.elearning.dto.LoginDto;
import com.lethien.elearning.dto.UserDto;
import com.lethien.elearning.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@RestController
@RequestMapping("api/auth")
public class ApiAuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	private UserService userService;

	public SecretKey convertStringToSecretKeyto(String encodedKey) {
		byte[] keyBytes = Decoders.BASE64.decode(encodedKey);
		SecretKey originalKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");
		return originalKey;
	}

	/**
	 * @param_userService
	 */
	public ApiAuthController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping("register")
	public ResponseEntity<Object> add(@RequestBody UserDto dto) {
		try {
			if (userService.getUserDtoByEmail(dto.getEmail()) == null) {
				userService.save(dto);
				return new ResponseEntity<Object>("Thêm thành công!", HttpStatus.CREATED);
			}
			return new ResponseEntity<Object>("Email đã tồn tại!", HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("")
	public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
		/*final String JWT_SECRET = "ChuoiBiMat";
		SecretKey decodeKey = convertStringToSecretKeyto(JWT_SECRET);*/
		SecretKey key = convertStringToSecretKeyto("Test123456789ioqedfhoeifhouwhfowfhowuvboiwbuvgoouhoguhf");
		final long JWT_EXPIRATION = 864000000L;
		//Authentication authentication = null;
		String email = loginDto.getEmail();
		String password = loginDto.getPassword();
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String hashedPassword = passwordEncoder.encode(password);
//        System.out.println(hashedPassword);
		try {
			System.out.println(email + " " + password);
			/*authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(email, password));*/
//			SecurityContextHolder.getContext().setAuthentication(authentication);
			Date now = new Date();
			Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
			//SecretKey key = Jwts.SIG.HS256.key().build();
			// Nếu đăng nhập thành công thì trả về một token
			String token = Jwts.builder().subject(email).issuedAt(now).expiration(expiryDate)
					.signWith(key).compact();
			/*SecretKey decodeKey = convertStringToSecretKeyto(email);
			String token = Jwts.builder().signWith(decodeKey).compact()*/;
			System.out.println(token);
			return new ResponseEntity<Object>(token, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

}