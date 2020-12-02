package onim.en.area_api.util;

import java.util.HashSet;
import java.util.Set;

public class JavaUtils {

  public static Set<Class<?>> getInterfaces(Class<?> c) {
    return InterfaceExplorer.explore(c);
  }

}

class InterfaceExplorer {

  public static Set<Class<?>> explore(Class<?> c) {
    return explore(c, new HashSet<>());
  }

  private static Set<Class<?>> explore(Class<?> c, Set<Class<?>> interfaces) {
    for (Class<?> i : c.getInterfaces()) {
      if (interfaces.contains(i)) {
        continue;
      }

      interfaces.add(i);
      interfaces = explore(i, interfaces);
    }

    Class<?> s = c.getSuperclass();
    if (s != null) {
      interfaces = explore(s, interfaces);
    }

    return interfaces;
  }
}