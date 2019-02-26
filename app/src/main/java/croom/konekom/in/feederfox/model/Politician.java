package croom.konekom.in.feederfox.model;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Politician {
    private String Name,Image_URL;
    private int id;
    private JSONArray PoliticalComment;
    public Politician(int id,String Name, String Image_URL, JSONArray PoliticalComment){
        this.Name = Name;
        this.PoliticalComment = PoliticalComment;
        this.Image_URL = Image_URL;
        this.id = id;

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
}
