/**
 * Grupo Aval Acciones y Valores S.A. CONFIDENTIAL
 *
 * <p>Copyright (c) 2018 . All Rights Reserved.
 *
 * <p>NOTICE: This file is subject to the terms and conditions defined in file 'LICENSE', which is
 * part of this source code package.
 */
package co.com.appgate.test.session.controller;


import co.com.appgate.test.session.model.SessionRequest;
import co.com.appgate.test.session.model.SessionResponse;
import co.com.appgate.test.session.services.ISessionService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${adl.endpoint.base}")
@CrossOrigin(origins = "*")
public class SessionController {

  private final ISessionService service;

  @Autowired
  public SessionController(final ISessionService service) {
    this.service = service;
  }

  @PostMapping(
      value = "session/generation",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SessionResponse> generateTransaction(
      @RequestHeader final HttpHeaders headers, @RequestBody final SessionRequest request) {
    final Map<String, String> headersMap = headers.toSingleValueMap();

    return service
          .generateTransaction(headersMap, request)
          .map(res -> new ResponseEntity<>(res, HttpStatus.OK))
          .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
  }


}
