package com.github.ynverxe.modulator.util;

public interface AdaptedBiConsumer<T, U> {
  void accept(T first, U second) throws Throwable;
}