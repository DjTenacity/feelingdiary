package cn.dj.android.common.lib.util;

import cn.dj.android.common.lib.App;

/**
 * 统一日志管理
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2016/4/23.
 */
public class Log {

	public static void v(String tag, String msg) {
		log(android.util.Log.VERBOSE, tag, getClassNameAndLineNumber() + msg,null);
	}

	public static void v(String tag, String msg, Throwable tr) {
		log(android.util.Log.VERBOSE, tag, getClassNameAndLineNumber() + msg,tr);
	}

	public static void d(String tag, String msg) {
		log(android.util.Log.DEBUG, tag, getClassNameAndLineNumber() + msg,null);
	}

	public static void d(String tag, String msg, Throwable tr) {
		log(android.util.Log.DEBUG, tag, getClassNameAndLineNumber() + msg, tr);
	}

	public static void i(String tag, String msg) {
		log(android.util.Log.INFO, tag, getClassNameAndLineNumber() + msg, null);
	}

	public static void i(String tag, String msg, Throwable tr) {
		log(android.util.Log.INFO, tag, getClassNameAndLineNumber() + msg, tr);
	}

	public static void w(String tag, String msg) {
		log(android.util.Log.WARN, tag, getClassNameAndLineNumber() + msg, null);
	}

	public static void w(String tag, String msg, Throwable tr) {
		log(android.util.Log.WARN, tag, getClassNameAndLineNumber() + msg, tr);
	}

	public static void e(String tag, String msg) {
		log(android.util.Log.ERROR, tag, getClassNameAndLineNumber() + msg,	null);
	}

	public static void e(String tag, String msg, Throwable tr) {
		log(android.util.Log.ERROR, tag, getClassNameAndLineNumber() + msg, tr);
	}
	
	/**
	 * 日志打印
	 * @Date  gaodianjie / Jul 30, 2014
	 * @param level
	 * @param tag
	 * @param msg
	 * @param e
	 */
	public static void log(int level, String tag, String msg, Throwable e) {
		if (App.logFlag) {
			switch (level) {
			case android.util.Log.VERBOSE:
				if (level >= App.DEFAULT_LEVEL) {
					android.util.Log.v(tag, msg, e);
				}
				break;
			case android.util.Log.DEBUG:
				if (level >= App.DEFAULT_LEVEL) {
					android.util.Log.d(tag, msg, e);
				}
				break;
			case android.util.Log.INFO:
				if (level >= App.DEFAULT_LEVEL) {
					android.util.Log.i(tag, msg, e);
				}
				break;
			case android.util.Log.WARN:
				if (level >= App.DEFAULT_LEVEL) {
					android.util.Log.w(tag, msg, e);
				}
				break;
			case android.util.Log.ERROR:
				if (level >= App.DEFAULT_LEVEL) {
					android.util.Log.e(tag, msg, e);
				}
				break;
			default:
				break;
			}
		}

	}

	/**
	 * @desc 打印调用函数名和行号
	 * @param
	 * @author mwgao/ 2014-7-24
	 */
	public static String getClassNameAndLineNumber() {
		StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
		String msg = "";
		if (stacks[4] != null) {
			String names[] = stacks[4].getClassName().split("\\.");
			String className = names[names.length - 1];
			msg = className + ":" + stacks[4].getLineNumber() + " 	 ";
		}
		return msg;
	}

}
