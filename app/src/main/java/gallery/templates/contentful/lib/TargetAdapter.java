package gallery.templates.contentful.lib;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class TargetAdapter implements Target {
  @Override public void onPrepareLoad(Drawable placeHolderDrawable) {

  }

  @Override public void onBitmapFailed(Drawable errorDrawable) {

  }

  @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

  }
}
