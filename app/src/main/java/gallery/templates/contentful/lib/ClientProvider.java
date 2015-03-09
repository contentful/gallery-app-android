package gallery.templates.contentful.lib;

import android.content.Context;
import com.contentful.java.cda.CDAClient;
import gallery.templates.contentful.App;
import gallery.templates.contentful.R;

/** Thread-safe {@link CDAClient} provider. */
public class ClientProvider {
  private ClientProvider() {
    throw new AssertionError();
  }

  private static CDAClient sInstance;
  private static final Object LOCK = new Object();

  /**
   * Return a singleton {@link CDAClient} instance.
   */
  @SuppressWarnings("ConstantConditions")
  public static CDAClient get() {
    synchronized (LOCK) {
      if (sInstance == null) {
        Context context = App.get();

        // Extract credentials from SharedPreferences
        String spaceId = Preferences.getSpaceId();
        String accessToken = Preferences.getAccessToken();

        if (spaceId == null || accessToken == null) {
          // Fallback to demo space credentials
          spaceId = context.getString(R.string.api_space_id);
          accessToken = context.getString(R.string.api_access_token);

          if (spaceId == null || accessToken == null) {
            throw new IllegalStateException("Credentials must be specified in config.xml!");
          }
        }

        sInstance = new CDAClient.Builder().setSpaceKey(spaceId)
            .setAccessToken(accessToken)
            .nullifyUnresolvedLinks()
            .build();
      }

      return sInstance;
    }
  }

  /**
   * Resets the singleton.
   */
  public static void reset() {
    synchronized (LOCK) {
      sInstance = null;
    }
  }
}
