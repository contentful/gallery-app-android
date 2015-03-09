package gallery.templates.contentful.activities;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.transition.Transition;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnPageChange;
import gallery.templates.contentful.lib.Holder;
import gallery.templates.contentful.R;
import gallery.templates.contentful.dto.Gallery;
import gallery.templates.contentful.dto.Image;
import gallery.templates.contentful.fragments.GalleryInfoFragment;
import gallery.templates.contentful.fragments.SlideFragment;
import gallery.templates.contentful.gallery.SlideFragmentAdapter;
import gallery.templates.contentful.gallery.SlideImageAdapter;
import gallery.templates.contentful.lib.Const;
import gallery.templates.contentful.lib.Intents;
import gallery.templates.contentful.lib.TransitionListenerAdapter;
import gallery.templates.contentful.lib.Utils;
import gallery.templates.contentful.ui.FloatingActionButton;
import gallery.templates.contentful.ui.LockableViewPager;
import gallery.templates.contentful.ui.ViewUtils;

public class GalleryActivity extends FragmentActivity {
  private static final ArgbEvaluator argbEvaluator = new ArgbEvaluator();

  private SlideImageAdapter imageAdapter;
  private SlideFragmentAdapter fragmentAdapter;
  private GalleryInfoFragment galleryInfoFragment;
  private BroadcastReceiver colorizeReceiver;

  private Gallery gallery;
  private Image image;

