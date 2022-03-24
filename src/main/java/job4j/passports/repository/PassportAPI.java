package job4j.passports.repository;

import job4j.passports.domain.Passport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Repository
public class PassportAPI {
    @Value("${api-url}")
    private String url;

    private final RestTemplate client;

    public PassportAPI(RestTemplate client) {
        this.client = client;
    }

    public Passport save(Passport passport) {
        return client.postForEntity(
                String.format("%s/save", url),
                passport,
                Passport.class
        ).getBody();
    }

    public boolean update(String id, Passport passport) {
        return client.exchange(
                String.format("%s/update?id=%s", url, id),
                HttpMethod.PUT,
                new HttpEntity<>(passport),
                Void.class
        ).getStatusCode() != HttpStatus.NOT_FOUND;
    }

    public boolean delete(String id) {
        return client.exchange(
                String.format("%s/delete?id=%s", url, id),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class
        ).getStatusCode() != HttpStatus.NOT_FOUND;
    }

    public List<Passport> findAll() {
        List<Passport> body = client.exchange(
                String.format("%s/find", url),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Passport>>() {
                }
        ).getBody();
        return body == null ? Collections.emptyList() : body;
    }

    public List<Passport> findBySerial(String serial) {
        return getList(String.format("%s/find?serial=%s", url, serial));
    }

    public List<Passport> findUnavailable() {
        return getList(String.format("%s/unavailable", url));
    }

    public List<Passport> findReplaceable() {
        return getList(String.format("%s/find-replaceable", url));
    }

    private List<Passport> getList(String composedUrl) {
        List<Passport> body = client.exchange(
                composedUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Passport>>() {
                }
        ).getBody();
        return body == null ? Collections.emptyList() : body;
    }
}