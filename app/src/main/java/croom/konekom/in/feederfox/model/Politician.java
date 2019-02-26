package croom.konekom.in.feederfox.model;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
@SuppressWarnings("serial")
public class Politician implements Serializable {
    private String Name,Image_URL,familyhistory,qualification,videoUrl;
    private int id;
    private JSONArray PoliticalComment;

    public Politician(int id,String Name, String Image_URL, JSONArray PoliticalComment,String qualification,String familyhistory,String videoUrl){
        this.Name = Name;
        this.PoliticalComment = PoliticalComment;
        this.Image_URL = Image_URL;
        this.id = id;
        this.videoUrl = videoUrl;
        this.qualification = qualification;
        this.familyhistory = familyhistory;
    }

    public ArrayList<Comment> getPoliticalComment() {
        ArrayList<Comment> commentArrayList = new ArrayList<>();
        for(int i=0;i<PoliticalComment.length();i++){
            try {
                JSONObject commentObject = PoliticalComment.getJSONObject(i);
                Comment comment = new Comment(commentObject.getString("user"),commentObject.getString("comment"));
                commentArrayList.add(comment);
                Log.d("Comment comment",comment.getComment());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return commentArrayList;
    }

    public String getImage_URL() {
        return Image_URL;
    }

    public String getName() {
        return Name;
    }

    public int getId() {
        return id;
    }

    public String getFamilyhistory() {
        return familyhistory;
    }

    public String getQualification() {
        return qualification;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}
