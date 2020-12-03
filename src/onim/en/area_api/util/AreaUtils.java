package onim.en.area_api.util;

import java.util.Random;

import onim.en.area_api.area.AreaManager;
import onim.en.area_api.area.AreaModel;

public class AreaUtils {

  private static final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

  public static String createAreaId() {
    StringBuilder builder = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < 16; i++) {
      random.setSeed(System.nanoTime() + i);
      builder.append(characters.charAt(random.nextInt(characters.length())));
    }
    return regenerateIfAlreadyExists(builder.toString());
  }

  public static String regenerateIfAlreadyExists(String id) {
    AreaModel area = AreaManager.getArea(id);
    if (area != null) {
      return createAreaId();
    }
    return id;
  }
}
