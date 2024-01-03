package coc.recruiter.client;

import coc.recruiter.model.Clan;
import coc.recruiter.model.Clans;
import coc.recruiter.model.Location;
import coc.recruiter.model.Locations;
import coc.recruiter.model.Player;
import coc.recruiter.model.Players;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.yaml.snakeyaml.util.UriEncoder;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class CocClientImpl implements CocClient {
  private static final String PLAYER_SEARCH = "/players/%s";
  private static final String LOCATION_LISTING = "/locations";
  private static final String CLAN_SEARCH = "/clans";
  private static final String CLAN_MEMBERS = CLAN_SEARCH.concat("/%s/members");
  private final WebClient webClient;

  @Override
  @SneakyThrows
  public Player getPlayerInfo(final String tag) {
    log.info("Fetching info for player {}", tag);
    return webClient
        .get()
        .uri(UriEncoder.encode(PLAYER_SEARCH.formatted(tag)))
        .retrieve()
        .bodyToMono(Player.class)
        .block();
  }

  @Override
  @SneakyThrows
  public List<Clan> getClansPerCountryCodes(final Set<String> countries) {
    log.info("Fetching info clans in {}", countries);
    return getLocationId(countries)
        .stream()
        .map(this::fetchClanPerCountry)
        .map(Clans::getItems)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  @Override
  @SneakyThrows
  public List<Clan> getClansPerCountryCodes(final Set<String> countries,
                                            final int limit,
                                            final String before,
                                            final String after) {
    log.info("Fetching info clans in {}", countries);
    return getLocationId(countries)
        .stream()
        .map(c -> fetchClanPerCountry(c, limit, before, after))
        .map(Clans::getItems)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  @Override
  public Players getClanMembers(final String tag) {
    log.info("Fetching members for clan {}", tag);
    return webClient
        .get()
        .uri(UriEncoder.encode(CLAN_MEMBERS.formatted(tag)))
        .retrieve()
        .bodyToMono(Players.class)
        .block();
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
    final var map = new LinkedMultiValueMap<String, String>();
    map.put("locationId", List.of(countryCode));

    return getClans(map);
  }

  private Clans fetchClanPerCountry(final String countryCode,
                                    final int limit,
                                    final String before,
                                    final String after) {
    log.info("Fetching clan info for location {}", countryCode);
    final var map = new LinkedMultiValueMap<String, String>();
    map.put("locationId", List.of(countryCode));
    Optional.ofNullable(limit)
        .map(String::valueOf)
        .filter(Strings::isNotBlank)
        .ifPresent(e -> map.put("limit", List.of(e)));

    Optional.ofNullable(before)
        .map(String::valueOf)
        .filter(Strings::isNotBlank)
        .ifPresent(e -> map.put("before", List.of(e)));

    Optional.ofNullable(after)
        .map(String::valueOf)
        .filter(Strings::isNotBlank)
        .ifPresent(e -> map.put("after", List.of(e)));

    return getClans(map);
  }

  private Clans getClans(final MultiValueMap<String, String> map) {
    return webClient
        .get()
        .uri(uriBuilder -> uriBuilder
            .path(CLAN_SEARCH)
            .queryParams(map)
            .build())
        .retrieve()
        .bodyToMono(Clans.class)
        .block();
  }
}
