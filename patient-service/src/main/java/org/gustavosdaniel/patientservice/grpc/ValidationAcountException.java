package org.gustavosdaniel.patientservice.grpc;

import io.grpc.StatusRuntimeException;
import org.gustavosdaniel.patientservice.exception.BaseException;

public class ValidationAcountException extends BaseException {

    public ValidationAcountException() {
    }

    public ValidationAcountException(String message) {
        super(message);
    }

    public ValidationAcountException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationAcountException(Throwable cause) {
        super(cause);
    }

    public ValidationAcountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
