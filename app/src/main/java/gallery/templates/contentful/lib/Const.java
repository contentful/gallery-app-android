package gallery.templates.contentful.lib;

import android.os.Build;
import gallery.templates.contentful.App;
import gallery.templates.contentful.R;

public class Const {
  private Const() {
    throw new AssertionError();
  }

  public static final String SPACE_ID = "fbr4i5aajb0w";

  public static final String ACCESS_TOKEN =
      "bb7868adae619627d086f309f987e7765da1fc75336dea1f06c01dc959fc19a3";

  public static final String CONTENT_TYPE_AUTHOR = "38nK0gXXIccQ2IEosyAg6C";

  public static final String CONTENT_TYPE_GALLERY = "7leLzv8hW06amGmke86y8G";

  public static final String CONTENT_TYPE_IMAGE = "1xYw5JsIecuGE68mmGMg20";

  // CDN Asset
  public static final Integer CDN_ASSET_SIZE =
      App.get().getResources().getInteger(R.integer.cdn_asset_size);

  public static final boolean HAS_L = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;

  public static Integer IMAGE_WIDTH;

  public static Integer IMAGE_HEIGHT;
}
