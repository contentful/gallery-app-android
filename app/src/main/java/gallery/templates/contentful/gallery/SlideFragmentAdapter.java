package gallery.templates.contentful.gallery;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import gallery.templates.contentful.fragments.SlideFragment;
import gallery.templates.contentful.vault.Gallery;
import gallery.templates.contentful.vault.Image;

public class SlideFragmentAdapter extends FragmentPagerAdapter {
  private final Gallery data;

  private final Map<Image, Fragment> fragments;

  public SlideFragmentAdapter(Context context, FragmentManager fm, Gallery gallery) {
    super(fm);
    this.data = gallery;
    this.fragments = new HashMap<>();

    for (Image image : gallery.images()) {
      fragments.put(image, SlideFragment.newSlide(context, image));
    }
  }

  public Image getImage(int position) {
    return data.images().get(position);
  }

  public SlideFragment fragmentForPosition(int position) {
    return (SlideFragment) fragments.get(getImage(position));
  }

  @Override public Fragment getItem(int position) {
    return fragmentForPosition(position);
  }

  @Override public int getCount() {
    return data.images().size();
  }
}
