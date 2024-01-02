package coc.recruiter.model;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse<T> {
  private List<T> items;
}
