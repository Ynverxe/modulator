package com.github.ynverxe.modulator;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;

public interface ModuleAccess {

  @NotNull Map<Class<? extends Module>, Module> modules();

  @SuppressWarnings("unchecked")
  default @NotNull <T extends Module> Optional<T> getModule(@NotNull Class<T> moduleClass) {
    return Optional.of((T) modules().get(moduleClass));
  }

  default @NotNull <T extends Module> T module(@NotNull Class<T> moduleClass) throws IllegalArgumentException {
    return getModule(moduleClass)
      .orElseThrow(() -> new IllegalArgumentException("No module registered of type '" + moduleClass + "'"));
  }

  default boolean isPresent(@NotNull Class<? extends Module> moduleClass) {
    return getModule(moduleClass).isPresent();
  }
}