package com.github.ynverxe.modulator;

public enum ModuleCategory {
  /**
   * Modules that load important resources
   * like configuration files or connects
   * to a database.
   */
  INITIALIZATION,
  /**
   * Modules that implements a functionality.
   */
  FUNCTIONALITY,
  /**
   * Modules that implements ways that user
   * interacts with the functionality. For
   * example: a command line.
   */
  INTERACTION,
  OTHER;

}