package com.github.ynverxe.modulator;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Map;

public class ExecutionContext implements ModuleAccess {

  private final @NotNull ModuleContainer mateModules;
  private final @NotNull ModuleRegistry registry;
  private final @NotNull Logger logger;

  ExecutionContext(@NotNull ModuleContainer mateModules, @NotNull ModuleRegistry registry,
      @NotNull Logger logger) {
    this.mateModules = mateModules;
    this.registry = registry;
    this.logger = logger;
  }

  public @NotNull ModuleContainer mateModules() {
    return mateModules;
  }

  public @NotNull ModuleRegistry registry() {
    return registry;
  }

  public @NotNull Logger logger() {
    return logger;
  }

  @Override
  public @NotNull Map<Class<? extends Module>, Module> modules() {
    return mateModules.modules();
  }
}