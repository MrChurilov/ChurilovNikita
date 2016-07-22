package ru.test.rambler.churilovnikita.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import ru.test.rambler.churilovnikita.Constants;


public class PhotosList implements Parcelable {
    private List<Photo> mPhotoList = new ArrayList<Photo>();

    public PhotosList() {
    }

    public PhotosList(Parcel source) {
        int n = source.readInt();
        for (int i = 0; i < n; i++) {
            String urlPhoto = source.readString();
            int countLikes = source.readInt();
            ArrayList<String> tags = source.createStringArrayList();
            mPhotoList.add(new Photo(urlPhoto, tags, countLikes));
        }
    }

    public void addPhoto(Photo photo) {
        mPhotoList.add(photo);
    }

    public List<Photo> getPhotoList() {
        return mPhotoList;
    }

    public PhotosList(JsonArray json) {

        for (int i = 0; i < json.size(); i++) {
            try {
                String urlPhoto = json.get(i).getAsJsonObject().get("images").getAsJsonObject().get("low_resolution").getAsJsonObject().get("url").getAsString();
                int countLikes = json.get(i).getAsJsonObject().get("likes").getAsJsonObject().get("count").getAsInt();
                JsonArray tags = json.get(i).getAsJsonObject().get("tags").getAsJsonArray();

                ArrayList<String> tagsList = new ArrayList<String>();
                for (int j = 0; j < tags.size(); j++) {
                    tagsList.add(tags.get(j).getAsString());
                }

                Photo photo = new Photo(urlPhoto, tagsList, countLikes);
                mPhotoList.add(photo);

            } catch (Exception e) {
                Log.e(Constants.TAG, e.toString());
            }
        }

    }

    public int countPhoto() {
        return mPhotoList.size();
    }

    public Photo getPhoto(int position) {
        return mPhotoList.get(position);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int f) {
        int n = mPhotoList.size();
        parcel.writeInt(n);
        if (n > 0) {
            for (int i = 0; i < mPhotoList.size(); i++) {
                Photo photo = mPhotoList.get(i);
                parcel.writeInt(photo.countLikes);
                parcel.writeString(photo.UrlPhoto);
                parcel.writeStringList(photo.tags);
            }
        }
    }

    public static final Creator<PhotosList> CREATOR = new Creator<PhotosList>() {
        public PhotosList createFromParcel(Parcel source) {
            return new PhotosList(source);
        }

        public PhotosList[] newArray(int size) {
            return new PhotosList[size];
        }
    };


    public static class Photo {

        private final String UrlPhoto;
        private ArrayList<String> tags = new ArrayList<String>();
        private final int countLikes;

        public Photo(String UrlPhoto, ArrayList<String> tags, int countLikes) {
            this.countLikes = countLikes;
            this.tags = tags;
            this.UrlPhoto = UrlPhoto;
        }

        public String getUrlPhoto() {
            return UrlPhoto;
        }

        public ArrayList<String> getTags() {
            return tags;
        }

        public void setTags(ArrayList<String> tags) {
            this.tags = tags;
        }

        public int getCountLikes() {
            return countLikes;
        }
    }

}
