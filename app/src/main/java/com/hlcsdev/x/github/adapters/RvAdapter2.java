package com.hlcsdev.x.github.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hlcsdev.x.github.R;
import com.hlcsdev.x.github.models.commits.Commits;

import java.util.List;


public class RvAdapter2 extends RecyclerView.Adapter<RvAdapter2.ViewHolder> {

    private List<Commits> commits;

    public RvAdapter2(List<Commits> commits) {
        this.commits = commits;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int pos = holder.getAdapterPosition();

        // Установка текста
        holder.message.setText(commits.get(pos).getCommit().getMessage());
        holder.author.setText("Автор: " + commits.get(pos).getCommit().getAuthor().getName());
        holder.date.setText(commits.get(pos).getCommit().getAuthor().getDate());
        holder.hash.setText(commits.get(pos).getSha());
    }

    @Override
    public int getItemCount() {
        return commits.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView message;
        TextView author;
        TextView date;
        TextView hash;

        ViewHolder(View itemView) {
            super(itemView);

            message = (TextView) itemView.findViewById(R.id.message);
            author = (TextView) itemView.findViewById(R.id.author);
            date = (TextView) itemView.findViewById(R.id.date);
            hash = (TextView) itemView.findViewById(R.id.hash);
        }
    }

}
