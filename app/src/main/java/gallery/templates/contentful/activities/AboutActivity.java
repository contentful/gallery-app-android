package gallery.templates.contentful.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gallery.templates.contentful.R;

public class AboutActivity extends AppCompatActivity {
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about);
    ButterKnife.inject(this);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @OnClick({ R.id.btn_faq, R.id.btn_feedback, R.id.btn_contact, R.id.btn_license })
  void onClickButton(View view) {
    int urlResId;

    switch (view.getId()) {
      case R.id.btn_faq:
        urlResId = R.string.url_faq;
        break;

      case R.id.btn_feedback:
        urlResId = R.string.url_feedback;
        break;

      case R.id.btn_contact:
        urlResId = R.string.url_contact;
        break;

      case R.id.btn_license:
        urlResId = R.string.url_licensing;
        break;

      default:
        return;
    }

    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(urlResId))));
  }
}
