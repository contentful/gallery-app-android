package gallery.templates.contentful.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmGallery extends RealmObject {
  @PrimaryKey
  private String remoteId;

  private String title;
  private String slug;
  private RealmAuthor author;
  private RealmAsset coverImage;
  private String description;
  private RealmList<RealmImage> images;
  private String tags;
  private String date;

  public String getRemoteId() {
    return remoteId;
  }

  public void setRemoteId(String remoteId) {
    this.remoteId = remoteId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public RealmAuthor getAuthor() {
    return author;
  }

  public void setAuthor(RealmAuthor author) {
    this.author = author;
  }

  public RealmAsset getCoverImage() {
    return coverImage;
  }

  public void setCoverImage(RealmAsset coverImage) {
    this.coverImage = coverImage;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public RealmList<RealmImage> getImages() {
    return images;
  }

  public void setImages(RealmList<RealmImage> images) {
    this.images = images;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
