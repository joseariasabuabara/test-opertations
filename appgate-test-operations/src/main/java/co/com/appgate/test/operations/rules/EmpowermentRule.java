package co.com.appgate.test.operations.rules;

import co.com.appgate.test.operations.util.OperationtsMath;
import com.deliveredtechnologies.rulebook.annotation.Given;
import com.deliveredtechnologies.rulebook.annotation.Result;
import com.deliveredtechnologies.rulebook.annotation.Rule;
import com.deliveredtechnologies.rulebook.annotation.Then;
import com.deliveredtechnologies.rulebook.annotation.When;
import java.math.BigDecimal;

@Rule(order = 1)
public class EmpowermentRule {

    @Given("fact")
    private OperationFact fact;

    @Result
    private BigDecimal result;

    @When
    public boolean when() {

        return OperationtsMath.EMPOWERMENT.getMessage().equalsIgnoreCase(fact.getOperation());
    }

    @Then
    public void then() {
        result = fact.getValues().get(0);
        for (int i = 1; i < fact.getValues().size(); i++) {
            result = new BigDecimal(
                Math.pow(result.doubleValue(), fact.getValues().get(i).doubleValue()));
        }
    }


}
