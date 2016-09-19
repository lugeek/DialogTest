package com.lugeek.dialogtest;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by lujiaming on 16/9/20.
 */

public class SimpleListDialog extends Dialog implements View.OnClickListener, SimpleListAdapter.InternalListCallback{

    protected final Builder mBuilder;
    protected View mContainer;

    protected TextView mTvTitle;
    protected RecyclerView mRvContent;
    protected TextView mTvPositive;
    protected TextView mTvNegative;

    public SimpleListDialog(Builder builder) {
        super(builder.context, android.R.style.Theme_Material_Light_Dialog_Alert);
        mBuilder = builder;
        final LayoutInflater inflater = LayoutInflater.from(builder.context);
        mContainer = inflater.inflate(R.layout.simple_list_dialog, null);
        initView();
    }

    private void initView() {
        mTvTitle = (TextView) mContainer.findViewById(R.id.tv_title);
        mRvContent = (RecyclerView) mContainer.findViewById(R.id.recyclerview_content);
        mTvPositive = (TextView) mContainer.findViewById(R.id.tv_positive);
        mTvNegative = (TextView) mContainer.findViewById(R.id.tv_negative);

        if (mTvTitle != null) {
//            mTvTitle.setTextColor(mBuilder.titleColor);
            if (mBuilder.title != null) {
                mTvTitle.setText(mBuilder.title);
                mTvTitle.setVisibility(View.VISIBLE);
            } else {
                mTvTitle.setVisibility(View.GONE);
            }
        }

        if (mRvContent != null && mBuilder.items != null && !mBuilder.items.isEmpty()) {
            mBuilder.adapter = new SimpleListAdapter(this, R.layout.simple_list_item);
            mRvContent.setLayoutManager(new LinearLayoutManager(mBuilder.context));
            mRvContent.setAdapter(mBuilder.adapter);
            ((SimpleListAdapter)mBuilder.adapter).setCallback(this);
        }

        setContentView(mContainer);
    }

    @Override
    public boolean onItemSelected(SimpleListDialog dialog, View itemView, int position, CharSequence text, boolean longPress) {
        dismiss();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_positive) {
            dismiss();
        } else if (v.getId() == R.id.tv_negative) {
            dismiss();
        }
    }

    public static class Builder {
        protected final Context context;
        protected CharSequence title;
        protected int titleColor = -1;
        protected boolean titleColorSet = false;
        protected ArrayList<CharSequence> items;
        protected ListCallback listCallback;
        protected RecyclerView.Adapter<?> adapter;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder title(@StringRes int titleRes) {
            title(this.context.getText(titleRes));
            return this;
        }

        public Builder title(@NonNull CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder titleColor(@ColorInt int color) {
            this.titleColor = color;
            this.titleColorSet = true;
            return this;
        }

        public Builder titleColorRes(@ColorRes int colorRes) {
            return titleColor(ContextCompat.getColor(context, colorRes));
        }

        public Builder items(@NonNull Collection collection) {
            if (collection.size() > 0) {
                final String[] array = new String[collection.size()];
                int i = 0;
                for (Object obj : collection) {
                    array[i] = obj.toString();
                    i++;
                }
                items(array);
            }
            return this;
        }

        public Builder items(@ArrayRes int itemsRes) {
            items(this.context.getResources().getTextArray(itemsRes));
            return this;
        }

        public Builder items(@NonNull CharSequence... items) {
            this.items = new ArrayList<>();
            Collections.addAll(this.items, items);
            return this;
        }

        public Builder itemsCallback(@NonNull ListCallback callback) {
            this.listCallback = callback;
            return this;
        }



        @UiThread
        public SimpleListDialog build() {
            return new SimpleListDialog(this);
        }

        @UiThread
        public SimpleListDialog show() {
            SimpleListDialog dialog = build();
            dialog.show();
            return dialog;
        }
    }

    public interface ListCallback {
        void onSelection(SimpleListDialog dialog, View itemView, int position, CharSequence text);
    }

}
