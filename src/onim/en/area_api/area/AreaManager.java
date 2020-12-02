package onim.en.area_api.area;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Maps;

public class AreaManager {

  private static HashMap<String, AreaModel> idToAreas = Maps.newHashMap();
  private static HashMap<String, AreaModel> nameToAreas = Maps.newHashMap();
  private static HashMap<Class<?>, Set<AreaModel>> classToAreaMap = Maps.newHashMap();

  public static void register(AreaModel area) {
    classToAreaMap.compute(area.getClass(), (k, v) -> {
      if (v == null)
        v = new HashSet<>();
      v.add(area);
      return v;
    });
    idToAreas.put(area.getAreaId(), area);
    nameToAreas.put(area.getAreaName(), area);

    AreaStorage.changed(area);
  }

  public static void unregister(AreaModel area) {
    classToAreaMap.compute(area.getClass(), (k, v) -> {
      if (v != null)
        v.remove(area);
      return v;
    });

    idToAreas.remove(area.getAreaId());
    nameToAreas.remove(area.getAreaName());

    AreaStorage.removed(area);
  }

  @Nullable
  public static AreaModel getArea(String areaId) {
    return idToAreas.get(areaId);
  }

  @Nullable
  public static AreaModel getAreaByName(String areaName) {
    return nameToAreas.get(areaName);
  }

  @Nonnull
  public static Collection<AreaModel> getAreaForInterface(Class<?> i) {
    return classToAreaMap.containsKey(i) ? classToAreaMap.get(i) : new ArrayList<>();
  }

  @Nonnull
  public static Collection<AreaModel> getAllAreas() {
    return idToAreas.values();
  }

}
