package gallery.templates.contentful.lib;

import gallery.templates.contentful.App;

public class Intents {
  private Intents() {
    throw new AssertionError();
  }

  private static final String PACKAGE_NAME = App.get().getPackageName();

  public static final String ACTION_RELOAD = PACKAGE_NAME + ".ACTION_RELOAD";

  public static final String ACTION_COLORIZE = PACKAGE_NAME + ".ACTION_COLORIZE";

  public static final String EXTRA_GALLERY = PACKAGE_NAME + ".EXTRA_GALLERY";

  public static final String EXTRA_IMAGE = PACKAGE_NAME + ".EXTRA_IMAGE";

  public static final String EXTRA_CLR_LIGHT_MUTED = PACKAGE_NAME + ".EXTRA_CLR_LIGHT_MUTED";

  public static final String EXTRA_CLR_DARK_MUTED = PACKAGE_NAME + ".EXTRA_CLR_DARK_MUTED";

  public static final String EXTRA_CLR_VIBRANT = PACKAGE_NAME + ".EXTRA_CLR_VIBRANT";
}
