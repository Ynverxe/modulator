package com.github.ynverxe.modulator.reflection;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

@ApiStatus.Internal
@ApiStatus.Experimental
public final class PackageClassesResolver {

  private PackageClassesResolver() {
  }

  public static @NotNull List<Class<?>> resolveClasses(
    @NotNull String packageName,
    @NotNull ClassLoader classLoader,
    @NotNull BiPredicate<Package, Class<?>> filter
    ) throws IOException, ClassNotFoundException {
    List<Class<?>> classes = new ArrayList<>();

    Package thePackage = null;

    InputStream stream = classLoader.getResourceAsStream(packageName
      .replaceAll("\\.", "/"));

    if (stream == null) return classes;

    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

    String line;
    while ((line = reader.readLine()) != null) {
      if (!line.endsWith(".class")) continue;
      line = line.substring(0, line.lastIndexOf("."));
      Class<?> clazz = Class.forName(packageName + "." + line, true, classLoader);

      if (thePackage == null) {
        thePackage = clazz.getPackage();
      }

      if (!filter.test(thePackage, clazz)) continue;

      classes.add(clazz);
    }

    reader.close();
    return classes;
  }
}