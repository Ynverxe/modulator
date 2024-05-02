package com.github.ynverxe.modulator;

import com.github.ynverxe.modulator.exception.MissingModuleException;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface ModuleAccess {

  @NotNull Map<Class<? extends Module>, Module> modules();

  @SuppressWarnings("unchecked")
  default @NotNull <T extends Module> Optional<T> getModule(@NotNull Class<T> moduleClass) {
    return Optional.of((T) modules().get(moduleClass));
  }

  default @NotNull <T extends Module> T module(@NotNull Class<T> moduleClass) throws MissingModuleException {
    return getModule(moduleClass)
      .orElseThrow(() -> new MissingModuleException("No module registered of type '%s'", moduleClass, true));
  }

  default boolean isPresent(@NotNull Class<? extends Module> moduleClass) {
    return getModule(moduleClass).isPresent();
  }

  default <M extends Module, T> @NotNull Optional<T> mapModuleSafely(@NotNull Class<M> moduleClass, @NotNull Function<M, T> moduleMapper) {
    return getModule(moduleClass)
      .map(moduleMapper);
  }

  default <M extends Module, T> @NotNull T mapModule(@NotNull Class<M> moduleClass, @NotNull Function<M, T> moduleMapper)
    throws MissingModuleException {
    return moduleMapper.apply(module(moduleClass));
  }
}