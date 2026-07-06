package io.github.libxposed.api.error;

import io.github.libxposed.api.annotations.XposedApiMin;

/**
 * Thrown to indicate that a hook failed due to framework internal error.
 * <p>
 * Design Note: This inherits from {@link Error} rather than {@link RuntimeException} because hook failures are
 * considered fatal framework bugs. Module developers <b>should not</b> attempt to catch this error to provide
 * fallbacks. Instead, please report the issue to the framework maintainers so it can be fixed at the root.
 * </p>
 */
@SuppressWarnings("unused")
@XposedApiMin(101)
public class HookFailedError extends XposedFrameworkError {

    @XposedApiMin(101)
    public HookFailedError(String message) {
        super(message);
    }

    @XposedApiMin(101)
    public HookFailedError(String message, Throwable cause) {
        super(message, cause);
    }

    @XposedApiMin(101)
    public HookFailedError(Throwable cause) {
        super(cause);
    }
}
