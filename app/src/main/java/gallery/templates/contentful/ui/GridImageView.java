package gallery.templates.contentful.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class GridImageView extends ImageView {
  public GridImageView(Context context) {
    super(context);
  }

  public GridImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public GridImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public GridImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
  }
}
