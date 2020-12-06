package co.com.appgate.test.session.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@DynamoDBTable(tableName = "operations-sessions")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionResponse {


    @DynamoDBHashKey
    private String transactionId;
    private String customerId;
    private String status;
    private int expirationTransaction;
    private Date date;

}
