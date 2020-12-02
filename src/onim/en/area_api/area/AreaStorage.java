package onim.en.area_api.area;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Stream;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import onim.en.area_api.AreaAPI;
import onim.en.area_api.area.model.CircleArea;
import onim.en.area_api.area.model.PolygonArea;
import onim.en.area_api.area.model.RectangleArea;
import onim.en.area_api.util.JavaExecutor;

public class AreaStorage {

  private static final HashMap<String, AreaModel> changedAreas = Maps.newHashMap();
  private static final HashMap<Class<? extends AreaModel>, Set<AreaModel>> removedAreas = Maps.newHashMap();

  public static void changed(AreaModel area) {
    changedAreas.put(area.getAreaId(), area);
  }

  public static void removed(AreaModel area) {
    Class<? extends AreaModel> c = area.getClass();

    removedAreas.compute(c, (k, v) -> {
      if (v == null)
        v = Sets.newHashSet();
      v.add(area);
      return v;
    });
  }

  public static void loadAreas() {
    Gson gson = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .create();

    try {
      File rectangle = AreaStorage.getOrCreate(AreaAPI.dataDir, "rectangle");
      File circle = AreaStorage.getOrCreate(AreaAPI.dataDir, "circle");
      File polygon = AreaStorage.getOrCreate(AreaAPI.dataDir, "polygon");

      parseAndRegister(gson, rectangle, RectangleArea.class);
      parseAndRegister(gson, circle, CircleArea.class);
      parseAndRegister(gson, polygon, PolygonArea.class);
    } catch (FileAlreadyExistsException e) {
      e.printStackTrace();
    }

  }

  public static void saveAreas() {
    Gson gson = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .setPrettyPrinting()
        .create();

    try {
      File rectangle = AreaStorage.getOrCreate(AreaAPI.dataDir, "rectangle");
      File circle = AreaStorage.getOrCreate(AreaAPI.dataDir, "circle");
      File polygon = AreaStorage.getOrCreate(AreaAPI.dataDir, "polygon");

      serializeAndWrite(gson, rectangle, RectangleArea.class);
      serializeAndWrite(gson, circle, CircleArea.class);
      serializeAndWrite(gson, polygon, PolygonArea.class);

      changedAreas.clear();

      removeUnnecessaryFiles(rectangle, RectangleArea.class);
      removeUnnecessaryFiles(circle, CircleArea.class);
      removeUnnecessaryFiles(polygon, PolygonArea.class);
    } catch (FileAlreadyExistsException e) {
      e.printStackTrace();
    }
  }

  private static void parseAndRegister(Gson gson, File dir, Class<? extends AreaModel> c) {
    Stream.of(dir.listFiles()).forEach(f -> {
      JavaExecutor.excuteThrowable(() -> {
        FileReader reader = new FileReader(f);
        AreaModel parsed = gson.fromJson(reader, c);
        reader.close();
        AreaManager.register(parsed);
      });
    });
  }

  private static void serializeAndWrite(Gson gson, File dir, Class<? extends AreaModel> c) {
    AreaManager.getAreaForInterface(c).stream()
        .filter(a -> changedAreas.containsKey(a.getAreaId()))
        .forEach(a -> {
          JavaExecutor.excuteThrowable(() -> {
            String json = gson.toJson(a);
            FileWriter writer = new FileWriter(new File(dir, a.getAreaId() + ".json"));
            writer.append(json);
            writer.close();
          });
        });
  }

  private static void removeUnnecessaryFiles(File dir, Class<? extends AreaModel> c) {
    Set<AreaModel> set = removedAreas.get(c);
    if (set == null)
      return;

    set.forEach(a -> {
      File file = new File(dir, a.getAreaId() + ".json");
      file.delete();
    });

    removedAreas.put(c, Sets.newHashSet());
  }

  /**
   * Create directory if it doesn't exist. or throws when the file has represeted File.
   *
   * @param parent
   * @param child
   * @return
   * @throws FileAlreadyExistsException
   */
  private static File getOrCreate(File parent, String child) throws FileAlreadyExistsException {
    File file = new File(parent, child);
    if (!file.exists())
      file.mkdirs();

    if (!file.isDirectory())
      throw new FileAlreadyExistsException(child, null, "The file has already exists. and is not a directory!!");

    return file;
  }

}
