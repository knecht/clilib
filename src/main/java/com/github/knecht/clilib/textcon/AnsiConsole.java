package com.github.knecht.clilib.textcon;

public class AnsiConsole extends Console {

  @Override
  public void printf(IConsoleChannel chan, int c, String format, Object... args) {
    String s = "\u001b[";
    if ((c & Color.bold) != 0) {
      s += "01;";
    }
    if ((c & Color.underline) != 0) {
      s += "04;";
    }
    if ((c & Color.dark) != 0) {
      s += "02;";
    }
    s += "3" + (c & 7) + "m";
    chan.printf(s + format + "\u001b[00m", args);
  }

}
