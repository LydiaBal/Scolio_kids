package ntua.hci.scolio_kids.main;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ntua.hci.scolio_kids.R;


public class CustomListViewAdapterTwo extends ArrayAdapter<String> {
    Context context;

    public CustomListViewAdapterTwo(Context context, int resourceId, List<String> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
//        TextView txtTitle;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        String rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_img, null);
            holder = new ViewHolder();
//            holder.txtTitle = (TextView) convertView.findViewById(R.id.label);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon_file);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

//        holder.txtTitle.setText(rowItem);
//        holder.imageView.setImageResource(Integer.parseInt(rowItem.getPhoto()));

        if (rowItem.isEmpty()) {
//            iview.setImageResource(R.drawable.placeholder);
        } else{

        Picasso.with(context).load(rowItem).into(holder.imageView);
        }
        return convertView;
    }
}


