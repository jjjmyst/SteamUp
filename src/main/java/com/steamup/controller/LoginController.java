package com.steamup.controller;

import com.steamup.entities.UserInfo;
import com.steamup.models.AuthRequest;
import com.steamup.models.BasicUserInfo;
import com.steamup.service.JwtService;
import com.steamup.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/newUser")
    public String showNewUserForm(Model model) {
        model.addAttribute("basicUserInfo", new BasicUserInfo());
        return "register"; // Returns the login form view
    }

    @PostMapping(value = "/newUser", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addNewUser(BasicUserInfo userInfo, Model model) {
        try {
            service.addUser(new UserInfo(userInfo));
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register"; // Return to the registration form with an error message
        }
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(required = false) String error, @RequestParam(required = false) String locked, Model model) {
        model.addAttribute("authRequest", new AuthRequest());
        if (null != error) {
            model.addAttribute("error", "Invalid username or password");
        }
        if (null != locked) {
            model.addAttribute("error", "Account is locked");
        }
        return "login"; // Returns the login form view
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String authenticateAndGetToken(AuthRequest authRequest, Model model) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            jwtService.generateToken(authRequest.getUsername());
            return "redirect:/auth/user/userProfile";
        }

        return "login";
    }
}

