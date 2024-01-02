package coc.recruiter.controller;

import coc.recruiter.client.CocClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/info")
public class InfoController {
  private static final Set<String> COUNTRY_CODES = Set.of("PT", "BR");
  private final CocClient cocClient;

  @GetMapping("/player/{tag}")
  public ResponseEntity<?> getInfo(@PathVariable final String tag) {
    return ResponseEntity.ok(cocClient.getPlayerInfo("#" + tag.toUpperCase()));
  }

  @GetMapping("/clan")
  public ResponseEntity<?> clanInfo() {
    return ResponseEntity.ok(cocClient.getClansPerCountryCodes(COUNTRY_CODES));
  }

  @GetMapping("/locations")
  public ResponseEntity<?> locations() {
    return ResponseEntity.ok(cocClient.getLocationId(COUNTRY_CODES));
  }
}
