package com.github.ynverxe.modulator.reflection.provider.modules;

import com.github.ynverxe.modulator.ExecutionContext;
import com.github.ynverxe.modulator.ExecutionPriority;
import com.github.ynverxe.modulator.Module;

public class ExcludedModule implements Module {
  @Override
  public void load(ExecutionContext context) throws Throwable {

  }

  @Override
  public ExecutionPriority executionPriority() {
    return null;
  }
}