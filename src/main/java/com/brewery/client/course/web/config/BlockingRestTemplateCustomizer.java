package com.brewery.client.course.web.config;


import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.DefaultConnectionKeepAliveStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {
    private final Integer maxtotalconnections;
    private final Integer maxdefaultmaxperroute;
    private final Integer responsetimeout;
    private final Integer connectionrequesttimeout;

    public BlockingRestTemplateCustomizer(@Value("${rest.maxtotalconnections}") Integer maxtotalconnections,
                                          @Value("${rest.maxdefaultmaxperroute}") Integer maxdefaultmaxperroute,
                                          @Value("${rest.responsetimeout}") Integer responsetimeout,
                                          @Value("${rest.connectionrequesttimeout}") Integer connectionrequesttimeout) {
        this.maxtotalconnections = maxtotalconnections;
        this.maxdefaultmaxperroute = maxdefaultmaxperroute;
        this.responsetimeout = responsetimeout;
        this.connectionrequesttimeout = connectionrequesttimeout;
    }


    public ClientHttpRequestFactory clientHttpRequestFactory() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxtotalconnections);
        connectionManager.setDefaultMaxPerRoute(maxdefaultmaxperroute);

        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(Timeout.of(connectionrequesttimeout, TimeUnit.MILLISECONDS))
                .setResponseTimeout(responsetimeout, TimeUnit.MILLISECONDS)
                .build();

        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig)
                .build();


        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(this.clientHttpRequestFactory());
    }
}
