package gallery.templates.contentful.vault;

import com.contentful.vault.Asset;
import com.contentful.vault.ContentType;
import com.contentful.vault.Field;
import com.contentful.vault.Resource;
import gallery.templates.contentful.lib.Const;
import java.util.List;
import org.parceler.Parcel;

@Parcel
@ContentType(Const.CONTENT_TYPE_GALLERY)
public class Gallery extends Resource {
  @Field String title;

  @Field String slug;

  @Field Author author;

  @Field Asset coverImage;

  @Field String description;

  @Field List<Image> images;

  @Field List<String> tags;

  @Field String date;

  public String title() {
    return title;
  }

  public String slug() {
    return slug;
  }

  public Author author() {
    return author;
  }

  public Asset coverImage() {
    return coverImage;
  }

  public String description() {
    return description;
  }

  public List<Image> images() {
    return images;
  }

  public List<String> tags() {
    return tags;
  }

  public String date() {
    return date;
  }
}
