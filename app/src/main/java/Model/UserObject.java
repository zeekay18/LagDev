package Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Oriaje on 3/7/2017.
 */
public class UserObject {


    @SerializedName("login")
    private String username;

    @SerializedName("avatar_url")
    private String imageUrl;

    @SerializedName("url")
    private String profileUrl;

    public UserObject()
    {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public static class SearchResult {

        private ArrayList<UserObject> items;

        public ArrayList<UserObject> getItems(){return items;}
    }

}
