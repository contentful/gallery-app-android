package gallery.templates.contentful.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Image implements Parcelable {
  public static Image create(String remoteId,
      @Nullable String title,
      @Nullable String photoUrl,
      @Nullable String caption,
      @Nullable String credits) {
    return new AutoValue_Image(remoteId, title, photoUrl, caption, credits);
  }

  public abstract String remoteId();

  @Nullable public abstract String title();

  @Nullable public abstract String photoUrl();

  @Nullable public abstract String caption();

  @Nullable public abstract String credits();

  // Parcelable :(
  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(remoteId());
    dest.writeValue(title());
    dest.writeValue(photoUrl());
    dest.writeValue(caption());
    dest.writeValue(credits());
  }

  public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
    public Image createFromParcel(Parcel in) {
      ClassLoader classLoader = getClass().getClassLoader();

      String remoteId = (String) in.readValue(classLoader);
      String title = (String) in.readValue(classLoader);
      String photoUrl = (String) in.readValue(classLoader);
      String caption = (String) in.readValue(classLoader);
      String credits = (String) in.readValue(classLoader);

      return Image.create(remoteId, title, photoUrl, caption, credits);
    }

    public Image[] newArray(int size) {
      return new Image[size];
    }
  };
}
