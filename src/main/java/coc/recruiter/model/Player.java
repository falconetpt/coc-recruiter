package coc.recruiter.model;

import lombok.Data;
import java.util.List;


@Data
public class Player {
  public String tag;
  public String name;
  public int townHallLevel;
  public int townHallWeaponLevel;
  public int expLevel;
  public int trophies;
  public int bestTrophies;
  public int warStars;
  public int attackWins;
  public int defenseWins;
  public int builderHallLevel;
  public int builderBaseTrophies;
  public int bestBuilderBaseTrophies;
  public String role;
  public String warPreference;
  public int donations;
  public int donationsReceived;
  public long clanCapitalContributions;
  public Clan clan;
  public League league;
  public List<Troop> troops;
  public List<Hero> heroes;
  public List<HeroEquipment> heroEquipment;
  public List<Spell> spells;
}

