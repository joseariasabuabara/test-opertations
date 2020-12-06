package co.com.appgate.test.operations.util;

import lombok.Getter;

@Getter
public enum OperationtsMath {
    SUM("0", "Suma"),
    SUBSTRACTION("1", "Resta"),
    MULTIPLICATION("2", "Multiplicacion"),
    DIVISION("3", "Division"),
    EMPOWERMENT("4", "Potenciacion");

    final String code;
    final String message;

    OperationtsMath(final String code, final String message) {
        this.code = code;
        this.message = message;
    }
}
