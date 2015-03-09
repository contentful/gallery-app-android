package gallery.templates.contentful.lib;

import gallery.templates.contentful.App;

public class Intents {
  private Intents() {
    throw new AssertionError();
  }

  private static final String PACKAGE_NAME = App.get().getPackageName();

  // Actions
  public static final String ACTION_SYNC = PACKAGE_NAME + ".ACTION_SYNC";
  public static final String ACTION_CHANGE_SPACE = PACKAGE_NAME + ".ACTION_CHANGE_SPACE";
  public static final String ACTION_SHOW_ERROR = PACKAGE_NAME + ".ACTION_SHOW_ERROR";
  public static final String ACTION_RELOAD = PACKAGE_NAME + ".ACTION_RELOAD";
  public static final String ACTION_COLORIZE = PACKAGE_NAME + ".ACTION_COLORIZE";

  // Extras
  public static final String EXTRA_SPACE_ID = PACKAGE_NAME + ".EXTRA_SPACE_ID";
  public static final String EXTRA_ACCESS_TOKEN = PACKAGE_NAME + ".ACCESS_TOKEN";
  public static final String EXTRA_STATUS_CODE = PACKAGE_NAME + ".EXTRA_STATUS_CODE";
  public static final String EXTRA_GALLERY = PACKAGE_NAME + ".EXTRA_GALLERY";
  public static final String EXTRA_IMAGE = PACKAGE_NAME + ".EXTRA_IMAGE";
  public static final String EXTRA_CLR_LIGHT_MUTED = PACKAGE_NAME + ".EXTRA_CLR_LIGHT_MUTED";
  public static final String EXTRA_CLR_DARK_MUTED = PACKAGE_NAME + ".EXTRA_CLR_DARK_MUTED";
  public static final String EXTRA_CLR_VIBRANT = PACKAGE_NAME + ".EXTRA_CLR_VIBRANT";
}
