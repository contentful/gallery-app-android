package gallery.templates.contentful.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmAsset extends RealmObject {
  @PrimaryKey
  private String remoteId;

  private String url;
  private String mimeType;

  public String getRemoteId() {
    return remoteId;
  }

  public void setRemoteId(String remoteId) {
    this.remoteId = remoteId;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }
}
