package it.planner.travel.service.restservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.planner.travel.domain.dto.UserResponseDto;


@Service
public class UserService {

    @Value("${external.api.user.url}")
    private String userInfoUrl;

    @Value("${external.api.user.timeout:5000}")
    private int timeout;

    public UserResponseDto getUserProfile(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); // oppure headers.set("Authorization", "Bearer " + token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(
                userInfoUrl,
                HttpMethod.GET,
                entity,
                UserResponseDto.class)
                .getBody();
    }

}
