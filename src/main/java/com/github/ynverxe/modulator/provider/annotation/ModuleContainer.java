package com.github.ynverxe.modulator.provider.annotation;

import com.github.ynverxe.modulator.Module;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleContainer {

  /**
   * Excluded Modules.
   */
  @NotNull Class<? extends Module>[] excluded() default {};

}