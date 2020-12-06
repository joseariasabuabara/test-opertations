package co.com.appgate.test.operations.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamoDBTable(tableName = "operation")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Operation {

    @DynamoDBHashKey
    private String transactionId;
    private String sessionId;
    private String customerId;
    private String operation;
    private BigDecimal result;
    private Date date;
}