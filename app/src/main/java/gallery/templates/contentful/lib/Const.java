package gallery.templates.contentful.lib;

import android.os.Build;
import gallery.templates.contentful.App;
import gallery.templates.contentful.R;

public class Const {
  private Const() {
    throw new AssertionError();
  }

  // Content Type IDs
  public static final String CONTENT_TYPE_AUTHOR =
      App.get().getString(R.string.content_type_author);

  public static final String CONTENT_TYPE_GALLERY =
      App.get().getString(R.string.content_type_gallery);

  public static final String CONTENT_TYPE_IMAGE =
      App.get().getString(R.string.content_type_image);

  // CDN Asset
  public static final Integer CDN_ASSET_SIZE =
      App.get().getResources().getInteger(R.integer.cdn_asset_size);

  public static final boolean HAS_L = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;

  public static Integer IMAGE_WIDTH;
  public static Integer IMAGE_HEIGHT;
}
