package coc.recruiter.model;

import lombok.Data;

@Data
public class Clan {
  private String tag;
  private String name;
  private long warWins;
  private long warTies;
  private long warLosses;
  private int members;

  private League warLeague;

  private ChatLanguage chatLanguage;

  private String requiredTownhallLevel;
}
