package com.jeontongju.search.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AwsCredentialsConfig {

  @Value("${cloud.aws.credentials.access-key}")
  private String accessKey;

  @Value("${cloud.aws.credentials.secret-key}")
  private String secretKey;

  @Bean
  public AWSCredentials amazonAWSCredentials() {
    return new BasicAWSCredentials(accessKey, secretKey);
  }

  @Bean
  public AWSCredentialsProvider amazonAWSCredentialsProvider() {
    return new AWSStaticCredentialsProvider(amazonAWSCredentials());
  }

  @Bean(name = "customCredentialsProvider")
  public CredentialsProvider customCredentialsProvider() {
    final BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
    basicCredentialsProvider.setCredentials(AuthScope.ANY,
            new UsernamePasswordCredentials(accessKey, secretKey));
    return basicCredentialsProvider;
  }

}
