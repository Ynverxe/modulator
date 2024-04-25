package com.github.ynverxe.modulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExecutionPriorityTest {
  @Test
  public void testOrder() {
    ExecutionPriority forConfiguration = new ExecutionPriority(
        ModuleCategory.INITIALIZATION,
        0
    );

    ExecutionPriority forDatabase = new ExecutionPriority(
        ModuleCategory.INITIALIZATION,
        1
    );

    ExecutionPriority forCLISupport = new ExecutionPriority(
        ModuleCategory.INTERACTION,
        0
    );

    List<ExecutionPriority> priorities = new ArrayList<>(
        Arrays.asList(forDatabase, forCLISupport, forConfiguration));

    priorities.sort(Comparator.comparing(Function.identity()));

    assertEquals(forConfiguration, priorities.get(0));
    assertEquals(forDatabase, priorities.get(1));
    assertEquals(forCLISupport, priorities.get(2));
  }
}