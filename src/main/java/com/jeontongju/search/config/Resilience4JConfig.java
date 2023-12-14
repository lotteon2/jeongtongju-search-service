package com.jeontongju.search.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import java.time.Duration;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class Resilience4JConfig {

  @Bean
  public CircuitBreakerConfig circuitBreakerConfig() {
    return CircuitBreakerConfig.custom()
            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
            .slowCallDurationThreshold(Duration.ofSeconds(2))
            .slowCallRateThreshold(50)
            .failureRateThreshold(50)
            .minimumNumberOfCalls(5)
            .slidingWindowSize(10)
            .maxWaitDurationInHalfOpenState(Duration.ofMillis(1000))
            .waitDurationInOpenState(Duration.ofMillis(10000))
            .permittedNumberOfCallsInHalfOpenState(3)
            .build();
  }

  @Bean
  public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {

    TimeLimiterConfig timeLimiterConfig =
            TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(6)).build();
    return factory ->
            factory.configureDefault(
                    id ->
                            new Resilience4JConfigBuilder(id)
                                    .timeLimiterConfig(timeLimiterConfig)
                                    .circuitBreakerConfig(circuitBreakerConfig())
                                    .build());
  }
}
