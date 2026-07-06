package io.github.libxposed.api;

import io.github.libxposed.api.annotations.XposedApiMin;

/**
 * Super class which all Xposed module entry classes should extend.<br/>
 * Entry classes will be instantiated once for each loaded module generation in a process.
 */
@SuppressWarnings("unused")
@XposedApiMin(101)
public abstract class XposedModule extends XposedInterfaceWrapper implements XposedModuleInterface {
}
