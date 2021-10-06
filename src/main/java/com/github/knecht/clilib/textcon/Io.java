package com.github.knecht.clilib.textcon;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicLong;

public class Io {

  public static String toString(File f) throws FileNotFoundException, IOException {
    return toString(new FileInputStream(f));
  }

  public static String toString(InputStream fin) throws FileNotFoundException, IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    copy(fin, baos, null);
    fin.close();
    return new String(baos.toByteArray());
  }

  public static long copy(InputStream in, OutputStream out, AtomicLong counter) throws IOException {
    byte[] buf = new byte[8192];
    long count = 0;
    for (;;) {
      int n = in.read(buf);
      if (n < 1)
        break;
      out.write(buf, 0, n);
      count += n;
      if (null != counter)
        counter.addAndGet(n);
    }
    return count;
  }

  public static void download(String string, File target, AtomicLong counter)
      throws MalformedURLException, IOException {
    InputStream in = new URL(string).openStream();
    FileOutputStream fo = new FileOutputStream(target);
    copy(in, fo, counter);
    fo.close();
  }

  public static void smartDownload(String string, File lib) throws MalformedURLException, IOException {
    File target = new File(lib, string.replaceAll(".*/", ""));
    if (!target.exists() || target.length() == 0)
      download(string, target, null);
  }

}
