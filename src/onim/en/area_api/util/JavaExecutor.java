package onim.en.area_api.util;

public class JavaExecutor {

  public interface TryThrowable {
    public void tryThrowable() throws Exception;
  }

  public static void excuteThrowable(TryThrowable t) {
    try {
      t.tryThrowable();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
