//package com.himalayas.authserver.controller.logout;
//
//import com.himalayas.securitycommons.tenant.TenantContextHolder;
//import com.himalayas.securitycommons.user.UserContextHolder;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.Objects;
//
//@Slf4j
//@Controller
//public class OidcLogoutController {
//
//  @GetMapping("/connect/logout")
//  public void logout(HttpServletRequest request,
//                     HttpServletResponse response,
//                     Authentication authentication,
//                     @RequestParam(value = "post_logout_redirect_uri", required = false) String postLogoutRedirectUri,
//                     @RequestParam(value = "id_token_hint", required = false) String idTokenHint) throws Exception {
//    if (idTokenHint != null) {
//      log.info("Received id_token_hint: {}", idTokenHint);
//      // TODO: validate the id_token_hint if necessary
//    }
//    UserContextHolder.clear();
//    TenantContextHolder.clear();
//    // Perform Spring Security logout
//    new SecurityContextLogoutHandler().logout(request, response, authentication);
//    // Invalidate session
//    request.getSession(false).invalidate();
//    response.sendRedirect(Objects.requireNonNullElse(postLogoutRedirectUri, "/login?logout"));
//  }
//}
