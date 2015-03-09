package gallery.templates.contentful.lib;

import gallery.templates.contentful.dto.Author;
import gallery.templates.contentful.dto.Gallery;
import gallery.templates.contentful.dto.Image;
import gallery.templates.contentful.realm.RealmAsset;
import gallery.templates.contentful.realm.RealmAuthor;
import gallery.templates.contentful.realm.RealmGallery;
import gallery.templates.contentful.realm.RealmImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/** Converts realm objects to POJOs. */
public class RealmConverter {
  private RealmConverter() {
    throw new AssertionError();
  }

  public static Gallery gallery(RealmGallery gallery) {
    // author
    Author author = null;
    RealmAuthor realmAuthor = gallery.getAuthor();
    if (realmAuthor != null) {
      author = author(realmAuthor);
    }

    // cover image
    String coverImageUrl = null;
    RealmAsset coverImage = gallery.getCoverImage();
    if (coverImage != null) {
      coverImageUrl = coverImage.getUrl();
    }

    // images
    final List<Image> images = new ArrayList<>();
    Observable.from(gallery.getImages())
        .map(new Func1<RealmImage, Image>() {
          @Override public Image call(RealmImage realmImage) {
            return image(realmImage);
          }
        })
        .subscribe(new Action1<Image>() {
          @Override public void call(Image image) {
            images.add(image);
          }
        });

    // tags
    List<String> tags = null;
    String galleryTags = gallery.getTags();
    if (galleryTags != null) {
      tags = split(galleryTags);
    }

    return Gallery.create(gallery.getRemoteId(), gallery.getTitle(), gallery.getSlug(),
        author, coverImageUrl, gallery.getDescription(), images, tags, gallery.getDate());
  }

  public static Author author(RealmAuthor author) {
    // profile photo
    RealmAsset profilePhoto = author.getProfilePhoto();
    String photoUrl = null;
    if (profilePhoto != null) {
      photoUrl = profilePhoto.getUrl();
    }

    return Author.create(author.getRemoteId(), author.getName(), author.getTwitter(),
        photoUrl, author.getBiography());
  }

  public static Image image(RealmImage image) {
    // photo
    String photoUrl = null;
    RealmAsset photo = image.getPhoto();
    if (photo != null) {
      photoUrl = Utils.imageUrl(photo.getUrl());
    }

    return Image.create(image.getRemoteId(), image.getTitle(), photoUrl, image.getImageCaption(),
        image.getImageCredits());
  }

  private static List<String> split(String str) {
    return Arrays.asList(str.split("\\s*,\\s*"));
  }
}
