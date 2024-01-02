package coc.recruiter.client;

import lombok.SneakyThrows;

import java.util.List;
import java.util.Set;

public interface CocClient {
  Object getPlayerInfo(String tag);

  @SneakyThrows
  Object getClansPerCountryCodes(Set<String> countries);

  @SneakyThrows
  Object getLocations();

  @SneakyThrows
  List<String> getLocationId(Set<String> countryCodes);
}