  @InjectView(R.id.photo) ImageView photo;
  @InjectView(R.id.star) FloatingActionButton star;
  @InjectView(R.id.pager) LockableViewPager viewPager;
  @InjectView(R.id.info_container) ViewGroup infoContainer;

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gallery);
    ButterKnife.inject(this);

    extractIntentArguments();
    attachGalleryInfoFragment();
    createAdapterInstances();
    setViewPagerAdapter();
    initializeViews();
    setupColorizeReceiver(true);
  }

  @Override protected void onDestroy() {
    setupColorizeReceiver(false);
    super.onDestroy();
  }

  @Override public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    int index = viewPager.getCurrentItem();
    setViewPagerAdapter();
    viewPager.setCurrentItem(index);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  @Override public void onBackPressed() {
    if (Const.HAS_L) {
      if (isShowingInitialImage()) {
        createAlphaAnimator(star, false).withEndAction(new Runnable() {
          @Override public void run() {
            GalleryActivity.super.onBackPressed();
          }
        }).start();
      } else {
        getWindow().setSharedElementReturnTransition(null);
        finish();
      }

      return;
    }

    super.onBackPressed();
  }

  @OnPageChange(value = R.id.pager, callback = OnPageChange.Callback.PAGE_SCROLLED)
  void onPageSelected(int position, float positionOffset, int positionOffsetPixels) {
    if (positionOffsetPixels == 0) {
      return;
    }

    SlideFragment leftFragment = fragmentAdapter.fragmentForPosition(position);
    SlideFragment rightFragment = fragmentAdapter.fragmentForPosition(position + 1);

    if (leftFragment != null && rightFragment != null) {
      int leftVibrant = leftFragment.getColorVibrant();
      int rightVibrant = rightFragment.getColorVibrant();

      int leftDarkMuted = leftFragment.getColorDarkMuted();
      int rightDarkMuted = rightFragment.getColorDarkMuted();

      int vibrant = (Integer) argbEvaluator.evaluate(
          positionOffset, leftVibrant, rightVibrant);

      int darkMuted = (Integer) argbEvaluator.evaluate(
          positionOffset, leftDarkMuted, rightDarkMuted);

      colorizeButton(vibrant, darkMuted);
    }
  }

  @OnClick(R.id.star) void onClickStar(View v) {
    toggleInfoContainer(v);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  private void initializeViews() {
    viewPager.setCurrentItem(gallery.images().indexOf(image));
    viewPager.setOffscreenPageLimit(getResources().getInteger(
        R.integer.gallery_pager_offscreen_limit));

    repositionStar();
    ViewUtils.setViewHeight(infoContainer, Const.IMAGE_HEIGHT, true);

    if (Const.HAS_L && Utils.isPortrait(this)) {
      ViewUtils.setViewHeight(photo, Const.IMAGE_HEIGHT, false);
      photo.setImageDrawable(Holder.get());

      getWindow().getSharedElementEnterTransition().addListener(new TransitionListenerAdapter() {
        @Override public void onTransitionEnd(Transition transition) {
          getWindow().getSharedElementEnterTransition().removeListener(this);
          photo.setVisibility(View.GONE);
        }
      });
    }
  }

  private void attachGalleryInfoFragment() {
    galleryInfoFragment = (GalleryInfoFragment) GalleryInfoFragment
        .instantiate(this, GalleryInfoFragment.class.getName(), getIntent().getExtras());

    getSupportFragmentManager().beginTransaction()
        .add(R.id.info_container, galleryInfoFragment)
        .commit();
  }

  private void extractIntentArguments() {
    Intent intent = getIntent();
    gallery = intent.getParcelableExtra(Intents.EXTRA_GALLERY);
    image = intent.getParcelableExtra(Intents.EXTRA_IMAGE);
  }

  private void createAdapterInstances() {
    fragmentAdapter = new SlideFragmentAdapter(this, getSupportFragmentManager(), gallery);
    imageAdapter = new SlideImageAdapter(gallery.images());
  }

  private void setViewPagerAdapter() {
    if (Utils.isPortrait(this)) {
      viewPager.setAdapter(fragmentAdapter);
    } else {
      viewPager.setAdapter(imageAdapter);
    }
  }

  private void toggleInfoContainer(final View view) {
    final boolean show = ViewUtils.isGone(infoContainer);

    if (show) {
      SlideFragment slideFragment = fragmentAdapter.fragmentForPosition(viewPager.getCurrentItem());

      galleryInfoFragment.colorize(slideFragment.getColorDarkMuted(),
          slideFragment.getColorVibrant(), slideFragment.getColorLightMuted());
    }

    Animator animator;
    Runnable startRunnable = new Runnable() {
      @Override public void run() {
        star.setEnabled(false);
        viewPager.setLocked(show);
      }
    };

    Runnable endRunnable = new Runnable() {
      @Override public void run() {
        star.setEnabled(true);
        viewPager.setLocked(show);
      }
    };

    int duration = getResources().getInteger(R.integer.toggle_animation_duration);
    if (Const.HAS_L) {
      float radius = Math.max(photo.getWidth(), photo.getHeight()) * 2.0f;
      animator = ViewUtils.toggleViewCircular(view, infoContainer, show, radius, duration,
          startRunnable, endRunnable);
    } else {
      animator = ViewUtils.toggleViewFade(infoContainer, show, duration,
          startRunnable, endRunnable);
    }

    animator.start();
  }

  private void repositionStar() {
    int fabSize = getResources().getDimensionPixelSize(R.dimen.fab_size);
    star.setTranslationY(Const.IMAGE_HEIGHT - (fabSize / 2.0f));
  }

  private boolean isShowingInitialImage() {
    int originalIndex = gallery.images().indexOf(image);
    return originalIndex == viewPager.getCurrentItem();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  private void colorizeButton(int colorNormal, int colorPressed) {
    Drawable drawable = getResources().getDrawable(R.drawable.info_background);
    drawable.setColorFilter(colorNormal, PorterDuff.Mode.MULTIPLY);

    if (Const.HAS_L) {
      ColorStateList stateList = new ColorStateList(
          new int[][] {
              new int[] { }
          },
          new int[] {
              colorPressed
          });

      RippleDrawable rippleDrawable = new RippleDrawable(stateList, drawable, null);
      ViewUtils.setBackground(star, rippleDrawable);
    } else {
      ViewUtils.setBackground(star, drawable);
    }
  }

  private void setupColorizeReceiver(boolean register) {
    if (register) {
      if (colorizeReceiver == null) {
        colorizeReceiver = new BroadcastReceiver() {
          @Override public void onReceive(Context context, Intent intent) {
            Image image = intent.getParcelableExtra(Intents.EXTRA_IMAGE);
            int vibrant = intent.getIntExtra(Intents.EXTRA_CLR_VIBRANT, 0);
            int darkMuted = intent.getIntExtra(Intents.EXTRA_CLR_DARK_MUTED, 0);
            colorize(image, vibrant, darkMuted);
          }
        };
      }

      registerReceiver(colorizeReceiver, new IntentFilter(Intents.ACTION_COLORIZE));
    } else {
      unregisterReceiver(colorizeReceiver);
    }
  }

  private void colorize(Image image, int colorVibrant, int colorDarkMuted) {
    if (image.equals(fragmentAdapter.getImage(viewPager.getCurrentItem()))) {
      colorizeButton(colorVibrant, colorDarkMuted);

      if (star.getAlpha() == 0) {
        long startDelay = getResources().getInteger(R.integer.toggle_animation_duration);
        createAlphaAnimator(star, true).setStartDelay(startDelay).start();
      }
    }
  }

  private ViewPropertyAnimator createAlphaAnimator(View v, boolean show) {
    int duration = getResources().getInteger(R.integer.gallery_alpha_duration);
    float alpha = show ? 1.0f : 0.0f;
    return v.animate().alpha(alpha).setDuration(duration);
  }
}
