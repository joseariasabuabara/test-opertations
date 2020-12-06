package co.com.appgate.test.operations.rules;

import com.deliveredtechnologies.rulebook.model.RuleBook;
import com.deliveredtechnologies.rulebook.model.runner.RuleBookRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RuleBookConfiguration {

    @Bean
    public RuleBook ruleBook() {
        return new RuleBookRunner("co.com.appgate.test.operations.rules");
    }
}
