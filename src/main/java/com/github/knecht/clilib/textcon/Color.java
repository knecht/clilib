package com.github.knecht.clilib.textcon;

public class Color {

  public static int bold = 0x10;

  public static int dark = 0x20;

  public static int underline = 0x40;

  public static int red = 0x01;

  public static int green = 0x02;

  public static int blue = 0x04;

  public static int yellow = red | green;

  public static int magenta = red | blue;

  public static int cyan = green | blue;

  public static int white = red | green | blue;

  public static int gray = dark | white;

}
