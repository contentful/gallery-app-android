package gallery.templates.contentful.vault;

import com.contentful.vault.Space;

import gallery.templates.contentful.lib.Const;

@Space(value = Const.SPACE_ID, models = {Author.class, Gallery.class, Image.class}, locales = {"en-US"})
public class GallerySpace {
}
