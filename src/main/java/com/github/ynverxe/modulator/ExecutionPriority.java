package com.github.ynverxe.modulator;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class ExecutionPriority implements Comparable<ExecutionPriority> {

  private final @NotNull ModuleCategory category;
  private final int priority;

  public ExecutionPriority(@NotNull ModuleCategory category, int priority) {
    this.category = Objects.requireNonNull(category, "category");
    this.priority = priority;
  }

  public ModuleCategory category() {
    return category;
  }

  public int priority() {
    return priority;
  }

  @Override
  public int compareTo(@NotNull ExecutionPriority o) {
    int categoryValue = category.compareTo(o.category);

    if (categoryValue == 0) {
      return Integer.compare(priority, o.priority);
    }

    return categoryValue;
  }
}