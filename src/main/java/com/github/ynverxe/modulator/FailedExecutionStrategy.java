package com.github.ynverxe.modulator;

import org.jetbrains.annotations.NotNull;

public interface FailedExecutionStrategy {
  void handle(@NotNull String groupKey, @NotNull Module failed, @NotNull Throwable error,
      @NotNull ExecutionScope scope);
}