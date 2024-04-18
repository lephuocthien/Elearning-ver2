package com.lethien.elearning.controller;

import com.lethien.elearning.dto.LoginDto;
import com.lethien.elearning.dto.UserDto;
import com.lethien.elearning.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("admin")
public class LoginController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService  userService;

    public SecretKey convertStringToSecretKeyto(String encodedKey) {
        byte[] keyBytes = Decoders.BASE64.decode(encodedKey);
        SecretKey originalKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");
        return originalKey;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String index( ModelMap modelMap,
                         HttpSession session) {
        LoginDto loginDto = new LoginDto();
        modelMap.addAttribute("loginDto", loginDto);
        return "admin/login/index";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public String login(
            @ModelAttribute("loginDto") LoginDto loginDto,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            ModelMap modelMap) throws IOException {
        SecretKey key = convertStringToSecretKeyto("Test123456789lephuocthien31101999ABCDXYZTest123456789lephuocthien31101999ABCDXYZ");
        final long JWT_EXPIRATION = 864000000L;
        Authentication authentication = null;
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
            // Nếu đăng nhập thành công thì trả về một token
            String token = Jwts.builder().subject(email).issuedAt(now).expiration(expiryDate)
                    .signWith(key).compact();
            UserDto dto = userService.getUserDtoByEmail(email);
            session.setAttribute("AUTH_NAME", dto.getFullname());
            session.setAttribute("AUTH_ID", dto.getId());
            session.setAttribute("TOKEN", token);
            return "redirect:/admin/home";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Failed");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/admin/login";
        }
    }
    @RequestMapping(value = "/logout")
    public String logout( HttpSession session) {
        session.removeAttribute("AUTH_NAME");
        session.removeAttribute("AUTH_ID");
        session.removeAttribute("TOKEN");
        return "redirect:/admin/login";
    }
}
