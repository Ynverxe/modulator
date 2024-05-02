package com.github.ynverxe.modulator.exception;

import com.github.ynverxe.modulator.Module;
import org.jetbrains.annotations.NotNull;

public class MissingModuleException extends RuntimeException {

  private final @NotNull Class<? extends Module> moduleClass;

  public MissingModuleException(String message, @NotNull Class<? extends Module> moduleClass, boolean format) {
    super(format ? String.format(message, message) : message);
    this.moduleClass = moduleClass;
  }

  public Class<? extends Module> moduleClass() {
    return moduleClass;
  }
}