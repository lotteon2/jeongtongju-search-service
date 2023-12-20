package com.jeontongju.search.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.RestHighLevelClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class OpenSearchConfig implements DisposableBean {

  @Value("${opensearch.hostname}")
  private String hostname;

  @Autowired
  @Qualifier("customCredentialsProvider")
  private CredentialsProvider credentialsProvider;

  @Bean
  public RestHighLevelClient client() {

    RestClientBuilder restClientBuilder =
        RestClient.builder(new HttpHost(hostname, 443, "https"))
            .setHttpClientConfigCallback(
                new RestClientBuilder.HttpClientConfigCallback() {
                  @Override
                  public HttpAsyncClientBuilder customizeHttpClient(
                      HttpAsyncClientBuilder httpClientBuilder) {
                    return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                  }
                });

    return new RestHighLevelClient(restClientBuilder);
  }

  @Override
  public void destroy() throws Exception {
    client().close();
  }
}
