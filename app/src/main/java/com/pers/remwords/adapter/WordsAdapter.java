package com.pers.remwords.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pers.remwords.R;
import com.pers.remwords.entity.Words;

import java.util.List;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ViewHolder> {

    private List<Words> wordsList;
    private Context mContext;

    static public class ViewHolder extends RecyclerView.ViewHolder {
        TextView word;
        TextView means;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            word = (TextView) itemView.findViewById(R.id.word);
            means = (TextView) itemView.findViewById(R.id.means);
        }
    }

    public WordsAdapter(List<Words> wordsList, Context mContext) {
        this.wordsList = wordsList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.words_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Words words = wordsList.get(position);
        holder.word.setText(words.getWord());
        holder.means.setText(words.getMeans());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onClick(position,words);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null)
                    Log.d("TAG", "onLongClick: "+words.getWord());
                    onItemLongClickListener.onLongClick(holder.itemView,position,words);
                //返回false会在长安结束后继续点击
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return wordsList.size();
    }

    public interface OnItemClickListener {
        void onClick(int position,Words words);
    }

    OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemLongClickListener {
        void onLongClick(View view,int position,Words words);
    }

    OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }


}
