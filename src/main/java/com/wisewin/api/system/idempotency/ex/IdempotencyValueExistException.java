package com.wisewin.api.system.idempotency.ex;

/**
 * Created by Shibo on 16/8/29.
 */
public class IdempotencyValueExistException extends Exception {

    public IdempotencyValueExistException() {}


    public IdempotencyValueExistException(String notes) {
        super(notes);
    }
}
