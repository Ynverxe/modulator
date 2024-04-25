package com.github.ynverxe.modulator;

import org.jetbrains.annotations.NotNull;

public interface Module {

  void load(@NotNull ExecutionContext context) throws Throwable;

  default void end(@NotNull ExecutionContext context) throws Throwable {}

  default void reload(@NotNull ExecutionContext context) throws Throwable {}

  @NotNull ExecutionPriority executionPriority();

  default boolean isOptional() {
    return false;
  }
}