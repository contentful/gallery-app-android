package gallery.templates.contentful.lib;

import java.util.HashMap;
import java.util.Map;

public class LoaderId {
  private static final Map<Class, Integer> loaders = new HashMap<>();

  public synchronized static int forClass(Class<?> clazz) {
    Integer id = loaders.get(clazz);
    if (id == null) {
      id = loaders.size();
      loaders.put(clazz, id);
    }
    return id;
  }
}