package com.github.ynverxe.modulator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface ModuleGroupProvider {

  @NotNull List<@NotNull Module> modules();

  @NotNull String key();

  class Simple implements ModuleGroupProvider {
    private final List<Module> modules;
    private final String key;

    Simple(String key, List<Module> modules) {
      this.modules = modules;
      this.key = key;
    }

    @Override
    public @NotNull List<@NotNull Module> modules() {
      return Collections.unmodifiableList(modules);
    }

    @NotNull
    @Override
    public String key() {
      return key;
    }
  }

  static @NotNull ModuleGroupProvider of(@NotNull String key, @NotNull List<Module> modules) {
    return new Simple(key, modules);
  }

  static @NotNull ModuleGroupProvider of(@NotNull String key, @NotNull Module... modules) {
    return new Simple(key, Arrays.asList(modules));
  }
}