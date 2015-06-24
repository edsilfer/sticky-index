package br.com.stickyindexsample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.stickyindexsample.R;

/**
 * Created by edgar on 6/8/15.
 */
public class ContactsDtlsAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    // Constructor _________________________________________________________________________________
    public ContactsDtlsAdapter (Context context) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    // GETTERS AND SETTERS _________________________________________________________________________
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {
            vi = inflater.inflate(R.layout.contact_dtls_row, null);
            holder = new ViewHolder(vi);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        switch (position) {
            case 0:
                holder.icon.setImageResource(R.drawable.camera_icon);
                holder.content.setText("usrnickname");
                break;
            case 1:
                holder.icon.setImageResource(R.drawable.home_number_icon);
                holder.content.setText("username@email.com");
                break;
            case 2:
                holder.icon.setImageResource(R.drawable.mobile_number_icon);
                holder.content.setText("(+55) 61 7849 - 2136");
                break;
            case 3:
                holder.icon.setImageResource(R.drawable.home_number_icon);
                holder.content.setText("(+55) 12 3541 - 6589");
                break;
        }

        return vi;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // View Holder class
    private class ViewHolder {
        private ImageView icon;
        private TextView content;

        public ViewHolder (View v) {
            icon = (ImageView) v.findViewById(R.id.dtls_type);
            content = (TextView) v.findViewById(R.id.dtls_content);
        }
    }
}
