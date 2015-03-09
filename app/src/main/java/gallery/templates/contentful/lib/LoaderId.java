package gallery.templates.contentful.lib;

import java.util.HashMap;

/** Helper class to map classes to loader IDs. */
public class LoaderId {
  private static final HashMap<Class, Integer> loaders = new HashMap<>();

  /**
   * Returns a unique loader ID for the given {@code clazz}.
   * @param clazz class
   * @return loader ID
   */
  public synchronized static int forClass(Class<?> clazz) {
    Integer id = loaders.get(clazz);
    if (id == null) {
      id = loaders.size();
      loaders.put(clazz, id);
    }
    return id;
  }
}