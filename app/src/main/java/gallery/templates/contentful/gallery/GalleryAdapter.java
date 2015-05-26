package gallery.templates.contentful.gallery;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.squareup.picasso.Picasso;
import gallery.templates.contentful.R;
import gallery.templates.contentful.lib.Utils;
import gallery.templates.contentful.vault.Gallery;
import gallery.templates.contentful.vault.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GalleryAdapter extends RecyclerView.Adapter {
  public static final int VIEW_TYPE_SECTION = R.layout.grid_section;

  public static final int VIEW_TYPE_ITEM = R.layout.grid_item;

  private final Map<Integer, Gallery> sections;

  private final ArrayList<Object> data;

  private final View.OnClickListener itemClickListener;

  private final int imageSize;

  public GalleryAdapter(View.OnClickListener itemClickListener, int imageSize) {
    this.itemClickListener = itemClickListener;
    this.imageSize = imageSize;
    sections = new HashMap<>();
    data = new ArrayList<>();
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
    v.setOnClickListener(itemClickListener);

    if (VIEW_TYPE_SECTION == viewType) {
      return new SectionViewHolder(v);
    }

    return new ItemViewHolder(v);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (isSection(position)) {
      onBindSection((SectionViewHolder) holder, position);
    } else {
      onBindItem((ItemViewHolder) holder, position);
    }
  }

  @Override public int getItemViewType(int position) {
    if (isSection(position)) {
      return VIEW_TYPE_SECTION;
    }

    return VIEW_TYPE_ITEM;
  }

  @Override public int getItemCount() {
    return data.size();
  }

  @SuppressWarnings("unchecked")
  public <T> T getItem(int position) {
    return (T) data.get(position);
  }

  public Gallery sectionFor(int position) {
    while (position > 0) {
      position -= 1;
      Gallery gallery = sections.get(position);
      if (gallery != null) {
        return gallery;
      }
    }

    return null;
  }

  public boolean isSection(int position) {
    return sections.get(position) != null;
  }

  public void setData(List<Gallery> galleryList) {
    clear();

    for (Gallery gallery : galleryList) {
      sections.put(data.size(), gallery);
      data.add(gallery);
      data.addAll(gallery.images());
    }
  }

  public void clear() {
    data.clear();
    sections.clear();
  }

  private void onBindSection(SectionViewHolder holder, int position) {
    Gallery gallery = (Gallery) data.get(position);
    holder.title.setText(gallery.title());
    loadPhoto(holder.cover, gallery.coverImage().url());
  }

  private void onBindItem(ItemViewHolder holder, int position) {
    loadPhoto(holder.photo, ((Image) data.get(position)).photo().url());
  }

  private void loadPhoto(ImageView imageView, String url) {
    Picasso.with(imageView.getContext())
        .load(Utils.imageUrl(url, imageSize, imageSize))
        .fit()
        .centerCrop()
        .placeholder(R.drawable.grid_placeholder)
        .into(imageView);
  }

  public static class ItemViewHolder extends RecyclerView.ViewHolder {
    public final View rootView;

    public @InjectView(R.id.photo) ImageView photo;

    ItemViewHolder(View itemView) {
      super(itemView);
      this.rootView = itemView;
      ButterKnife.inject(this, itemView);
    }
  }

  public static class SectionViewHolder extends RecyclerView.ViewHolder {
    public final View rootView;

    public @InjectView(R.id.title) TextView title;

    public @InjectView(R.id.cover) ImageView cover;

    SectionViewHolder(View itemView) {
      super(itemView);
      this.rootView = itemView;
      ButterKnife.inject(this, itemView);
    }
  }
}
