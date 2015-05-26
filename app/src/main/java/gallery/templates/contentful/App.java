package gallery.templates.contentful;

import android.app.Application;
import com.contentful.vault.SyncConfig;
import com.contentful.vault.Vault;
import gallery.templates.contentful.lib.ClientProvider;
import gallery.templates.contentful.vault.GallerySpace;

public class App extends Application {
  private static App instance;

  @Override public void onCreate() {
    super.onCreate();
    instance = this;
    requestSync();
  }

  public static App get() {
    return instance;
  }

  public static void requestSync() {
    requestSync(false);
  }

  public static void requestSync(boolean invalidate) {
    Vault.with(get(), GallerySpace.class).requestSync(
        SyncConfig.builder()
            .setClient(ClientProvider.get())
            .setInvalidate(invalidate)
            .build());
  }
}