package co.com.appgate.test.operations.util;

import lombok.Getter;

@Getter
public enum StatusInfo {
    SUCCESFULLY("0", "Successful"),
    NOT_AUTHORIZED("303", "Session incorrect, not authorized"),
    NOT_ALLOWED("304", "Operation not allowed"),
    MISSING_OPERAND("305", "Missing operand");

    final String code;
    final String message;

    StatusInfo(final String code, final String message) {
        this.code = code;
        this.message = message;
    }
}
