package coc.recruiter.model;

import lombok.Data;

@Data
public class Troop {
  public String name;
  public int level;
  public int maxLevel;
  public String village;
  public boolean superTroopIsActive;
}
