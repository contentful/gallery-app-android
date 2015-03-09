package gallery.templates.contentful.sync;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.Constants;
import com.contentful.java.cda.model.CDAAsset;
import com.contentful.java.cda.model.CDAEntry;
import com.contentful.java.cda.model.CDAResource;
import com.contentful.java.cda.model.CDASyncedSpace;
import gallery.templates.contentful.App;
import gallery.templates.contentful.lib.ClientProvider;
import gallery.templates.contentful.lib.Const;
import gallery.templates.contentful.lib.Preferences;
import gallery.templates.contentful.realm.RealmAsset;
import gallery.templates.contentful.realm.RealmAuthor;
import gallery.templates.contentful.realm.RealmGallery;
import gallery.templates.contentful.realm.RealmImage;
import io.realm.Realm;
import io.realm.RealmObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SyncHelper {
  private SyncHelper() {
    throw new AssertionError();
  }

  public static void performSync() {
    CDASyncedSpace space = fetchSynchronizedSpace();

    ArrayList<CDAResource> items = space.getItems();
    if (items.size() > 0) {
      Realm realm = Realm.getInstance(App.get());

      try {
        realm.beginTransaction();

        for (CDAResource resource : items) {
          if (isDeleted(resource)) {
            delete(realm, resource);
          } else {
            save(realm, resource);
          }
        }

        realm.commitTransaction();
      } finally {
        realm.close();
      }
    }

    saveSyncToken(space.getSyncToken());
  }

  private static void delete(Realm realm, CDAResource resource) {
    Class<? extends RealmObject> clazz = classForResource(resource);
    if (clazz != null) {
      realm.where(clazz)
          .equalTo("remoteId", extractResourceId(resource))
          .findAll()
          .clear();
    }
  }

  private static void save(Realm realm, CDAResource resource) {
    Class<? extends RealmObject> clazz = classForResource(resource);
    if (clazz != null) {
      if (RealmAuthor.class.equals(clazz)) {
        saveAuthor(realm, (CDAEntry) resource);
      } else if (RealmAsset.class.equals(clazz)) {
        saveAsset(realm, (CDAAsset) resource);
      } else if (RealmGallery.class.equals(clazz)) {
        saveGallery(realm, (CDAEntry) resource);
      } else if (RealmImage.class.equals(clazz)) {
        saveImage(realm, (CDAEntry) resource);
      }
    }
  }

  private static RealmAsset saveAsset(Realm realm, CDAAsset asset) {
    String remoteId = extractResourceId(asset);
    RealmAsset realmAsset = getOrCreate(realm, RealmAsset.class, remoteId);
    realmAsset.setRemoteId(remoteId);
    realmAsset.setUrl(asset.getUrl());
    realmAsset.setMimeType(asset.getMimeType());
    return realmAsset;
  }

  private static RealmAuthor saveAuthor(Realm realm, CDAEntry entry) {
    String remoteId = extractResourceId(entry);
    RealmAuthor author = getOrCreate(realm, RealmAuthor.class, remoteId);

    Map fields = entry.getFields();
    author.setRemoteId(remoteId);
    author.setName(emptyIfNull((String) fields.get("name")));
    author.setTwitter(emptyIfNull((String) fields.get("twitterHandle")));
    author.setBiography(emptyIfNull((String) fields.get("biography")));
    setAuthorProfilePhoto(realm, author, fields);

    return author;
  }

  private static void setAuthorProfilePhoto(Realm realm, RealmAuthor author, Map fields) {
    CDAAsset profilePhoto = (CDAAsset) fields.get("profilePhoto");
    if (profilePhoto != null) {
      author.setProfilePhoto(saveAsset(realm, profilePhoto));
    }
  }

  private static RealmGallery saveGallery(final Realm realm, CDAEntry entry) {
    String remoteId = extractResourceId(entry);
    final RealmGallery realmGallery = getOrCreate(realm, RealmGallery.class, remoteId);

    Map fields = entry.getFields();
    realmGallery.setRemoteId(remoteId);
    realmGallery.setTitle(emptyIfNull((String) fields.get("title")));
    realmGallery.setSlug(emptyIfNull((String) fields.get("slug")));
    realmGallery.setDescription(emptyIfNull((String) fields.get("description")));
    realmGallery.setDate(emptyIfNull((String) fields.get("date")));

    setGalleryAuthor(realm, realmGallery, fields);
    setGalleryCoverImage(realm, realmGallery, fields);
    setGalleryImages(realm, realmGallery, entry);
    setGalleryTags(realmGallery, fields);

    return realmGallery;
  }

  private static void setGalleryTags(RealmGallery realmGallery, Map fields) {
    List tags = (List) fields.get("tags");
    if (tags != null) {
      realmGallery.setTags(TextUtils.join(",", tags));
    }
  }

  private static void setGalleryCoverImage(Realm realm, RealmGallery realmGallery, Map fields) {
    CDAAsset coverImage = (CDAAsset) fields.get("coverImage");
    if (coverImage != null) {
      realmGallery.setCoverImage(saveAsset(realm, coverImage));
    }
  }

  private static void setGalleryAuthor(Realm realm, RealmGallery realmGallery, Map fields) {
    CDAEntry author = (CDAEntry) fields.get("author");
    if (author != null) {
      realmGallery.setAuthor(saveAuthor(realm, author));
    }
  }

  private static void setGalleryImages(final Realm realm, final RealmGallery gallery,
      CDAEntry entry) {
    List images = (List) entry.getFields().get("images");
    if (images != null) {
      gallery.getImages().clear();
      for (Object o : images) {
        gallery.getImages().add(saveImage(realm, (CDAEntry) o));
      }
    }
  }

  private static RealmImage saveImage(Realm realm, CDAEntry entry) {
    String remoteId = extractResourceId(entry);
    RealmImage image = getOrCreate(realm, RealmImage.class, remoteId);

    Map fields = entry.getFields();
    image.setRemoteId(remoteId);
    image.setTitle(emptyIfNull((String) fields.get("title")));
    image.setImageCaption(emptyIfNull((String) fields.get("imageCaption")));
    image.setImageCredits(emptyIfNull((String) fields.get("imageCredits")));
    setImagePhoto(realm, image, fields);

    return image;
  }

  private static void setImagePhoto(Realm realm, RealmImage image, Map fields) {
    CDAAsset photo = (CDAAsset) fields.get("photo");
    if (photo != null) {
      image.setPhoto(saveAsset(realm, photo));
    }
  }

  @Nullable private static Class<? extends RealmObject> classForResource(CDAResource resource) {
    if (resource instanceof CDAAsset) {
      return RealmAsset.class;
    } else if (resource instanceof CDAEntry) {
      String contentTypeId = extractResourceContentType((CDAEntry) resource);

      if (Const.CONTENT_TYPE_AUTHOR.equals(contentTypeId)) {
        return RealmAuthor.class;
      } else if (Const.CONTENT_TYPE_GALLERY.equals(contentTypeId)) {
        return RealmGallery.class;
      } else if (Const.CONTENT_TYPE_IMAGE.equals(contentTypeId)) {
        return RealmImage.class;
      }
    }

    return null;
  }

  private static String extractResourceId(CDAResource resource) {
    return (String) resource.getSys().get("id");
  }

  private static String extractResourceContentType(CDAEntry entry) {
    Map map = (Map) entry.getSys().get("contentType");
    map = (Map) map.get("sys");
    return (String) map.get("id");
  }

  private static String extractResourceType(CDAResource resource) {
    return (String) resource.getSys().get("type");
  }

  private static String emptyIfNull(String str) {
    if (str == null) {
      return "";
    }
    return str;
  }

  private static <T extends RealmObject> T getOrCreate(Realm realm, Class<T> clazz,
      String remoteId) {
    T object = realm.where(clazz).equalTo("remoteId", remoteId).findFirst();

    if (object == null) {
      object = realm.createObject(clazz);
    }
    return object;
  }

  private static void saveSyncToken(String syncToken) {
    Preferences.get().edit().putString(Preferences.KEY_SYNC_TOKEN, syncToken).commit();
  }

  private static CDASyncedSpace fetchSynchronizedSpace() {
    CDAClient client = ClientProvider.get();
    String syncToken = Preferences.getSyncToken();
    boolean initial = syncToken == null;
    CDASyncedSpace space;

    if (initial) {
      space = client.synchronization().performInitial();
    } else {
      space = client.synchronization().performWithToken(syncToken);
    }

    return space;
  }

  private static boolean isDeleted(CDAResource resource) {
    String type = extractResourceType(resource);
    return Constants.CDAResourceType.DeletedAsset.toString().equals(type) ||
        Constants.CDAResourceType.DeletedEntry.toString().equals(type);
  }
}