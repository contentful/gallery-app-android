package gallery.templates.contentful.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.parceler.Parcels;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import gallery.templates.contentful.R;
import gallery.templates.contentful.lib.Intents;
import gallery.templates.contentful.vault.Gallery;

public class GalleryInfoFragment extends Fragment {
  private Gallery gallery;

  @BindView(R.id.root) ViewGroup root;

  @BindView(R.id.title) TextView title;

  @BindView(R.id.description) TextView description;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    extractIntentArguments();
  }

  @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                     @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    return inflater.inflate(R.layout.fragment_gallery_info, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    initializeViews();
  }

  private void extractIntentArguments() {
    gallery = Parcels.unwrap(getArguments().getParcelable(Intents.EXTRA_GALLERY));
  }

  private void initializeViews() {
    title.setText(gallery.title());
    description.setText(gallery.description());
  }

  public void colorize(int colorDarkMuted, int colorVibrant, int colorLightMuted) {
    int red = Color.red(colorDarkMuted);
    int green = Color.green(colorDarkMuted);
    int blue = Color.blue(colorDarkMuted);

    root.setBackgroundColor(Color.argb(
        getResources().getInteger(R.integer.gallery_info_bg_alpha),
        red, green, blue));

    title.setTextColor(colorVibrant);
    description.setTextColor(colorLightMuted);
  }
}
