package com.anbui.elephant.interaction;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

public class Interaction {
    private final String contentId;
    private final SharedPreferences sharedPreferences;
    public boolean isRequesting = false;
    public boolean isAllowAction = true;

    public interface InteractionCallback {
        void onResult(boolean isSuccess, int views, int likes);
    }

    public Interaction(Context context, String contentId) {
        this.contentId = contentId;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        convertOldLocalDataToNewFormat("views");
        convertOldLocalDataToNewFormat("likes");
    }

    public void initialize(InteractionCallback callback) {
        if (!isReady()) {
            callback.onResult(false, 0, 0);
            return;
        }

        view(callback);
        isAllowAction = true;
    }

    public void get(InteractionCallback callback) {
        if (!isReady()) {
            callback.onResult(false, 0, 0);
            return;
        }

        callback.onResult(true, getViewCount(), getLikeCount());
    }

    public void view(InteractionCallback callback) {
        setViews();
        callback.onResult(true, getViewCount(), getLikeCount());
    }

    public void like(InteractionCallback callback) {
        setLikes();
        callback.onResult(true, getViewCount(), getLikeCount());
    }

    public String getFomatedViewCount() {
        return InteractionUtils.formatCount(getViewCount());
    }

    public String getFormatedLikeCount() {
        return InteractionUtils.formatCount(getLikeCount());
    }

    public int getViewCount() {
        return getViews().size();
    }

    public int getLikeCount() {
        return isLiked() ? 1 : 0;
    }

    private boolean isReady() {
        return contentId != null && !contentId.isEmpty();
    }

    public boolean isLiked() {
        return getLikes().contains(contentId);
    }

    private boolean isViewed() {
        return getViews().contains(contentId);
    }

    public void setLikes() {
        Set<String> set = getLikes();
        if (isLiked()) {
            set.remove(contentId);
        } else {
            set.add(contentId);
        }
        sharedPreferences.edit().putStringSet("likes", set).apply();
    }

    public Set<String> getLikes() {
        return new HashSet<>(sharedPreferences.getStringSet("likes", new HashSet<>()));
    }

    public void setViews() {
        if (isViewed()) return;
        Set<String> set = getViews();
        set.add(contentId);
        sharedPreferences.edit().putStringSet("views", set).apply();
    }

    public Set<String> getViews() {
        return new HashSet<>(sharedPreferences.getStringSet("views", new HashSet<>()));
    }

    private void convertOldLocalDataToNewFormat(String key) {
        Object raw = sharedPreferences.getAll().get(key);

        if (raw instanceof Set) return;

        if (raw instanceof String old) {
            Set<String> newSet = new HashSet<>();

            if (!old.isEmpty()) {
                String[] parts = old.split(",");

                for (String part : parts) {
                    if (!part.trim().isEmpty()) {
                        newSet.add(part.trim());
                    }
                }
            }

            sharedPreferences.edit()
                    .putStringSet(key, newSet)
                    .apply();
        }
    }
}
