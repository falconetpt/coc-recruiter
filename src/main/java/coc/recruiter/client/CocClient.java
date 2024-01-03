package coc.recruiter.client;

import coc.recruiter.model.Clan;
import coc.recruiter.model.Locations;
import coc.recruiter.model.Player;
import coc.recruiter.model.Players;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Set;

public interface CocClient {
  Player getPlayerInfo(String tag);

  List<Clan> getClansPerCountryCodes(Set<String> countries);

  @SneakyThrows
  List<Clan> getClansPerCountryCodes(Set<String> countries,
                                     int limit,
                                     String before,
                                     String after);

  Players getClanMembers(String tag);

  Locations getLocations();

  List<String> getLocationId(Set<String> countryCodes);
}
