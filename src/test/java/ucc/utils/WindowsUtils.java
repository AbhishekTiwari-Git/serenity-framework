package ucc.utils;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Secur32;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.W32APIOptions;

public class WindowsUtils {

	   private WindowsUtils() {}

	   static final Secur32 secur32 =
	         (Secur32) Native.loadLibrary("secur32", Secur32.class, W32APIOptions.DEFAULT_OPTIONS);

	   public static String getCurrentUserName() {
	      char[] userNameBuf = new char[10000];
	      IntByReference size = new IntByReference(userNameBuf.length);
	      boolean result = secur32.GetUserNameEx(Secur32.EXTENDED_NAME_FORMAT.NameSamCompatible, userNameBuf, size);

	      if (!result)
	          throw new IllegalStateException("Cannot retreive name of the currently logged-in user");

	      return new String(userNameBuf, 0, size.getValue());
	  }

	  
	}