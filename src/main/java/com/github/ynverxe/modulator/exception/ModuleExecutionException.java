package com.github.ynverxe.modulator.exception;

import com.github.ynverxe.modulator.Module;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;

public class ModuleExecutionException extends RuntimeException {

  private final Module module;

  public ModuleExecutionException(@NotNull String message, @NotNull Throwable cause, @NotNull Module module) {
    super(requireNonNull(message, "message"), requireNonNull(cause, "cause"));
    this.module = requireNonNull(module, "module");
  }

  @Override
  public synchronized @NotNull Throwable getCause() {
    return super.getCause();
  }

  public @NotNull Module getModule() {
    return module;
  }
}