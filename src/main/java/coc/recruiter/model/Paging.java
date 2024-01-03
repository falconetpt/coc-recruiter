package coc.recruiter.model;

import lombok.Data;

@Data
public class Paging {
  private Cursor cursor;

  @Data
  public class Cursor {
    private String before;
    private String after;

  }
}
