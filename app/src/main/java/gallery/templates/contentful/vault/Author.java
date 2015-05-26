package gallery.templates.contentful.vault;

import com.contentful.vault.Asset;
import com.contentful.vault.ContentType;
import com.contentful.vault.Field;
import com.contentful.vault.Resource;
import gallery.templates.contentful.lib.Const;
import org.parceler.Parcel;

@Parcel
@ContentType(Const.CONTENT_TYPE_AUTHOR)
public class Author extends Resource {
  @Field String name;

  @Field String twitterHandle;

  @Field Asset profilePhoto;

  @Field String biography;

  public String name() {
    return name;
  }

  public String twitterHandle() {
    return twitterHandle;
  }

  public Asset profilePhoto() {
    return profilePhoto;
  }

  public String biography() {
    return biography;
  }
}
