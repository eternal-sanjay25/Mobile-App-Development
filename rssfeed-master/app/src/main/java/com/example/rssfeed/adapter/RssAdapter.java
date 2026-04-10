package com.example.rssfeed.adapter;

import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rssfeed.R;
import com.example.rssfeed.model.RssItem;
import java.util.List;

public class RssAdapter extends RecyclerView.Adapter<RssAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(RssItem item);
    }

    private List<RssItem> items;
    private final OnItemClickListener listener;

    public RssAdapter(List<RssItem> items, OnItemClickListener listener) {
        this.items    = items;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate, tvDescription;

        public ViewHolder(View view) {
            super(view);
            tvTitle       = view.findViewById(R.id.tvTitle);
            tvDate        = view.findViewById(R.id.tvDate);
            tvDescription = view.findViewById(R.id.tvDescription);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rss, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RssItem item = items.get(position);

        holder.tvTitle.setText(item.getTitle());
        holder.tvDate.setText(item.getPubDate());

        String rawDesc = item.getDescription();
        String plainDesc;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            plainDesc = Html.fromHtml(rawDesc, Html.FROM_HTML_MODE_COMPACT).toString();
        } else {
            plainDesc = Html.fromHtml(rawDesc).toString();
        }

        if (plainDesc.length() > 150) {
            plainDesc = plainDesc.substring(0, 150) + "…";
        }
        holder.tvDescription.setText(plainDesc);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void updateItems(List<RssItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }
}