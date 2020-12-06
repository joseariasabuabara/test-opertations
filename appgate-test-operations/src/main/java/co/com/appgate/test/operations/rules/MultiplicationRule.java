package co.com.appgate.test.operations.rules;

import co.com.appgate.test.operations.util.OperationtsMath;
import com.deliveredtechnologies.rulebook.annotation.Given;
import com.deliveredtechnologies.rulebook.annotation.Result;
import com.deliveredtechnologies.rulebook.annotation.Rule;
import com.deliveredtechnologies.rulebook.annotation.Then;
import com.deliveredtechnologies.rulebook.annotation.When;
import java.math.BigDecimal;

@Rule(order = 1)
public class MultiplicationRule {

    @Given("fact")
    private OperationFact fact;

    @Result
    private BigDecimal result;

    @When
    public boolean when() {

        return OperationtsMath.MULTIPLICATION.getMessage().equalsIgnoreCase(fact.getOperation());
    }

    @Then
    public void then() {
        result = fact.getValues().stream().reduce(new BigDecimal(1), this::operation);
    }

    private BigDecimal operation(final BigDecimal number, final BigDecimal number2) {
        return number.multiply(number2);
    }
}
