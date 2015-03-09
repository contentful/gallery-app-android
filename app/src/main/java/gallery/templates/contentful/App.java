package gallery.templates.contentful;

import android.app.Application;
import gallery.templates.contentful.sync.SyncService;

public class App extends Application {
  private static App sInstance;

  @Override public void onCreate() {
    super.onCreate();
    sInstance = this;
    SyncService.sync();
  }

  public static App get() {
    return sInstance;
  }
}
