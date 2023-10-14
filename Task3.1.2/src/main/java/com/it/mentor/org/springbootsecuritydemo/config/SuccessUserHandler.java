package com.it.mentor.org.springbootsecuritydemo.config;

import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.web.authentication.*;
import org.springframework.stereotype.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/admin/people");
        } else if (roles.contains("ROLE_USER")) {
            httpServletResponse.sendRedirect("/person/info");
        } else {
            httpServletResponse.sendRedirect("/auth/registration");
        }
    }
}