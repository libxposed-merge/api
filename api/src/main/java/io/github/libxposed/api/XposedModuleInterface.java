package io.github.libxposed.api;

import android.app.AppComponentFactory;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.List;

import io.github.libxposed.annotation.SinceApi;
import io.github.libxposed.api.annotations.XposedApiExact;
import io.github.libxposed.api.annotations.XposedApiMin;

/**
 * Interface for module initialization.
 */
@SuppressWarnings("unused")
public interface XposedModuleInterface {
    /**
     * Wraps information about the process in which the module is loaded.
     */
    interface ModuleLoadedParam {
        /**
         * Returns whether the current process is system server.
         *
         * @return {@code true} if the current process is system server
         */
        boolean isSystemServer();

        /**
         * Gets the process name.
         *
         * @return The process name
         */
        @NonNull
        String getProcessName();
    }

    /**
     * Wraps information about system server.
     */
    @XposedApiExact(100)
    interface SystemServerLoadedParam {
        /**
         * Gets the class loader of system server.
         *
         * @return The class loader
         */
        @NonNull
        ClassLoader getClassLoader();
    }

    /**
     * Wraps information about the package being loaded.
     */
    interface PackageLoadedParam {
        /**
         * Gets the package name of the current package.
         *
         * @return The package name.
         */
        @NonNull
        String getPackageName();

        /**
         * Gets the {@link ApplicationInfo} of the current package.
         *
         * @return The ApplicationInfo.
         */
        @NonNull
        ApplicationInfo getApplicationInfo();

        /**
         * Returns whether this is the first and main package loaded in the app process.
         *
         * @return {@code true} if this is the first package.
         */
        boolean isFirstPackage();

        /**
         * Gets the default classloader of the current package. This is the classloader that loads
         * the app's code, resources and custom {@link AppComponentFactory}.
         */
        @RequiresApi(Build.VERSION_CODES.Q)
        @NonNull
        ClassLoader getDefaultClassLoader();

        /**
         * Gets the class loader of the package being loaded.
         *
         * @return The class loader.
         */
        @XposedApiExact(100)
        @NonNull
        ClassLoader getClassLoader();

    }

    /**
     * Wraps information about the package whose classloader is ready.
     */
    @XposedApiMin(101)
    interface PackageReadyParam extends PackageLoadedParam {
        /**
         * Gets the classloader of the current package. It may be different from {@link #getDefaultClassLoader()}
         * if the package has a custom {@link android.app.AppComponentFactory} that creates a different classloader.
         */
        @NonNull
        ClassLoader getClassLoader();

        /**
         * Gets the {@link AppComponentFactory} of the current package.
         */
        @RequiresApi(Build.VERSION_CODES.P)
        @NonNull
        AppComponentFactory getAppComponentFactory();
    }

    /**
     * Wraps information about system server.
     */
    @XposedApiMin(101)
    interface SystemServerStartingParam {
        /**
         * Gets the class loader of system server.
         */
        @NonNull
        ClassLoader getClassLoader();
    }

    /**
     * Wraps information about the hot reloading event.
     */
    @SinceApi(XposedInterface.API_102)
    @XposedApiMin(102)
    interface HotReloadingParam {
        /**
         * Gets the data passed from the module app when triggering hot reload through the service.
         * This can be null if the app passes {@code null} or the hot reload is triggered by app updating.
         * The bundle should contain only values that can be unmarshalled without the module's class
         * loader, such as primitive values, strings, arrays, and framework {@link Bundle} instances.
         */
        @Nullable
        Bundle getExtras();

        /**
         * Sets the data to be passed to the new code after hot reloading. This can be retrieved in
         * {@link #onHotReloaded(HotReloadedParam)}. The saved state must not contain objects created
         * under the old module classloader because retaining them in the new generation can keep the
         * old generation strongly reachable after hot reload. Use classloader-neutral values to
         * transfer state.
         * <p>
         * Implementations should reject old-module-classloader objects when they are detected. This
         * check is a diagnostic aid, not a complete object graph verifier; passing an undetected
         * old-module object is still a module lifecycle bug.
         * </p>
         *
         * @param outState The data to be passed to the new code after hot reloading
         * @throws IllegalArgumentException if {@code outState} contains an object detected as being
         *                                  created under the old module classloader
         */
        void setSavedInstanceState(@Nullable Object outState);
    }

    /**
     * Wraps information about the hot reloaded event.
     */
    @SinceApi(XposedInterface.API_102)
    @XposedApiMin(102)
    interface HotReloadedParam extends ModuleLoadedParam {
        /**
         * Gets the data passed from the module app when triggering hot reload. This can be null if the
         * app passes {@code null} or the hot reload is triggered by app updating.
         */
        @Nullable
        Bundle getExtras();

        /**
         * Gets the data set in {@link HotReloadingParam#setSavedInstanceState(Object)}.
         */
        @Nullable
        Object getSavedInstanceState();

