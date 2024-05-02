package com.github.ynverxe.modulator.reflection.provider.modules;

import com.github.ynverxe.modulator.ExecutionContext;
import com.github.ynverxe.modulator.ExecutionPriority;
import com.github.ynverxe.modulator.Module;
import com.github.ynverxe.modulator.ModuleCategory;

public class InvalidConstructorModule implements Module {

  public InvalidConstructorModule(String args) {
  }

  @Override
  public void load(ExecutionContext context) throws Throwable {

  }

  @Override
  public ExecutionPriority executionPriority() {
    return new ExecutionPriority(ModuleCategory.INITIALIZATION, 1);
  }
}