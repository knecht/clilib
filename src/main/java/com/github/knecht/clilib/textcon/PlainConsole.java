package com.github.knecht.clilib.textcon;

public class PlainConsole extends Console {

  @Override
  public void printf(IConsoleChannel chan, int c, String format, Object... args) {
    chan.printf(format, args);
  }

}
