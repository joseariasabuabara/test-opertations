/**
 * Grupo Aval Acciones y Valores S.A. CONFIDENTIAL
 *
 * <p>Copyright (c) 2018 . All Rights Reserved.
 *
 * <p>NOTICE: This file is subject to the terms and conditions defined in file 'LICENSE', which is
 * part of this source code package.
 */
package co.com.appgate.test.operations.controller;

import co.com.appgate.test.operations.model.Operation;
import co.com.appgate.test.operations.model.OperatorRequest;
import co.com.appgate.test.operations.model.OperatorResponse;
import co.com.appgate.test.operations.service.IOperatorService;
import co.com.appgate.test.operations.util.StatusInfo;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${adl.endpoint.base}")
@CrossOrigin(origins = "*")
public class OperatorsController {

    private final IOperatorService service;

    @Autowired
    public OperatorsController(final IOperatorService service) {
        this.service = service;
    }

    @PostMapping(
        value = "/operator/execute",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperatorResponse> execute(
        @RequestHeader final HttpHeaders headers, @RequestBody final OperatorRequest request) {
        final Map<String, String> headersMap = headers.toSingleValueMap();

        return service
            .execute(headersMap, request)
            .map(res -> {

                if (StatusInfo.SUCCESFULLY.getCode().equalsIgnoreCase(res.getStatusCode())) {
                    return new ResponseEntity<>(res, HttpStatus.OK);
                } else if (StatusInfo.NOT_ALLOWED.getCode().equalsIgnoreCase(res.getStatusCode())) {
                    return new ResponseEntity<>(res, HttpStatus.METHOD_NOT_ALLOWED);
                } else {
                    return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
                }

            })
            .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }


    @GetMapping(
        value = "/operator/audit/{userId}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Operation>> auditOperations(
        @RequestHeader final HttpHeaders headers, @PathVariable final String userId) {
        final Map<String, String> headersMap = headers.toSingleValueMap();

        return service
            .findOperations(headersMap, userId)
            .map(res -> new ResponseEntity<>(res, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
