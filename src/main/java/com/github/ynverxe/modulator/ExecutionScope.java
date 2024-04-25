package com.github.ynverxe.modulator;

import com.github.ynverxe.modulator.util.AdaptedBiConsumer;
import org.jetbrains.annotations.NotNull;

public enum ExecutionScope {
  LOAD(Module::load),
  RELOAD(Module::reload),
  END(Module::end);

  private final @NotNull AdaptedBiConsumer<Module, ExecutionContext> executor;

  ExecutionScope(@NotNull AdaptedBiConsumer<Module, ExecutionContext> executor) {
    this.executor = executor;
  }

  public AdaptedBiConsumer<Module, ExecutionContext> executor() {
    return executor;
  }
}