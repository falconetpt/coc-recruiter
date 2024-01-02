package coc.recruiter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class CocApiConfig {

  @Bean
  public WebClient webClient(final @Value("${coc.base.url}") String url,
                             final @Value("${coc.api.token}") String token) {
    final var factory = new DefaultUriBuilderFactory(url);
    factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
    final var exchangeStrategies = ExchangeStrategies.builder()
        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024));

    return WebClient.builder()
        .baseUrl(url)
        .uriBuilderFactory(factory)
        .exchangeStrategies(exchangeStrategies.build())
        .defaultHeader("Authorization", "Bearer %s".formatted(token))
        .build();
  }
}
