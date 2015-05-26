package gallery.templates.contentful.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import gallery.templates.contentful.R;
import gallery.templates.contentful.lib.Const;
import gallery.templates.contentful.lib.Intents;
import gallery.templates.contentful.lib.TargetAdapter;
import gallery.templates.contentful.lib.Utils;
import gallery.templates.contentful.ui.ViewUtils;
import gallery.templates.contentful.vault.Image;
import org.parceler.Parcels;

public class SlideFragment extends Fragment implements Palette.PaletteAsyncListener {
  private Image image;

  private AsyncTask paletteTask;

  private Target target;

  private Bitmap bitmap;

  private boolean hasPalette;

  private int colorLightMuted;

  private int colorDarkMuted;

  private int colorVibrant;

  @InjectView(R.id.photo) ImageView photo;

  @InjectView(R.id.bottom) ViewGroup bottomContainer;

  @InjectView(R.id.title) TextView title;

  @InjectView(R.id.caption) TextView caption;

  public static SlideFragment newSlide(Context context, Image image) {
    Bundle b = new Bundle();
    b.putParcelable(Intents.EXTRA_IMAGE, Parcels.wrap(image));
    return (SlideFragment) SlideFragment.instantiate(context, SlideFragment.class.getName(), b);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    extractIntentArguments();
    displayPhoto();
  }

  @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    return inflater.inflate(R.layout.fragment_slide, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);
    ViewUtils.setViewHeight(photo, Const.IMAGE_HEIGHT, true);
    title.setText(image.title());
    caption.setText(image.caption());
    applyColor();
    applyImage();
  }

  @Override public void onDestroyView() {
    ButterKnife.reset(this);
    super.onDestroyView();
  }

  @Override public void onDestroy() {
    cancelPaletteTask();
    if (target != null) {
      Picasso.with(getActivity()).cancelRequest(target);
      target = null;
    }

    bitmap = null;
    super.onDestroy();
  }

  @Override public void onGenerated(Palette palette) {
    hasPalette = true;
    target = null;
    int black = getResources().getColor(android.R.color.black);
    int white = getResources().getColor(android.R.color.white);
    colorDarkMuted = palette.getDarkMutedColor(black);
    colorVibrant = palette.getVibrantColor(white);
    colorLightMuted = palette.getLightMutedColor(white);
    applyColor();
    sendPalette();
  }

  private void extractIntentArguments() {
    Bundle b = getArguments();
    image = Parcels.unwrap(b.getParcelable(Intents.EXTRA_IMAGE));
  }

  private void cancelPaletteTask() {
    if (paletteTask != null) {
      paletteTask.cancel(true);
      paletteTask = null;
    }
  }

  private void sendPalette() {
    getActivity().sendBroadcast(attachColors(
        new Intent(Intents.ACTION_COLORIZE)
            .putExtra(Intents.EXTRA_IMAGE, Parcels.wrap(image))));
  }

  private Intent attachColors(Intent intent) {
    intent.putExtra(Intents.EXTRA_CLR_LIGHT_MUTED, colorLightMuted);
    intent.putExtra(Intents.EXTRA_CLR_DARK_MUTED, colorDarkMuted);
    intent.putExtra(Intents.EXTRA_CLR_VIBRANT, colorVibrant);
    return intent;
  }

  private void applyImage() {
    if (bitmap != null && photo != null) {
      photo.setImageBitmap(bitmap);
    }
  }

  private void applyColor() {
    if (hasPalette && bottomContainer != null) {
      bottomContainer.setBackgroundColor(colorDarkMuted);
      title.setTextColor(colorVibrant);
      caption.setTextColor(colorLightMuted);
      bottomContainer.animate().alpha(1.0f).setDuration(200).start();
      sendPalette();
    }
  }

  private void displayPhoto() {
    Picasso.with(getActivity()).load(Utils.imageUrl(image.photo().url()))
        .resize(Const.IMAGE_WIDTH, Const.IMAGE_HEIGHT)
        .centerCrop()
        .into(target = new TargetAdapter() {
          @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            SlideFragment.this.bitmap = bitmap;
            applyImage();
            paletteTask = new Palette.Builder(bitmap)
                .maximumColorCount(32)
                .generate(SlideFragment.this);
          }
        });
  }

  public int getColorVibrant() {
    return colorVibrant;
  }

  public int getColorDarkMuted() {
    return colorDarkMuted;
  }

  public int getColorLightMuted() {
    return colorLightMuted;
  }
}
