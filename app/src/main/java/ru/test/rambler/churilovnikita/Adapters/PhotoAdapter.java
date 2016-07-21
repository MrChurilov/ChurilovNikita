package ru.test.rambler.churilovnikita.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.volokh.danylo.hashtaghelper.HashTagHelper;


import butterknife.BindView;
import butterknife.ButterKnife;
import ru.test.rambler.churilovnikita.UI.FullScreenActivity;
import ru.test.rambler.churilovnikita.Interfaces.Searchable;
import ru.test.rambler.churilovnikita.Model.PhotosList;
import ru.test.rambler.churilovnikita.R;


public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private PhotosList mPhotoList;
    private Context mContext;
    private Searchable mCallback;

    public PhotoAdapter(Context context, PhotosList photoList,Searchable callback) {
        this.mContext = context;
        this.mPhotoList = photoList;
        this.mCallback=callback;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.card_photo_item, parent, false);
        return new PhotoViewHolder(itemView);
    }

    private PhotosList.Photo getItem(int position) {
        return mPhotoList.getPhoto(position);
    }

    public void setData(PhotosList photoList) {
        mPhotoList = photoList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        holder.fillView(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mPhotoList.countPhoto();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_photo)
        ImageView mPhotoImgV;
        @BindView(R.id.txt_likes)
        TextView mLikesTxtV;
        @BindView(R.id.txt_tags)
        TextView mTagsTxtV;


        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void fillView(final PhotosList.Photo photo) {
            mLikesTxtV.setText(mContext.getResources().getQuantityString(R.plurals.likes_plural, photo.getCountLikes(), photo.getCountLikes()));

            Picasso.with(mContext).load(photo.getUrlPhoto()).into(mPhotoImgV);
            mPhotoImgV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(FullScreenActivity.newInstance(mContext, photo.getUrlPhoto()));
                }
            });

            StringBuilder hashTags = new StringBuilder();
            for (String tag : photo.getTags()) {
                hashTags.append("#" + tag + " ");
            }
            mTagsTxtV.setText(hashTags.length() > 0 ? hashTags.toString() : mContext.getResources().getString(R.string.NoTags));
            HashTagHelper mHashTagHelper = HashTagHelper.Creator.create(mContext.getResources().getColor(R.color.colorPrimary), new HashTagHelper.OnHashTagClickListener() {
                @Override
                public void onHashTagClicked(String hashTag) {
                    mCallback.searchByTag(hashTag);
                }
            });
            mHashTagHelper.handle(mTagsTxtV);
        }
    }
}
