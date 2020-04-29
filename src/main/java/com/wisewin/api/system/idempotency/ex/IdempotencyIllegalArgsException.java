package com.wisewin.api.system.idempotency.ex;

/**
 * Created by Shibo on 16/8/29.
 */
public class IdempotencyIllegalArgsException extends Exception {

    public IdempotencyIllegalArgsException () {}


    public IdempotencyIllegalArgsException (String notes) {
        super(notes);
    }
}
