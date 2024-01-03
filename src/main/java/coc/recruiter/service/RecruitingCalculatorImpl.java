package coc.recruiter.service;

import coc.recruiter.client.CocClient;
import coc.recruiter.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruitingCalculatorImpl implements RecruitingCalculator {
  private final CocClient cocClient;
  @Value("${coc.exclusion.town.hall.min}")
  private final int minTownHallLevel;

  @Override
  public List<Player> listEligiblePlayers(Set<String> countryCodes) {
    return cocClient.getClansPerCountryCodes(countryCodes, 10, null, null)
        .parallelStream()
        .flatMap(c -> cocClient.getClanMembers(c.getTag()).getItems().stream())
        .map(p -> cocClient.getPlayerInfo(p.tag))
        .filter(p -> p.townHallLevel >= minTownHallLevel)
        .collect(Collectors.toList());
  }
}
