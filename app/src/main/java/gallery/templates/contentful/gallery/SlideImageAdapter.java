package gallery.templates.contentful.gallery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;
import gallery.templates.contentful.lib.Utils;
import gallery.templates.contentful.vault.Image;

public class SlideImageAdapter extends PagerAdapter {
  private final List<Image> images;

  public SlideImageAdapter(List<Image> images) {
    this.images = images;
  }

  @Override public int getCount() {
    return images.size();
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override public Object instantiateItem(ViewGroup container, int position) {
    Context context = container.getContext();
    ImageView imageView = new ImageView(context);
    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    Picasso.get().load(Utils.imageUrl(images.get(position).photo().url())).into(imageView);
    container.addView(imageView);
    return imageView;
  }

  @Override public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }
}
