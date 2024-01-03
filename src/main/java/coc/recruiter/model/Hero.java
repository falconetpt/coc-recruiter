package coc.recruiter.model;

import lombok.Data;

import java.util.List;

@Data
public class Hero {
  public String name;
  public int level;
  public int maxLevel;
  public List<Equipment> equipment;
  public String village;
}
