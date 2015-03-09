package gallery.templates.contentful.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

public class ViewUtils {
  private static final float INTERPOLATOR_FACTOR = 2.0f;

  private ViewUtils() {
    throw new AssertionError();
  }

  @SuppressWarnings("deprecation")
  public static void setBackground(View view, Drawable drawable) {
    if (Build.VERSION.SDK_INT >= 16) {
      view.setBackground(drawable);
    } else {
      view.setBackgroundDrawable(drawable);
    }
  }

  public static boolean isGone(View view) {
    return view.getVisibility() == View.GONE;
  }

  public static void setViewHeight(View view, int height, boolean layout) {
    view.getLayoutParams().height = height;
    if (layout) {
      view.requestLayout();
    }
  }

  public static Animator toggleViewFade(final View target, final boolean show, int duration,
      @Nullable final Runnable startRunnable, @Nullable final Runnable endRunnable) {

    Animator animator;
    float alpha = show ? 1.0f : 0.0f;
    animator = ObjectAnimator.ofFloat(target, "alpha", alpha);
    animator.setDuration(duration);

    animator.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationStart(Animator animation) {
        if (show) {
          target.setVisibility(View.VISIBLE);

          if (startRunnable != null) {
            startRunnable.run();
          }
        }
      }

      @Override public void onAnimationEnd(Animator animation) {
        if (!show) {
          target.setVisibility(View.GONE);
        }

        if (endRunnable != null) {
          endRunnable.run();
        }
      }
    });

    return animator;
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public static Animator toggleViewCircular(View source, final View target, final boolean show,
      float radius, int duration, @Nullable final Runnable startRunnable, @Nullable final Runnable endRunnable) {

    Animator animator;

    // get the center for the clipping circle
    int cx = (source.getLeft() + source.getRight()) / 2;
    int cy = (source.getTop() + (int) source.getTranslationY() + source.getBottom());

    float radiusStart = show ? 0 : radius;
    float radiusEnd = show ? radius : 0;

    animator = ViewAnimationUtils.createCircularReveal(target, cx, cy, radiusStart, radiusEnd);
    animator.setDuration(duration);
    animator.setInterpolator(createInterpolator(show));

    animator.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationStart(Animator animation) {
        if (show) {
          target.setVisibility(View.VISIBLE);

          if (startRunnable != null) {
            startRunnable.run();
          }
        }
      }

      @Override public void onAnimationEnd(Animator animation) {
        if (!show) {
          target.setVisibility(View.GONE);
        }

        if (endRunnable != null) {
          endRunnable.run();
        }
      }
    });

    return animator;
  }

  private static Interpolator createInterpolator(boolean show) {
    if (show) {
      return new AccelerateInterpolator(INTERPOLATOR_FACTOR);
    }

    return new DecelerateInterpolator(INTERPOLATOR_FACTOR);
  }
}
