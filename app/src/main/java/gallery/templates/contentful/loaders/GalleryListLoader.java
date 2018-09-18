package gallery.templates.contentful.loaders;

import android.content.Context;

import com.contentful.vault.Vault;

import java.util.List;

import gallery.templates.contentful.vault.Gallery;
import gallery.templates.contentful.vault.GallerySpace;

public class GalleryListLoader extends AbsLoader<List<Gallery>> {

  Context context;

  public GalleryListLoader(Context context) {
    this.context = context;
  }

  @Override protected List<Gallery> performLoad() {
    return Vault.with(context, GallerySpace.class).fetch(Gallery.class).all();
  }
}
