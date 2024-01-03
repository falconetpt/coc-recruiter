package coc.recruiter.model;

import java.util.List;

public class PagingResponse<T> {
  private List<T> items;
  private Paging paging;
}
