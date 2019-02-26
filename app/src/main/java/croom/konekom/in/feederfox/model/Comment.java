package croom.konekom.in.feederfox.model;

public class Comment {
    private String user,comment;
    public Comment(String user,String comment){
        this.user= user;
        this.comment=comment;
    }

    public String getComment() {
        return comment;
    }

    public String getUser() {
        return user;
    }
}
