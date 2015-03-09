package gallery.templates.contentful.loaders;

import gallery.templates.contentful.dto.Gallery;
import gallery.templates.contentful.lib.RealmConverter;
import gallery.templates.contentful.realm.RealmGallery;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;

public class GalleryListLoader extends AbsLoader<List<Gallery>> {
  @Override protected List<Gallery> performLoad() {
    Realm realm = Realm.getInstance(getContext());
    final List<Gallery> result;

    try {
      RealmResults<RealmGallery> galleries = realm.where(RealmGallery.class).findAll();
      result = new ArrayList<>();
      for (RealmGallery gallery : galleries) {
        result.add(RealmConverter.gallery(gallery));
      }
    } finally {
      realm.close();
    }

    return result;
  }
}
