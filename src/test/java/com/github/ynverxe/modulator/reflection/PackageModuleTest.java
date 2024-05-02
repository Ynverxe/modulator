package com.github.ynverxe.modulator.reflection;

import com.github.ynverxe.modulator.Module;
import com.github.ynverxe.modulator.provider.PackageModuleProvider;
import com.github.ynverxe.modulator.reflection.provider.modules.ExcludedModule;
import com.github.ynverxe.modulator.reflection.provider.modules.InvalidConstructorModule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PackageModuleTest {

  @Test
  public void testModuleInstantiation()
    throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    PackageModuleProvider provider = PackageModuleProvider.of(
      "test", "com.github.ynverxe.modulator.reflection.provider.modules");

    List<Module> modules = provider.modules();
    assertFalse(modules.isEmpty(), "No modules loaded");

    for (Module module : modules) {
      assertFalse(module instanceof InvalidConstructorModule);
      assertFalse(module instanceof ExcludedModule);
    }
  }
}