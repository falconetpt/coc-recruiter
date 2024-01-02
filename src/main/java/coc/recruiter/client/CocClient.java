package coc.recruiter.client;

import lombok.SneakyThrows;

import java.util.List;
import java.util.Set;

public interface CocClient {
  Object getPlayerInfo(String tag);

  Object getClansPerCountryCodes(Set<String> countries);

  Object getLocations();

  List<String> getLocationId(Set<String> countryCodes);
}
