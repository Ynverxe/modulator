package com.github.ynverxe.modulator.provider;

import com.github.ynverxe.modulator.Module;
import com.github.ynverxe.modulator.ModuleGroupProvider;
import com.github.ynverxe.modulator.provider.annotation.ModuleContainer;
import com.github.ynverxe.modulator.reflection.PackageClassesResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PackageModuleProvider implements ModuleGroupProvider {

  private final String moduleKey;
  private final List<Module> modules;

  PackageModuleProvider(String moduleKey, String packageName, @Nullable ClassLoader classLoader)
    throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    this.moduleKey = moduleKey;

    List<Class<? extends Module>> classes = getClasses(packageName, classLoader != null ? classLoader : ClassLoader.getSystemClassLoader());
    this.modules = createInstances(classes);
  }

  @Override
  public @NotNull List<@NotNull Module> modules() {
    return Collections.unmodifiableList(modules);
  }

  @Override
  public @NotNull String key() {
    return moduleKey;
  }

  private static List<Module> createInstances(List<Class<? extends Module>> classes) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    List<Module> modules = new ArrayList<>();
    for (Class<? extends Module> moduleClass : classes) {
      Constructor<? extends Module> constructor = moduleClass.getConstructor();

      boolean accessible = constructor.isAccessible();
      if (!accessible) {
        constructor.setAccessible(true);
      }

      modules.add(constructor.newInstance());
      constructor.setAccessible(accessible);
    }
    return modules;
  }

  @SuppressWarnings("unchecked")
  private static List<Class<? extends Module>> getClasses(String packageName, ClassLoader classLoader) throws IOException, ClassNotFoundException {
    List<Class<? extends Module>> excludedClasses = new ArrayList<>();

    return PackageClassesResolver.resolveClasses(packageName, classLoader, (pkg, clazz) -> {
        ModuleContainer container = pkg.getAnnotation(ModuleContainer.class);

        if (container == null)
          throw new IllegalStateException("Package doesn't have the @ModuleContainer annotation");

        if (!Module.class.isAssignableFrom(clazz)) return false;

        if (excludedClasses.isEmpty()) {
          excludedClasses.addAll(Arrays.asList(container.excluded()));
        }

        if (excludedClasses.contains(clazz)) return false;

        try {
          clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
          return false;
        }

        return true;
    })
      .stream()
      .map(clazz -> (Class<? extends Module>) clazz)
      .collect(Collectors.toList());
  }

  public static @NotNull PackageModuleProvider of(@NotNull String groupKey, @NotNull String packageName, @Nullable ClassLoader classLoader)
    throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    return new PackageModuleProvider(groupKey, packageName, classLoader);
  }

  public static @NotNull PackageModuleProvider of(@NotNull String groupKey, @NotNull String packageName)
    throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    return of(groupKey, packageName, ClassLoader.getSystemClassLoader());
  }
}