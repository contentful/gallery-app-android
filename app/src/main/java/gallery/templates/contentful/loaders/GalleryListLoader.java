package gallery.templates.contentful.loaders;

import com.contentful.vault.Vault;
import gallery.templates.contentful.vault.Gallery;
import gallery.templates.contentful.vault.GallerySpace;
import java.util.List;

public class GalleryListLoader extends AbsLoader<List<Gallery>> {
  @Override protected List<Gallery> performLoad() {
    return Vault.with(getContext(), GallerySpace.class).fetch(Gallery.class).all();
  }
}
