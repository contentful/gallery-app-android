package gallery.templates.contentful.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmAuthor extends RealmObject {
  @PrimaryKey
  private String remoteId;

  private String name;
  private String twitter;
  private String biography;
  private RealmAsset profilePhoto;

  public String getRemoteId() {
    return remoteId;
  }

  public void setRemoteId(String remoteId) {
    this.remoteId = remoteId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTwitter() {
    return twitter;
  }

  public void setTwitter(String twitter) {
    this.twitter = twitter;
  }

  public String getBiography() {
    return biography;
  }

  public void setBiography(String biography) {
    this.biography = biography;
  }

  public RealmAsset getProfilePhoto() {
    return profilePhoto;
  }

  public void setProfilePhoto(RealmAsset profilePhoto) {
    this.profilePhoto = profilePhoto;
  }
}
