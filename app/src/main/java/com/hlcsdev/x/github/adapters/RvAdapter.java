package com.hlcsdev.x.github.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hlcsdev.x.github.R;
import com.hlcsdev.x.github.models.repos.Repos;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    private List<Repos> repos;
    Context context;

    public RvAdapter(List<Repos> repos, Context context) {
        this.repos = repos;
        this.context = context;
    }

    public interface ItemClickCallback {
        void onItemClick(String login, String name);
    }

    private ItemClickCallback itemClickCallback;

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int pos = holder.getAdapterPosition();

        // Установка текста
        holder.name.setText(repos.get(pos).getName());
        holder.author.setText("Автор: " + repos.get(pos).getOwner().getLogin());
        holder.forks.setText("Forks: " + repos.get(pos).getForks());
        holder.watches.setText("Watches: " + repos.get(pos).getWatchers());
        holder.description.setText(repos.get(pos).getDescription());


        // Картинка
        String url = repos.get(pos).getOwner().getAvatarUrl();
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.load)
                .error(R.drawable.load)
                .into(holder.imageView);

        // Обработка клика
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemClickCallback.onItemClick(repos.get(pos).getOwner().getLogin(), repos.get(pos).getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView author;
        TextView forks;
        TextView watches;
        TextView description;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            author = (TextView) itemView.findViewById(R.id.author);
            forks = (TextView) itemView.findViewById(R.id.forks);
            watches = (TextView) itemView.findViewById(R.id.watches);
            description = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

}
