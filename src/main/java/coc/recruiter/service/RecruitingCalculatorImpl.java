package coc.recruiter.service;

import coc.recruiter.client.CocClient;
import coc.recruiter.model.Clan;
import coc.recruiter.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruitingCalculatorImpl implements RecruitingCalculator {
  private final CocClient cocClient;
  @Value("${coc.exclusion.town.hall.min}")
  private int minTownHallLevel;

  @Override
  public List<Player> listEligiblePlayers(Set<String> countryCodes) {
    return cocClient.getClansPerCountryCodes(countryCodes, 100, null, null)
        .parallelStream()
        .flatMap(c -> cocClient.getClanMembers(c.getTag()).getItems().stream().peek(e -> e.setClan(c)))
        .filter(p -> p.townHallLevel >= minTownHallLevel)
        .collect(Collectors.toList());
  }
}
