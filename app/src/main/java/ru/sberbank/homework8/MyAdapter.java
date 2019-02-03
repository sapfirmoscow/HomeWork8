package ru.sberbank.homework8;


import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.sberbank.homework8.model.Note;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Note> mListData;

    public MyAdapter() {
        mListData = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Note note = mListData.get(i);
        myViewHolder.title.setText(note.getTitle());
        myViewHolder.text.setText(note.getText());
        myViewHolder.cardView.setBackgroundColor(note.getColor());
        myViewHolder.date.setText(note.getCreationDate());

    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public void setNotes(List<Note> notes) {
        mListData.clear();
        mListData.addAll(notes);
        notifyDataSetChanged();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView title;
        private TextView text;
        private TextView date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            text = itemView.findViewById(R.id.text);
            date = itemView.findViewById(R.id.date);
            title = itemView.findViewById(R.id.title);
        }
    }
}