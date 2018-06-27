package com.igweze.ebi.journalapp.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.igweze.ebi.journalapp.R;
import com.igweze.ebi.journalapp.ui.DetailsActivity;
import com.igweze.ebi.journalapp.ui.model.Writeup;

import java.text.SimpleDateFormat;
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

            writeupTime.setText(Writeup.dateFormat.format(writeup.getTime()));
            writeupText.setText(writeup.getText());

            View parent = (View) writeupTime.getParent();
            parent.setOnClickListener(v -> {
                Context ctx = v.getContext();
                Intent intent = new Intent(ctx, DetailsActivity.class);
                intent.putExtra(Writeup.ITEM_ID, writeup.getId());
                intent.putExtra(Writeup.ITEM_TEXT, writeup.getText());
                intent.putExtra(Writeup.ITEM_TIME, writeup.getTime());
                ctx.startActivity(intent);
            });
        }
    }
}
