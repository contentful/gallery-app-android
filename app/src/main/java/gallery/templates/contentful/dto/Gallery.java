package gallery.templates.contentful.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import java.util.List;

@AutoValue
public abstract class Gallery implements Parcelable {
  public static Gallery create(String remoteId,
      @Nullable String title,
      @Nullable String slug,
      @Nullable Author author,
      @Nullable String coverImageUrl,
      @Nullable String description,
      List<Image> images,
      @Nullable List<String> tags,
      @Nullable String date) {
    return new AutoValue_Gallery(remoteId, title, slug, author, coverImageUrl, description,
        images, tags, date);
  }

  public abstract String remoteId();

  @Nullable public abstract String title();

  @Nullable public abstract String slug();

  @Nullable public abstract Author author();

  @Nullable public abstract String coverImageUrl();

  @Nullable public abstract String description();

  public abstract List<Image> images();

  @Nullable public abstract List<String> tags();

  @Nullable public abstract String date();

  // Parcelable :(
  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(remoteId());
    dest.writeValue(title());
    dest.writeValue(slug());
    dest.writeValue(author());
    dest.writeValue(coverImageUrl());
    dest.writeValue(description());
    dest.writeValue(images());
    dest.writeValue(tags());
    dest.writeValue(date());
  }

  public static final Parcelable.Creator<Gallery> CREATOR = new Parcelable.Creator<Gallery>() {
    @SuppressWarnings("unchecked")
    public Gallery createFromParcel(Parcel in) {
      ClassLoader classLoader = getClass().getClassLoader();

      String remoteId = (String) in.readValue(classLoader);
      String title = (String) in.readValue(classLoader);
      String slug = (String) in.readValue(classLoader);
      Author author = (Author) in.readValue(classLoader);
      String coverImageUrl = (String) in.readValue(classLoader);
      String description = (String) in.readValue(classLoader);
      List images = (List) in.readValue(classLoader);
      List tags = (List) in.readValue(classLoader);
      String date = (String) in.readValue(classLoader);

      return Gallery.create(remoteId, title, slug, author, coverImageUrl, description,
          images, tags, date);
    }

    public Gallery[] newArray(int size) {
      return new Gallery[size];
    }
  };
}
