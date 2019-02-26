package croom.konekom.in.feederfox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import croom.konekom.in.feederfox.R;
import croom.konekom.in.feederfox.model.Comment;
import croom.konekom.in.feederfox.model.Politician;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter implements View.OnClickListener{

    private ArrayList<Comment> dataSet;
    Context mContext;

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtComment;

    }

    public CommentAdapter(ArrayList<Comment> data, Context context) {
        super();
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Comment dataModel=(Comment) object;


    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Comment dataModel = (Comment) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.userName);
            viewHolder.txtComment = (TextView) convertView.findViewById(R.id.comment);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        viewHolder.txtName.setText(dataModel.getUser());
        viewHolder.txtComment.setText(dataModel.getComment());

        // Return the completed view to render on screen
        return convertView;
    }
}