        /**
         * Gets a list of hook handles created by the previous generation of this module. The new
         * code can choose to remove or atomically replace these hooks with new ones through
         * {@link XposedInterface.HookHandle#replaceHook(XposedInterface.Hooker)}.
         */
        @NonNull
        List<XposedInterface.HookHandle> getOldHookHandles();
    }

    /**
     * Gets notified when the module is loaded into the target process.<br/>
     * This callback is guaranteed to be called exactly once for a process.
     *
     * @param param Information about the process in which the module is loaded
     */
    @XposedApiMin(101)
    default void onModuleLoaded(@NonNull ModuleLoadedParam param) {
    }

    /**
     * Gets notified when a package is loaded into the app process. This is the time when the default
     * classloader is ready but before the instantiation of custom {@link android.app.AppComponentFactory}.<br/>
     * This callback could be invoked multiple times for the same process on each package.
     *
     * @param param Information about the package being loaded
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    default void onPackageLoaded(@NonNull PackageLoadedParam param) {
    }

    /**
     * Gets notified when custom {@link android.app.AppComponentFactory} has instantiated the app
     * classloader and is ready to create {@link android.app.Activity} and {@link android.app.Service}.<br/>
     * This callback could be invoked multiple times for the same process on each package.
     *
     * @param param Information about the package being loaded
     */
    @XposedApiMin(101)
    default void onPackageReady(@NonNull PackageReadyParam param) {
    }

    /**
     * Gets notified when system server is ready to start critical services.
     *
     * @param param Information about system server
     */
    @XposedApiMin(101)
    default void onSystemServerStarting(@NonNull SystemServerStartingParam param) {
    }

    /**
     * Gets notified when the system server is loaded.
     *
     * @param param Information about system server
     */
    @XposedApiExact(100)
    default void onSystemServerLoaded(@NonNull SystemServerLoadedParam param) {
    }

    /**
     * Gets notified when the module is about to be reloaded. This callback is called when hot
     * reloading is triggered through the service, or by app updating if {@code autoHotReload} is set
     * to true in {@code module.prop}. App-update hot reloading still proceeds only if this callback
     * returns {@code true}.
     * <p>This callback runs in <b>old</b> code.</p>
     * <p>
     * Hot reloads are serialized per target. Before the old hook handle list is captured, the
     * framework freezes old code so further hook registrations from old code fail. In-flight hook
     * calls keep using the hook chain snapshot that was active when they started.
     * </p>
     * <p>
     * Returning {@code true} declares that the old generation is ready to be retired. Before
     * returning {@code true}, modules must stop all module-owned Java and native threads,
     * unregister native hooks and external callbacks, release JNI global references to
     * module-classloader objects, and clear references to module objects stored by system
     * or app classes.
     * </p>
     * <p>
     * Returning {@code false} rejects the hot reload request. For service-triggered requests, this
     * is reported as {@code HotReloadResult.Status.FAILED} with a null message. If this callback
     * or the subsequent reload operation throws, the request is reported as failed with a
     * framework-provided diagnostic message.
     * </p>
     *
     * @param param Information about the hot reloading event
     * @return {@code true} to allow hot reloading to proceed, {@code false} to cancel hot reloading
     */
    @SinceApi(XposedInterface.API_102)
    @XposedApiMin(102)
    default boolean onHotReloading(@NonNull HotReloadingParam param) {
        return false;
    }

    /**
     * Gets notified when the module has been reloaded.
     * <p>This callback runs in <b>new</b> code.</p>
     * <p>
     * Package lifecycle callbacks are not automatically replayed after hot reload. Override this
     * method to atomically replace old hooks through
     * {@link XposedInterface.HookHandle#replaceHook(XposedInterface.Hooker)}, remove hooks that
     * should not survive, or perform reload-specific initialization. The default implementation
     * only unhooks all old hooks.
     * </p>
     * <p>
     * The framework keeps the previous module generation strongly reachable until this callback
     * finishes. After this callback returns or throws, the framework releases all references it owns
     * to the old generation, except for references required by old hooks that remain installed and
     * any references kept by module code. Classloader collection and unloading of native libraries
     * associated with it are runtime-dependent and are not guaranteed to happen immediately.
     * </p>
     * <p>
     * The framework does not call {@code UnregisterNatives}, {@code JNI_OnUnload}, or
     * {@code dlclose} as part of hot reload. If all old Java references are removed while old
     * native threads, callbacks, hooks, or JNI global references are still active, any resulting
     * crash or undefined behavior is a module bug.
     * </p>
     *
     * @param param Information about the hot reloaded event
     */
    @SinceApi(XposedInterface.API_102)
    @XposedApiMin(102)
    default void onHotReloaded(@NonNull HotReloadedParam param) {
        param.getOldHookHandles().forEach(XposedInterface.HookHandle::unhook);
    }
}
