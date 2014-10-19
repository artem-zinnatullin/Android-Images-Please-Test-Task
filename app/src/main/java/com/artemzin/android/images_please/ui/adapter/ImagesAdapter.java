package com.artemzin.android.images_please.ui.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.artemzin.android.images_please.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Artem Zinnatullin [artem.zinnatullin@gmail.com]
 */
public class ImagesAdapter extends BaseAdapter {

    private WeakReference<Activity> mActivityWeakReference = new WeakReference<Activity>(null);
    private List<File> mImageFiles;
    private ImageLoader mImageLoader = ImageLoader.getInstance();

    public ImagesAdapter(@NonNull Activity activity) {
        setData(activity, new ArrayList<File>(0));
    }

    public void setData(@NonNull Activity activity, @NonNull List<File> imageFiles) {
        mActivityWeakReference = new WeakReference<Activity>(activity);
        mImageFiles = imageFiles;
        notifyDataSetChanged();
    }

    @Override public int getCount() {
        return mImageFiles.size();
    }

    @Override public File getItem(int position) {
        return mImageFiles.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mActivityWeakReference.get().getLayoutInflater().inflate(R.layout.list_item_image, parent, false);
            viewHolder  = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        drawView(position, viewHolder);

        return convertView;
    }

    private void drawView(int position, @NonNull ViewHolder viewHolder) {
        viewHolder.mImageView.setImageDrawable(null);

        final File possibleImageFile = mImageFiles.get(position);
        final String imageUri = "file:///" + possibleImageFile.getAbsolutePath();

        mImageLoader.displayImage(imageUri, viewHolder.mImageView);
        viewHolder.mFilePathTextView.setText(possibleImageFile.getPath());
    }

    static class ViewHolder {
        @InjectView(R.id.list_item_image_image_view)
        ImageView mImageView;

        @InjectView(R.id.list_item_image_view_file_path_text_view)
        TextView mFilePathTextView;

        ViewHolder(@NonNull View view) {
            ButterKnife.inject(this, view);
        }
    }
}
