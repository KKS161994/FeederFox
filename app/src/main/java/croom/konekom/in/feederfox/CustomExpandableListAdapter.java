package croom.konekom.in.feederfox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import croom.konekom.in.feederfox.model.Comment;
import croom.konekom.in.feederfox.model.Politician;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    private ArrayList<Politician> politicianArrayList;

    public CustomExpandableListAdapter(Context context,ArrayList<Politician> politicians) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    this.politicianArrayList=politicians;
    }
    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.politicianArrayList.get(listPosition).getPoliticalComment()
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Comment expandedListText = (Comment) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView userNameView = (TextView) convertView
                .findViewById(R.id.userName);
        TextView commentView = (TextView) convertView
                .findViewById(R.id.comment);
        userNameView.setText(expandedListText.getUser());
        commentView.setText(expandedListText.getComment());
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.politicianArrayList.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.politicianArrayList.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        ImageView listTitleImageView = (ImageView) convertView
                .findViewById(R.id.listImage);
        listTitleTextView.setTypeface(null, Typeface.BOLD);

        Politician politician = politicianArrayList.get(listPosition);
        listTitleTextView.setText(politician.getName());
        Picasso.get().load(politician.getImage_URL()).into(listTitleImageView);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}