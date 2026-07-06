package io.github.libxposed.api.error;

import io.github.libxposed.api.annotations.XposedApiMin;

/**
 * Thrown to indicate that the Xposed framework function is broken.
 */
@XposedApiMin(101)
public class XposedFrameworkError extends Error {

    @XposedApiMin(101)
    public XposedFrameworkError(String message) {
        super(message);
    }

    @XposedApiMin(101)
    public XposedFrameworkError(String message, Throwable cause) {
        super(message, cause);
    }

    @XposedApiMin(101)
    public XposedFrameworkError(Throwable cause) {
        super(cause);
    }
}
