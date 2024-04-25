package com.github.ynverxe.modulator;

import org.jetbrains.annotations.NotNull;

public class ExecutionContext {

  private final @NotNull ModuleContainer mateModules;
  private final @NotNull ModuleRegistry registry;

  ExecutionContext(@NotNull ModuleContainer mateModules, @NotNull ModuleRegistry registry) {
    this.mateModules = mateModules;
    this.registry = registry;
  }

  public @NotNull ModuleContainer mateModules() {
    return mateModules;
  }

  public @NotNull ModuleRegistry registry() {
    return registry;
  }
}