package gallery.templates.contentful.sync;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import gallery.templates.contentful.App;
import gallery.templates.contentful.activities.MainActivity;
import gallery.templates.contentful.lib.ClientProvider;
import gallery.templates.contentful.lib.Intents;
import gallery.templates.contentful.lib.Preferences;
import gallery.templates.contentful.realm.RealmAuthor;
import gallery.templates.contentful.realm.RealmGallery;
import gallery.templates.contentful.realm.RealmImage;
import io.realm.Realm;
import retrofit.RetrofitError;

public class SyncService extends IntentService {
  public static void sync() {
    Context context = App.get();
    context.startService(new Intent(context, SyncService.class).setAction(Intents.ACTION_SYNC));
  }

  public static void changeSpace(String spaceId, String accessToken) {
    Context context = App.get();
    context.startService(new Intent(context, SyncService.class)
        .setAction(Intents.ACTION_CHANGE_SPACE)
        .putExtra(Intents.EXTRA_SPACE_ID, spaceId)
        .putExtra(Intents.EXTRA_ACCESS_TOKEN, accessToken));
  }

  public SyncService() {
    super(SyncService.class.getName());
  }

  @Override protected void onHandleIntent(Intent intent) {
    String action = intent.getAction();

    if (Intents.ACTION_SYNC.equals(action)) {
      actionSync();
    } else if (Intents.ACTION_CHANGE_SPACE.equals(action)) {
      actionChangeSpace(intent);
    }
  }

  private void actionSync() {
    try {
      SyncHelper.performSync();
    } catch (RetrofitError e) {
      e.printStackTrace();
      sendErrorBroadcast(e);
    } finally {
      sendReloadBroadcast();
    }
  }

  private void actionChangeSpace(Intent intent) {
    String spaceId = intent.getStringExtra(Intents.EXTRA_SPACE_ID);
    String token = intent.getStringExtra(Intents.EXTRA_ACCESS_TOKEN);

    clearDb();
    setCredentials(spaceId, token);
    SyncService.sync();
    startActivity(new Intent(getApplicationContext(), MainActivity.class)
        .setAction(intent.getAction())
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
  }

  private void clearDb() {
    Realm realm = Realm.getInstance(this);

    try {
      realm.beginTransaction();
      realm.allObjects(RealmAuthor.class).clear();
      realm.allObjects(RealmGallery.class).clear();
      realm.allObjects(RealmImage.class).clear();
      realm.commitTransaction();
    } finally {
      realm.close();
    }
  }

  private void setCredentials(String spaceId, String token) {
    Preferences.get().edit()
        .remove(Preferences.KEY_SYNC_TOKEN)
        .putString(Preferences.KEY_SPACE_ID, spaceId)
        .putString(Preferences.KEY_ACCESS_TOKEN, token)
        .commit();

    ClientProvider.reset();
  }

  private void sendErrorBroadcast(RetrofitError e) {
    Context context = App.get();
    Integer statusCode = null;

    if (RetrofitError.Kind.HTTP.equals(e.getKind())) {
      statusCode = e.getResponse().getStatus();
    }

    context.sendBroadcast(new Intent(Intents.ACTION_SHOW_ERROR)
        .putExtra(Intents.EXTRA_STATUS_CODE, statusCode));
  }

  private void sendReloadBroadcast() {
    Context context = App.get();
    context.sendBroadcast(new Intent(Intents.ACTION_RELOAD), null);
  }
}
