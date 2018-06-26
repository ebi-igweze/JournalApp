package com.igweze.ebi.journalapp.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.igweze.ebi.journalapp.R;
import com.igweze.ebi.journalapp.ui.model.Writeup;

import java.util.List;

public class WriteupAdapter extends RecyclerView.Adapter<WriteupAdapter.ViewHolder> {

    private List<Writeup> writeups;

    public WriteupAdapter(List<Writeup> writeups) {
        this.writeups = writeups;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_writeup, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Writeup writeup = writeups.get(position);
        holder.bind(writeup);
    }

    @Override
    public int getItemCount() {
        return writeups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView writeupTime;
        private TextView writeupText;

        public ViewHolder(View view) {
            super(view);
            writeupTime = view.findViewById(R.id.tvWriteupTime);
            writeupText = view.findViewById(R.id.tvWriteupText);
        }

        public void bind(Writeup writeup) {
            writeupTime.setText(writeup.time);
            writeupText.setText(writeup.text);
        }
    }
}
