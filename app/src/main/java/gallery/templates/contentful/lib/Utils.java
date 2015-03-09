package gallery.templates.contentful.lib;

import android.content.Context;
import android.content.res.Configuration;

public class Utils {
  private Utils() {
    throw new AssertionError();
  }

  public static String imageUrl(String url) {
    String size = Const.CDN_ASSET_SIZE.toString();
    return imageUrl(url, size, size);
  }

  public static String imageUrl(String url, String width, String height) {
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
}
