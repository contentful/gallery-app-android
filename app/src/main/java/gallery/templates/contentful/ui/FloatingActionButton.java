package gallery.templates.contentful.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Outline;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageButton;
import gallery.templates.contentful.lib.Const;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class FloatingActionButton extends ImageButton {
  public FloatingActionButton(Context context) {
    super(context);
  }

  public FloatingActionButton(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public FloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public FloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();

    if (Const.HAS_L) {
      setOutlineProvider(new ViewOutlineProvider() {
        @Override
        public void getOutline(View view, Outline outline) {
          outline.setOval(0, 0, getWidth(), getHeight());
        }
      });

      setClipToOutline(true);
    }
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    if (Const.HAS_L) {
      invalidateOutline();
    }
  }
}
