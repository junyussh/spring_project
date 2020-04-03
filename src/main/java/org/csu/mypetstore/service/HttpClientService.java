package org.csu.mypetstore.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class HttpClientService {
    private static final int TIMEOUT = 60000;
    private final RestTemplate restTemplate;

    public HttpClientService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String get(final String url) {
        String rs = this.restTemplate.getForObject(url, String.class);
        return rs;
    }

    public String post(final String url, final HttpEntity data) {
        try {
            String rs = this.restTemplate.postForObject(new URI(url), data, String.class);
            return rs; // Customization your response format
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String put(final String url, final HttpEntity data) {
        try {
            this.restTemplate.put(new URI(url), data);
            return "ok"; // Customization your response format
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String patch(final String url, final HttpEntity data) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(TIMEOUT);
        requestFactory.setReadTimeout(TIMEOUT);
        restTemplate.setRequestFactory(requestFactory);
        try {
            final String rs = this.restTemplate.patchForObject(new URI(url), data, String.class);
            return rs; // Customization your response format
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String delete(final String url) {
        try {
            this.restTemplate.delete(new URI(url));
            return "ok"; // Customization your response format
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
