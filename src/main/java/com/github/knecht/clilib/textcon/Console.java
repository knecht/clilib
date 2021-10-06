package com.github.knecht.clilib.textcon;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public abstract class Console {

  private static Console instance;

  public static Console get() {
    if (null == instance) {
      synchronized (Console.class) {
        if (null == instance) {
          if (File.pathSeparatorChar == ';') {
            try {
              instance = new WConsole();
            } catch (Throwable e) {
              instance = new PlainConsole();
            }
          } else {
            instance = new AnsiConsole();
          }
        }
      }
    }
    return instance;
  }

  IConsoleChannel out;

  public IConsoleChannel err;

  @Deprecated
  IConsoleChannel defChannel;

  BufferedReader in;

  protected Console() {
    in = new BufferedReader(new InputStreamReader(System.in));
    out = new ConsoleChannel(System.out, this);
    err = new ConsoleChannel(System.err, this);
    defChannel = out;
  }

  class ConsoleChannel implements IConsoleChannel {

    PrintStream ps;

    Console con;

    public ConsoleChannel(PrintStream ps, Console console) {
      this.ps = ps;
      this.con = console;
    }

    @Override
    public void printf(String format, Object... args) {
      synchronized (con) {
        ps.printf(format, args);
      }
    }

    @Override
    public void printf(int color, String format, Object... args) {
      con.printf(this, color, format, args);
    }

  }

  public String prompt(String str) throws IOException {
    printf("%s: ", str);

    return readLine();
  }

  public void printf(String format, Object... args) {
    defChannel.printf(format, args);
  }

  public void printf(int c, String format, Object... args) {
    printf(defChannel, c, format, args);
  }

  public abstract void printf(IConsoleChannel channel, int c, String format, Object... args);

  public String readLine() throws IOException {
    return in.readLine();
  }

  public static void main(String[] args) {
    Console.get().printf(Color.green, "green\n");
    Console.get().printf("normal\n");
    Console.get().printf(Color.red, "red\n");
    Console.get().printf("normal\n");
  }

  public void println(String string) {
    System.out.println(string);
  }
}
