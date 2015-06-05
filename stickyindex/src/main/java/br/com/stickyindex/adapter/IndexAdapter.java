package br.com.stickyindex.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.stickyindex.R;

/**
 * Created by edgar on 6/4/15.
 */
public class IndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private char[] dataSet;

    // CONSTRUCTOR _________________________________________________________________________________
    public IndexAdapter (char[] data) {
        this.dataSet = data;
    }

    // CALLBACKS ___________________________________________________________________________________
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.sticky_row_details, parent, false);

        return new IndexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        IndexViewHolder viewHolder = (IndexViewHolder) holder;

        String curr = String.valueOf(dataSet[position]);

        viewHolder.index.setText(curr);

        if (isHeader(position)) {
            viewHolder.index.setVisibility(TextView.VISIBLE);
        } else {
            viewHolder.index.setVisibility(TextView.INVISIBLE);
        }
    }


    // UTIL ________________________________________________________________________________________
    private Boolean isHeader (int pos) {
        if (pos == 0) {
            return Boolean.TRUE;
        } else if (isSameChar(dataSet[pos-1], dataSet[pos])) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    private Boolean isSameChar (char prev, char curr) {
        if (Character.toLowerCase(prev) == Character.toLowerCase(curr)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    // GETTERS AND SETTERS _________________________________________________________________________
    @Override
    public int getItemCount() {
        return dataSet.length;
    }

    public void setDataSet(char[] dataSet) {
        this.dataSet = dataSet;
    }

    // ViewHolder class ____________________________________________________________________________
    public class IndexViewHolder extends RecyclerView.ViewHolder {
        TextView index;

        public IndexViewHolder (View v) {
            super (v);
            index = (TextView) v.findViewById(R.id.sticky_row_index);
        }
    }
}
