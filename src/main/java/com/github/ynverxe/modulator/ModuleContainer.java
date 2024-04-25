package com.github.ynverxe.modulator;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unchecked")
public class ModuleContainer {

  private final @NotNull String key;
  private final @NotNull Map<Class<? extends Module>, Module> modules;

  ModuleContainer(@NotNull String key, @NotNull List<Module> modules) {
    Map<Class<? extends Module>, Module> moduleMap = new LinkedHashMap<>();
    modules.forEach(module -> {
      if (moduleMap.containsValue(module)) {
        throw new IllegalStateException("Duplicated Module '" + module.getClass() + "' at group "
            + "'" + key + "'");
      }

      moduleMap.put(module.getClass(), module);
    });

    this.key = key;
    this.modules = moduleMap;
  }

  public @NotNull String key() {
    return key;
  }

  public @NotNull Map<Class<? extends Module>, Module> modules() {
    return Collections.unmodifiableMap(modules);
  }

  @SuppressWarnings("unchecked")
  public @NotNull <T extends Module> Optional<T> getModule(@NotNull Class<T> moduleClass) {
    return Optional.of((T) modules.get(moduleClass));
  }

  public @NotNull <T extends Module> T module(@NotNull Class<T> moduleClass) throws IllegalArgumentException {
    return getModule(moduleClass)
        .orElseThrow(() -> new IllegalArgumentException("No module registered of type '" + moduleClass + "'"));
  }

  public boolean isPresent(@NotNull Class<? extends Module> moduleClass) {
    return getModule(moduleClass).isPresent();
  }

  void notifyFailedModule(Module module) {
    this.modules.remove(module.getClass());
  }
}