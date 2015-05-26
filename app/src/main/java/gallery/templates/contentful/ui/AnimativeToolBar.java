package gallery.templates.contentful.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import gallery.templates.contentful.R;

public class AnimativeToolBar extends Toolbar {
  private boolean visible = true;

  private ValueAnimator animator;

  public AnimativeToolBar(Context context) {
    super(context);
  }

  public AnimativeToolBar(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public AnimativeToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void show() {
    animateToolbar(true);
  }

  public void hide() {
    animateToolbar(false);
  }

  public boolean visible() {
    return visible;
  }

  private void animateToolbar(boolean show) {
    if (animator != null) {
      animator.cancel();
    }

    final MarginLayoutParams layoutParams = (MarginLayoutParams) getLayoutParams();
    int targetMargin = show ? 0 : -getHeight();

    animator = ValueAnimator.ofInt(layoutParams.topMargin, targetMargin);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        layoutParams.topMargin = (Integer) animation.getAnimatedValue();
        requestLayout();
      }
    });

    animator.setDuration(getResources().getInteger(R.integer.toolbar_animation_duration)).start();
    visible = show;
  }
}
