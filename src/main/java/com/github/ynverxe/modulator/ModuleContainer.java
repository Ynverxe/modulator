package com.github.ynverxe.modulator;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class ModuleContainer implements ModuleAccess {

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

  void notifyFailedModule(Module module) {
    this.modules.remove(module.getClass());
  }

  @Override
  public @NotNull Map<Class<? extends Module>, Module> modules() {
    return Collections.unmodifiableMap(modules);
  }
}