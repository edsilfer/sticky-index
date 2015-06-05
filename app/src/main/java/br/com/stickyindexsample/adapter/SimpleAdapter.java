package br.com.stickyindexsample.adapter;

/**
 * Created by edgar on 6/4/15.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.stikyindexsample.R;

public class SimpleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String [] dataSet;

    // CONSTRUCTOR _________________________________________________________________________________
    public SimpleAdapter(String[] data) {
        this.dataSet = data;
    }

    // CALLBACKS ___________________________________________________________________________________
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_details, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        String content = dataSet[position];

        viewHolder.index.setText(content);
    }

    // GETTERS AND SETTERS _________________________________________________________________________
    @Override
    public int getItemCount() {
        return dataSet.length;
    }

    public String [] getDataSet() {
        return dataSet;
    }

    public void setDataSet(String [] dataSet) {
        this.dataSet = dataSet;
    }

    // ViewHolder class ____________________________________________________________________________
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView index;

        public ViewHolder(View v) {
            super (v);
            index = (TextView) v.findViewById(R.id.content);
        }
    }
}
