package gallery.templates.contentful.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class LockableViewPager extends ViewPager {
  private boolean locked;

  public LockableViewPager(Context context) {
    super(context);
  }

  public LockableViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    return !locked && super.onInterceptTouchEvent(ev);
  }

  public void setLocked(boolean locked) {
    this.locked = locked;
  }
}
