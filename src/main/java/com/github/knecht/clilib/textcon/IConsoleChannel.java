package com.github.knecht.clilib.textcon;

public interface IConsoleChannel {

  public void printf(String format, Object... args);

  public void printf(int color, String format, Object... args);
}
