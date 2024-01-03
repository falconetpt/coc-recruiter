package coc.recruiter.service;

import coc.recruiter.model.Player;

import java.util.List;
import java.util.Set;

public interface RecruitingCalculator {
  List<Player> listEligiblePlayers(Set<String> countryCodes);
}
