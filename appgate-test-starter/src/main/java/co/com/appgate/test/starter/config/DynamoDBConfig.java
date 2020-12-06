/**
 * Grupo Aval Acciones y Valores S.A. CONFIDENTIAL
 *
 * <p>Copyright (c) 2018 . All Rights Reserved.
 *
 * <p>NOTICE: This file is subject to the terms and conditions defined in file 'LICENSE', which is
 * part of this source code package.
 */
package co.com.appgate.test.starter.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {

    @Bean
    public DynamoDBMapper getDynamoDBMapper(
        final AmazonDynamoDB amazonDynamoDB,
        @Value("${appGate.session.tableNamePrefix}") final String tableNamePrefix) {

        final DynamoDBMapperConfig dynamoDBMapperConfig =
            DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES)
                .withTableNameOverride(
                    DynamoDBMapperConfig.TableNameOverride.withTableNamePrefix(tableNamePrefix))
                .build();

        return new DynamoDBMapper(amazonDynamoDB, dynamoDBMapperConfig);
    }
}
