package com.lethien.elearning.api;

import java.util.Base64;
import java.util.Date;

import com.lethien.elearning.common.Common;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private AuthenticationManager authenticationManager;
    private UserService userService;

    /**
     *
     * @param authenticationManager
     * @param userService
     */
    public ApiAuthController(
            AuthenticationManager authenticationManager,
            UserService userService
    ) {
        super();
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    /**
     *
     * @param encodedKey
     * @return
     */
    public SecretKey convertStringToSecretKeyto(String encodedKey) {
        byte[] keyBytes = Decoders.BASE64.decode(encodedKey);
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, Common.ALGORITHM);
    }

    /**
     *
     * @param dto
     * @return
     */
    @PostMapping("register")
    public ResponseEntity<Object> add(@RequestBody UserDto dto) {
        try {
            if (userService.getUserDtoByEmail(dto.getEmail()) == null) {
                userService.save(dto);
                return new ResponseEntity<Object>("Thêm thành công!", HttpStatus.CREATED);
            }
            return new ResponseEntity<Object>("Email đã tồn tại!", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * @param loginDto
     * @return
     */
    @PostMapping("")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
        SecretKey key = convertStringToSecretKeyto(Common.SECRECT_KEY);
        Authentication authentication = null;
        String email = loginDto.getUsername();
        String password = loginDto.getPassword();
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + Common.JWT_EXPIRATION);
            // Nếu đăng nhập thành công thì trả về một token
            String token = Jwts.builder().subject(email).issuedAt(now).expiration(expiryDate)
                    .signWith(key).compact();
            return new ResponseEntity<Object>(token, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }
}
