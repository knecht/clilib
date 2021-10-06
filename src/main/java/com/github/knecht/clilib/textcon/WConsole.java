package com.github.knecht.clilib.textcon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WConsole extends Console {

  public static final int STD_INPUT_HANDLE = -10;

  public static final int STD_OUTPUT_HANDLE = -11;

  public static final int STD_ERROR_HANDLE = -12;

  public static final short CONSOLE_FOREGROUND_COLOR_BLACK = 0x00;

  public static final short CONSOLE_FOREGROUND_COLOR_BLUE = 0x01;

  public static final short CONSOLE_FOREGROUND_COLOR_GREEN = 0x02;

  public static final short CONSOLE_FOREGROUND_COLOR_AQUA = 0x03;

  public static final short CONSOLE_FOREGROUND_COLOR_RED = 0x04;

  public static final short CONSOLE_FOREGROUND_COLOR_PURPLE = 0x05;

  public static final short CONSOLE_FOREGROUND_COLOR_YELLOW = 0x06;

  public static final short CONSOLE_FOREGROUND_COLOR_WHITE = 0x07;

  public static final short CONSOLE_FOREGROUND_COLOR_GRAY = 0x08;

  public static final short CONSOLE_FOREGROUND_COLOR_LIGHT_BLUE = 0x09;

  public static final short CONSOLE_FOREGROUND_COLOR_LIGHT_GREEN = 0x0A;

  public static final short CONSOLE_FOREGROUND_COLOR_LIGHT_AQUA = 0x0B;

  public static final short CONSOLE_FOREGROUND_COLOR_LIGHT_RED = 0x0C;

  public static final short CONSOLE_FOREGROUND_COLOR_LIGHT_PURPLE = 0x0D;

  public static final short CONSOLE_FOREGROUND_COLOR_LIGHT_YELLOW = 0x0E;

  public static final short CONSOLE_FOREGROUND_COLOR_BRIGHT_WHITE = 0x0F;

  public static final short CONSOLE_BACKGROUND_COLOR_BLACK = 0x00;

  public static final short CONSOLE_BACKGROUND_COLOR_BLUE = 0x10;

  public static final short CONSOLE_BACKGROUND_COLOR_GREEN = 0x20;

  public static final short CONSOLE_BACKGROUND_COLOR_AQUA = 0x30;

  public static final short CONSOLE_BACKGROUND_COLOR_RED = 0x40;

  public static final short CONSOLE_BACKGROUND_COLOR_PURPLE = 0x50;

  public static final short CONSOLE_BACKGROUND_COLOR_YELLOW = 0x60;

  public static final short CONSOLE_BACKGROUND_COLOR_WHITE = 0x70;

  public static final short CONSOLE_BACKGROUND_COLOR_GRAY = 0x80;

  public static final short CONSOLE_BACKGROUND_COLOR_LIGHT_BLUE = 0x90;

  public static final short CONSOLE_BACKGROUND_COLOR_LIGHT_GREEN = 0xA0;

  public static final short CONSOLE_BACKGROUND_COLOR_LIGHT_AQUA = 0xB0;

  public static final short CONSOLE_BACKGROUND_COLOR_LIGHT_RED = 0xC0;

  public static final short CONSOLE_BACKGROUND_COLOR_LIGHT_PURPLE = 0xD0;

  public static final short CONSOLE_BACKGROUND_COLOR_LIGHT_YELLOW = 0xE0;

  public static final short CONSOLE_BACKGROUND_COLOR_BRIGHT_WHITE = 0xF0;

  public WConsole() throws IOException {
    File tmp = File.createTempFile("wcon", ".dll");
    FileOutputStream fout = new FileOutputStream(tmp);
    Io.copy(
        getClass().getResourceAsStream("wcon" + (System.getProperty("os.arch").contains("64") ? "64" : "32") + ".dll"),
        fout, null);
    fout.close();
    System.load(tmp.getAbsolutePath());
  }

  public void printf(IConsoleChannel chan, int c, String format, Object... args) {
    Integer last = setColor(c);
    chan.printf(format, args);
    setColor(last);
  }

  private static final int bit(int c, int bit, int val) {
    return ((c & bit) != 0) ? val : 0;
  }

  private static final int bitselect(int base, int... bits) {
    int result = 0;
    for (int i = 0; i < bits.length; i++) {
      result <<= 1;
      if ((bits[i] & base) != 0)
        result |= 1;
    }
    return result;
  }

  public Integer setColor(Integer c) {
    if (null == c)
      c = Color.gray;
    Integer old = null;
    try {
      int wc = bitselect(c, Color.red, Color.green, Color.blue);
      if ((c & Color.dark) == 0)
        wc += 8;
      setColor((short) wc);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return old;
  }

  private static native long getColor();

  private static native void setColor(short l);
}
