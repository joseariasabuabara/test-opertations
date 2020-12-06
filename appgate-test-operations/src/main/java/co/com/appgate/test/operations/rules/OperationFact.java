package co.com.appgate.test.operations.rules;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OperationFact {

    private String operation;
    private List<BigDecimal> values;

}
