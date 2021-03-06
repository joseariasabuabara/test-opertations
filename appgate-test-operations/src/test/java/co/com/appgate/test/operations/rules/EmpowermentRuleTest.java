/**
 * Grupo Aval Acciones y Valores S.A. CONFIDENTIAL
 *
 * <p>Copyright (c) 2018 . All Rights Reserved.
 *
 * <p>NOTICE: This file is subject to the terms and conditions defined in file 'LICENSE', which is
 * part of this source code package.
 */
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
import org.junit.Assert;
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
public class EmpowermentRuleTest {

    @Autowired
    private RuleBook ruleBook;

    @Test
    public void empowermentRuleTest() {

        // setup
        final List<BigDecimal> values = new ArrayList<>();
        values.add(new BigDecimal(5));
        values.add(new BigDecimal(2));
        values.add(new BigDecimal(2));

        final OperationFact fact = new OperationFact("potenciacion", values);

        final NameValueReferableMap<OperationFact> facts = new FactMap<>();
        facts.setValue("fact", fact);
        ruleBook.setDefaultResult(new BigDecimal(0));
        ruleBook.run(facts);

        // executions
        final Optional<Result> result = ruleBook.getResult();

        // asserts
        assertTrue(result.isPresent());
        Assert.assertEquals(new BigDecimal(625), new BigDecimal(result.get().toString()));
    }
}
