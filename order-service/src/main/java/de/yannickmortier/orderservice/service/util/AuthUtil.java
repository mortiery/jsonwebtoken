package de.yannickmortier.orderservice.service.util;

import de.yannickmortier.orderservice.domain.User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class AuthUtil {
    public static MultiValueMap<String, String> createHeadersForUser(User user) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + user.getCurrentToken());
        headers.add("Content-Type", "application/json");
        return headers;
    }
}
