package ntua.hci.scolio_kids.main;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.List;

import ntua.hci.scolio_kids.R;

public class CustomListViewAdapterPM extends ArrayAdapter<MemberFolder> {
    Context context;

    public CustomListViewAdapterPM(Context context, int resourceId, List<MemberFolder> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        CardView cardView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        CustomListViewAdapterPM.ViewHolder holder = null;
        MemberFolder rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new CustomListViewAdapterPM.ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.label);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon_file);
            holder.cardView = (CardView) convertView.findViewById(R.id.dot);
            convertView.setTag(holder);
        } else
            holder = (CustomListViewAdapterPM.ViewHolder) convertView.getTag();

        holder.txtTitle.setText(rowItem.getNameList());
        holder.cardView.setVisibility(rowItem.getIsVisib());
        //holder.imageView.setImageResource(Integer.parseInt(rowItem.getImageurl()));

        return convertView;
    }
}