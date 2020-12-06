package co.com.appgate.test.operations.controller;


import co.com.appgate.test.operations.model.OperandsRequest;
import co.com.appgate.test.operations.model.OperandsResponse;
import co.com.appgate.test.operations.service.IOperandsService;
import co.com.appgate.test.operations.util.StatusInfo;
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
public class OperandsController {

    private final IOperandsService service;

    @Autowired
    public OperandsController(final IOperandsService service) {
        this.service = service;
    }

    @PostMapping(
        value = "/operands/add",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperandsResponse> addOperands(
        @RequestHeader final HttpHeaders headers, @RequestBody final OperandsRequest request) {
        final Map<String, String> headersMap = headers.toSingleValueMap();

        return service
            .addOperand(headersMap, request)
            .map(res -> {

                if (StatusInfo.SUCCESFULLY.getCode().equalsIgnoreCase(res.getStatusCode())) {
                    return new ResponseEntity<>(res, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
                }
            })
            .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }


}
