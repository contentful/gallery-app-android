package gallery.templates.contentful.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmImage extends RealmObject {
  @PrimaryKey
  private String remoteId;

  private String title;
  private String imageCaption;
  private String imageCredits;
  private RealmAsset photo;

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

  public String getImageCaption() {
    return imageCaption;
  }

  public void setImageCaption(String imageCaption) {
    this.imageCaption = imageCaption;
  }

  public String getImageCredits() {
    return imageCredits;
  }

  public void setImageCredits(String imageCredits) {
    this.imageCredits = imageCredits;
  }

  public RealmAsset getPhoto() {
    return photo;
  }

  public void setPhoto(RealmAsset photo) {
    this.photo = photo;
  }
}
