package com.github.ynverxe.modulator;

import com.github.ynverxe.modulator.exception.ModuleExecutionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

public class ModuleRegistry {

  private final @NotNull Logger logger;
  private final Map<String, ModuleContainer> containers = new ConcurrentHashMap<>();
  private volatile FailedExecutionStrategy failedExecutionStrategy = (groupKey, failed, error, scope) -> {};

  public ModuleRegistry(@NotNull Logger logger) {
    this.logger = requireNonNull(logger, "logger");
  }

  public ModuleRegistry() {
    this(LoggerFactory.getLogger(ModuleRegistry.class));
  }

  public @NotNull ModuleContainer loadModuleGroup(@NotNull ModuleGroupProvider moduleGroupProvider) {
    String key = moduleGroupProvider.key();
    requireNonNull(key, "key");

    if (containers.containsKey(key)) {
      throw new IllegalArgumentException("Module with key '" + key + "' is already in use");
    }

    List<Module> modules = new ArrayList<>(moduleGroupProvider.modules());
    modules.forEach(module -> requireNonNull(module, "Null Module found on group '" + key + "'"));
    modules.sort(Comparator.comparing(Module::executionPriority));

    ModuleContainer container = new ModuleContainer(key, modules);

    for (Module executeModule : executeModules(container, ExecutionScope.LOAD)) {
      container.notifyFailedModule(executeModule);
    }

    this.containers.put(key, container);
    return container;
  }

  public boolean reloadModules(@NotNull String groupKey) {
    return getContainer(groupKey)
        .map(container -> {
          executeModules(container, ExecutionScope.RELOAD);
          return true;
        })
        .orElse(false);
  }

  public boolean endModules(@NotNull String groupKey) {
    return getContainer(groupKey)
        .map(container -> {
          try {
            executeModules(container, ExecutionScope.END);
          } finally {
            this.containers.remove(groupKey);
          }
          return true;
        })
        .orElse(false);
  }

  public @NotNull Optional<ModuleContainer> getContainer(@NotNull String key) {
    return Optional.ofNullable(containers.get(key));
  }

  @Contract("_ -> this")
  public ModuleRegistry failedExecutionStrategy(@NotNull FailedExecutionStrategy failedExecutionStrategy) {
    this.failedExecutionStrategy = requireNonNull(failedExecutionStrategy, "failedExecutionStrategy");
    return this;
  }

  public @NotNull FailedExecutionStrategy failedExecutionStrategy() {
    return failedExecutionStrategy;
  }

  private List<Module> executeModules(ModuleContainer container, ExecutionScope scope) {
    List<Module> failedModules = new ArrayList<>();
    ExecutionContext context = new ExecutionContext(container, this, logger);
    Collection<Module> modules = container.modules().values();

    for (Module module : modules) {
      try {
        scope.executor().accept(module, context);
      } catch (Throwable e) {
        failedModules.add(module);

        if (module.isOptional()) {
          logger.error("A Module of group '" + container.key() + "' "
              + "caused an exception", e);

          continue;
        }

        failedExecutionStrategy.handle(container.key(), module, e, scope);

        throw new ModuleExecutionException(
            "Non-optional Module '"
                + module.getClass()
                + "' from group "
                + "'"
                + container.key()
                + "' caused an exception",
            e, module);
      }
    }

    return failedModules;
  }
}