package com.babyblackdog.ddogdog.common.auth;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_ROLE;

import com.babyblackdog.ddogdog.global.exception.UserException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtSimpleAuthentication {

    private final HumanName username;
    private final Email email;
    private final Role role;

    private static class SingletonHolder {

        private static final JwtSimpleAuthentication INSTANCE = new JwtSimpleAuthentication();
    }

    private JwtSimpleAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, String> principal = getPrincipal(authentication);
        this.username = new HumanName(principal.get("username"));
        this.email = new Email(principal.get("email"));
        this.role = getAuthority(authentication);
    }

    private Map<String, String> getPrincipal(Authentication authentication) {
        Map<String, String> map = new HashMap<>();
        String[] principalData = parsePrincipal(authentication.getPrincipal());
        map.put("username", principalData[1].split("=")[1].replace("'", ""));
        map.put("email", principalData[2].split("=")[1]
                .replace("'", "")
                .replace("]", ""));
        return map;
    }

    private String[] parsePrincipal(Object principal) {
        return principal.toString().split(", ");
    }

    private Role getAuthority(Authentication authentication) {
        String authority = authentication.getAuthorities().stream()
                .findAny()
                .orElseThrow(() -> new UserException(INVALID_ROLE))
                .getAuthority();
        return Role.valueOf(authority);
    }

    public static JwtSimpleAuthentication getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public HumanName getUsername() {
        return username;
    }

    public String getUsernameValue() {
        return username.getValue();
    }

    public Email getEmail() {
        return email;
    }

    public String getEmailAddress() {
        return email.getValue();
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", JwtSimpleAuthentication.class.getSimpleName() + "[", "]")
                .add("username='" + username + "'")
                .add("email='" + email + "'")
                .add("role=" + role)
                .toString();
    }
}