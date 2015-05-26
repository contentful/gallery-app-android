package gallery.templates.contentful.vault;

import com.contentful.vault.Asset;
import com.contentful.vault.ContentType;
import com.contentful.vault.Field;
import com.contentful.vault.Resource;
import gallery.templates.contentful.lib.Const;
import org.parceler.Parcel;

@Parcel
@ContentType(Const.CONTENT_TYPE_IMAGE)
public class Image extends Resource {
  @Field String title;

  @Field Asset photo;

  @Field("imageCaption")
  String caption;

  @Field("imageCredits")
  String credits;

  public String title() {
    return title;
  }

  public Asset photo() {
    return photo;
  }

  public String caption() {
    return caption;
  }

  public String credits() {
    return credits;
  }
}
