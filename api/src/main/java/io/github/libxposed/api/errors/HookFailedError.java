package io.github.libxposed.api.errors;

import io.github.libxposed.api.annotations.XposedApiExact;

/**
 * Thrown to indicate that a hook failed due to framework internal error.
 */
@SuppressWarnings("unused")
@XposedApiExact(100)
public class HookFailedError extends XposedFrameworkError {

    @XposedApiExact(100)
    public HookFailedError(String message) {
        super(message);
    }

    @XposedApiExact(100)
    public HookFailedError(String message, Throwable cause) {
        super(message, cause);
    }

    @XposedApiExact(100)
    public HookFailedError(Throwable cause) {
        super(cause);
    }
}
