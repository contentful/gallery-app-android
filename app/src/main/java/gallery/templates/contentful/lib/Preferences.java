package gallery.templates.contentful.lib;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import gallery.templates.contentful.App;

public class Preferences {
  public static final String KEY_SPACE_ID = "SPACE_ID";

  public static final String KEY_ACCESS_TOKEN = "ACCESS_TOKEN";

  private Preferences() {
    throw new AssertionError();
  }

  public static SharedPreferences get() {
    return PreferenceManager.getDefaultSharedPreferences(App.get());
  }

  public static String getSpaceId() {
    return get().getString(KEY_SPACE_ID, null);
  }

  public static String getAccessToken() {
    return get().getString(KEY_ACCESS_TOKEN, null);
  }
}