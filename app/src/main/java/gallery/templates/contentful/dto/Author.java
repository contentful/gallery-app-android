package gallery.templates.contentful.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Author implements Parcelable {
  public static Author create(String remoteId,
      @Nullable String name,
      @Nullable String twitter,
      @Nullable String photoUrl,
      @Nullable String biography) {
    return new AutoValue_Author(remoteId, name, twitter, photoUrl, biography);
  }

  public abstract String remoteId();

  @Nullable public abstract String name();

  @Nullable public abstract String twitter();

  @Nullable public abstract String photoUrl();

  @Nullable public abstract String biography();

  // Parcelable :(
  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(remoteId());
    dest.writeValue(name());
    dest.writeValue(twitter());
    dest.writeValue(photoUrl());
    dest.writeValue(biography());
  }

  public static final Parcelable.Creator<Author> CREATOR = new Parcelable.Creator<Author>() {
    public Author createFromParcel(Parcel in) {
      ClassLoader classLoader = getClass().getClassLoader();

      String remoteId = (String) in.readValue(classLoader);
      String name = (String) in.readValue(classLoader);
      String twitter = (String) in.readValue(classLoader);
      String photoUrl = (String) in.readValue(classLoader);
      String biography = (String) in.readValue(classLoader);

      return Author.create(remoteId, name, twitter, photoUrl, biography);
    }

    public Author[] newArray(int size) {
      return new Author[size];
    }
  };
}
