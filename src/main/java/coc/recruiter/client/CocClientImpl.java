package coc.recruiter.client;

import coc.recruiter.model.Clans;
import coc.recruiter.model.Location;
import coc.recruiter.model.Locations;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.yaml.snakeyaml.util.UriEncoder;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class CocClientImpl implements CocClient {
  private static final String PLAYER_SEARCH = "/players/%s";
  private static final String LOCATION_LISTING = "/locations";
  private static final String CLAN_SEARCH = "/clans";
  private final WebClient webClient;

  @Override
  @SneakyThrows
  public Object getPlayerInfo(final String tag) {
    return webClient
        .get()
        .uri(UriEncoder.encode(PLAYER_SEARCH.formatted(tag)))
        .retrieve()
        .bodyToMono(Object.class)
        .block();
  }

  @Override
  @SneakyThrows
  public List<Clans> getClansPerCountryCodes(final Set<String> countries) {
    return getLocationId(countries)
        .stream()
        .map(this::fetchClanPerCountry)
        .collect(Collectors.toList());
  }

  @Override
  @SneakyThrows
  public Locations getLocations() {
    return webClient
        .get()
        .uri(LOCATION_LISTING)
        .retrieve()
        .bodyToMono(Locations.class)
        .block();
  }

  @Override
  @SneakyThrows
  public List<String> getLocationId(final Set<String> countryCodes) {
    return getLocations()
        .getItems()
        .stream()
        .filter(e -> Objects.nonNull(e.getCountryCode()))
        .filter(e -> countryCodes.contains(e.getCountryCode()))
        .map(Location::getId)
        .collect(Collectors.toList());
  }

  private Clans fetchClanPerCountry(final String countryCode) {
    log.info("Fetching clan info for location {}", countryCode);
    return webClient
        .get()
        .uri(uriBuilder -> uriBuilder
            .path(CLAN_SEARCH)
            .queryParam("locationId", countryCode)
            .build())
        .retrieve()
        .bodyToMono(Clans.class)
        .block();
  }
}
