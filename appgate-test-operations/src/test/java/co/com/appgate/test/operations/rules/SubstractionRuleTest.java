package co.com.appgate.test.operations.rules;


import static org.junit.Assert.assertTrue;

import co.com.appgate.test.operations.rules.OperationFact;
import co.com.appgate.test.operations.rules.RuleBookConfiguration;
import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.Result;
import com.deliveredtechnologies.rulebook.model.RuleBook;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    loader = AnnotationConfigContextLoader.class,
    classes = {RuleBookConfiguration.class})
public class SubstractionRuleTest {

    @Autowired
    private RuleBook ruleBook;

    @Test
    public void substractionRuleTest() {

        // setup
        final List<BigDecimal> values = new ArrayList<>();
        values.add(new BigDecimal(6));
        values.add(new BigDecimal(3));
        values.add(new BigDecimal(1));

        final OperationFact fact = new OperationFact("Resta", values);

        final NameValueReferableMap<OperationFact> facts = new FactMap<>();
        facts.setValue("fact", fact);
        ruleBook.setDefaultResult(new BigDecimal(0));
        ruleBook.run(facts);

        // executions
        final Optional<Result> result = ruleBook.getResult();

        // asserts
        assertTrue(result.isPresent());
        assertTrue(new BigDecimal(2).compareTo(new BigDecimal(result.get().toString())) == 0);
    }
}
