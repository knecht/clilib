package com.github.knecht.clilib;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.knecht.clilib.textcon.Console;

public class CliTool {

  protected Console con = Console.get();

  private Map<String, CliTool> subs = new HashMap();

  private Map<String, Field> flags = new HashMap();

  private Map<String, Method> commands = new HashMap();

  protected CliTool() {
    try {
      for (Field f : getClass().getDeclaredFields()) {
        SubTool st = f.getAnnotation(SubTool.class);
        if (null != st) {
          subs.put(st.value(), (CliTool) f.getType().newInstance());
        }
        Flag flag = f.getAnnotation(Flag.class);
        if (null != flag) {
          for (String s : flag.value()) {
            if (s.length() == 1) {
              flags.put("-" + s, f);
            } else {
              flags.put("--" + s, f);
            }
          }
        }
      }
      for (Method mth : getClass().getDeclaredMethods()) {
        Command cmd = mth.getAnnotation(Command.class);
        if (null != cmd) {
          commands.put(cmd.value(), mth);
        }
      }
    } catch (Throwable t) {
      throw new RuntimeException(t);
    }
  }

  public void run(String[] args) {
    try {
      List<String> list = new ArrayList(Arrays.asList(args));
      while (!list.isEmpty()) {
        String s = list.remove(0);
        if (s.startsWith("-")) {
          Field f = flags.get(s);
          if (null != f)
            f.set(this, Boolean.TRUE);
        } else {
          CliTool sub = subs.get(s);
          if (null != sub) {
            sub.run(list.toArray(new String[list.size()]));
            System.exit(0);
          }
          Method mth = commands.get(s);
          if (null != mth) {
            mth.invoke(this, new Object[] {list.toArray(new String[list.size()])});
            System.exit(0);
          }
        }
      }
      usage();
    } catch (Throwable t) {
      throw new RuntimeException(t);
    }
  }

  public void usage() {

  }
}
