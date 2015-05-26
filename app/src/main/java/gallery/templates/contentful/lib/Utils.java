package gallery.templates.contentful.lib;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;

public class Utils {
  private Utils() {
    throw new AssertionError();
  }

  public static String imageUrl(String url) {
    int size = Const.CDN_ASSET_SIZE;
    return imageUrl(url, size, size);
  }

  public static String imageUrl(String url, int width, int height) {
    String prefix = "?";

    if (url.contains("?")) {
      prefix = "&";
    }

    return url + prefix + "w=" + width + "&h=" + height;
  }

  public static boolean isPortrait(Context context) {
    return context.getResources().getConfiguration().orientation ==
        Configuration.ORIENTATION_PORTRAIT;
  }

  public static Point getDisplayDimensions(Activity activity) {
    Point point = new Point();
    activity.getWindowManager().getDefaultDisplay().getSize(point);
    return point;
  }
}
