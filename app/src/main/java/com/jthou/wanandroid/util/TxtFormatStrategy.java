//package com.jthou.wanandroid.util;
//
//import android.os.Environment;
//import android.os.Handler;
//import android.os.HandlerThread;
//import android.os.StatFs;
//import android.text.TextUtils;
//
//import com.orhanobut.logger.DiskLogStrategy;
//import com.orhanobut.logger.FormatStrategy;
//import com.orhanobut.logger.LogStrategy;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//import static android.os.Build.VERSION.SDK_INT;
//import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;
//import static com.orhanobut.logger.Logger.ASSERT;
//import static com.orhanobut.logger.Logger.DEBUG;
//import static com.orhanobut.logger.Logger.ERROR;
//import static com.orhanobut.logger.Logger.INFO;
//import static com.orhanobut.logger.Logger.VERBOSE;
//import static com.orhanobut.logger.Logger.WARN;
//
///**
// * CSV formatted file logging for Android.
// * Writes to CSV the following data:
// * epoch timestamp, ISO8601 timestamp (human-readable), log level, tag, log message.
// * @author quchao
// */
//
//public class TxtFormatStrategy implements FormatStrategy {
//
//  private static final String NEW_LINE = System.getProperty("line.separator");
//  private static final String SEPARATOR = " ";
//
//  private final Date date;
//  private final SimpleDateFormat dateFormat;
//  private final LogStrategy logStrategy;
//  private final String tag;
//
//  private TxtFormatStrategy(Builder builder) {
//    date = builder.date;
//    dateFormat = builder.dateFormat;
//    logStrategy = builder.logStrategy;
//    tag = builder.tag;
//  }
//
//  public static Builder newBuilder() {
//    return new Builder();
//  }
//
//  @Override
//  public void log(int priority, String onceOnlyTag, String message) {
//    String tag = formatTag(onceOnlyTag);
//
//    date.setTime(System.currentTimeMillis());
//    StringBuilder header = new StringBuilder();
//    // machine-readable date/time
//    header.append(Long.toString(date.getTime()));
//    // human-readable date/time
//    header.append(SEPARATOR);
//    header.append(dateFormat.format(date));
//    // level
//    header.append(SEPARATOR);
//    header.append(logLevel(priority));
//    // tag
//    header.append(SEPARATOR);
//    header.append(tag);
//    header.append(SEPARATOR);
//
//    StringBuilder builder = new StringBuilder();
//    builder.append(header);
//
//    // message
//    if (message.contains(NEW_LINE)) {
//      message = message.replaceAll(NEW_LINE, NEW_LINE + header.toString());
//    }
//    builder.append(message);
//    // new line
//    builder.append(NEW_LINE);
//
//    logStrategy.log(priority, tag, builder.toString());
//  }
//
//  private String formatTag(String tag) {
//    if (!TextUtils.isEmpty(tag) && !CommonUtils.isEquals(this.tag, tag)) {
//      return this.tag + "-" + tag;
//    }
//    return this.tag;
//  }
//
//  public static final class Builder {
//    Date date;
//    SimpleDateFormat dateFormat;
//    LogStrategy logStrategy;
//    String tag = "PRETTY_LOGGER";
//
//    private Builder() {
//    }
//
//    public Builder date(Date val) {
//      date = val;
//      return this;
//    }
//
//    public Builder dateFormat(SimpleDateFormat val) {
//      dateFormat = val;
//      return this;
//    }
//
//    public Builder logStrategy(LogStrategy val) {
//      logStrategy = val;
//      return this;
//    }
//
//    public Builder tag(String tag) {
//      this.tag = tag;
//      return this;
//    }
//
//    public TxtFormatStrategy build(String pkgName, String appName) {
//      if (date == null) {
//        date = new Date();
//      }
//      if (dateFormat == null) {
//        dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.UK);
//      }
//      if (logStrategy == null) {
//        String diskPath = Environment.getExternalStorageDirectory().getAbsolutePath();
//        String folder = diskPath + File.separatorChar + "Android" + File.separatorChar + "data" + File.separatorChar + pkgName + File.separatorChar + "log" + File.separatorChar;
//
//        HandlerThread ht = new HandlerThread("AndroidFileLogger." + folder);
//        ht.start();
//        Handler handler = new DiskLogStrategy.WriteHandler(ht.getLooper(), folder, calculateDiskCacheSize(new File(folder)));
//        logStrategy = new DiskLogStrategy(handler);
//      }
//      return new TxtFormatStrategy(this);
//    }
//
//    private long calculateDiskCacheSize(File dir) {
//      long size = MIN_DISK_CACHE_SIZE;
//
//      try {
//        StatFs statFs = new StatFs(dir.getAbsolutePath());
//        //noinspection deprecation
//        long blockCount =
//                SDK_INT < JELLY_BEAN_MR2 ? (long) statFs.getBlockCount() : statFs.getBlockCountLong();
//        //noinspection deprecation
//        long blockSize =
//                SDK_INT < JELLY_BEAN_MR2 ? (long) statFs.getBlockSize() : statFs.getBlockSizeLong();
//        long available = blockCount * blockSize;
//        // Target 2% of the total space.
//        size = available / 50;
//      } catch (IllegalArgumentException ignored) {
//      }
//
//      // Bound inside min/max size for disk cache.
//      return Math.max(Math.min(size, MAX_DISK_CACHE_SIZE), MIN_DISK_CACHE_SIZE);
//    }
//  }
//
//  private static final int MIN_DISK_CACHE_SIZE = 5 * 1024 * 1024; // 5MB
//  private static final int MAX_DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB
//
//
//
//  private String logLevel(int value) {
//    switch (value) {
//      case VERBOSE:
//        return "VERBOSE";
//      case DEBUG:
//        return "DEBUG";
//      case INFO:
//        return "INFO";
//      case WARN:
//        return "WARN";
//      case ERROR:
//        return "ERROR";
//      case ASSERT:
//        return "ASSERT";
//      default:
//        return "UNKNOWN";
//    }
//  }
//
//}
