package com.lugeek.dialogtest;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Aidan Follestad (afollestad)
 */
class SimpleListAdapter extends RecyclerView.Adapter<SimpleListAdapter.DefaultVH> {

    public interface InternalListCallback {
        boolean onItemSelected(SimpleListDialog dialog, View itemView, int position, CharSequence text, boolean longPress);
    }

    private final SimpleListDialog dialog;
    @LayoutRes
    private final int layout;
    private InternalListCallback callback;

    public SimpleListAdapter(SimpleListDialog dialog, @LayoutRes int layout) {
        this.dialog = dialog;
        this.layout = layout;
    }

    public void setCallback(InternalListCallback callback) {
        this.callback = callback;
    }

    @Override
    public DefaultVH onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);
        return new DefaultVH(view, this);
    }

    @Override
    public void onBindViewHolder(DefaultVH holder, int index) {
        final View view = holder.itemView;

        holder.title.setText(dialog.mBuilder.items.get(index));

    }

    @Override
    public int getItemCount() {
        return dialog.mBuilder.items != null ? dialog.mBuilder.items.size() : 0;
    }

    public static class DefaultVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView title;
        final SimpleListAdapter adapter;

        public DefaultVH(View itemView, SimpleListAdapter adapter) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.md_title);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (adapter.callback != null) {
                CharSequence text = null;
                if (adapter.dialog.mBuilder.items != null && getAdapterPosition() < adapter.dialog.mBuilder.items.size())
                    text = adapter.dialog.mBuilder.items.get(getAdapterPosition());
                adapter.callback.onItemSelected(adapter.dialog, view, getAdapterPosition(), text, false);
            }
        }
    }
}