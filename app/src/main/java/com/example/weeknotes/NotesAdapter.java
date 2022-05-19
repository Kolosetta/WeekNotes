package com.example.weeknotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private ArrayList<Note> notes;
    private OnNoteClickListener onNoteClickListener;
    public NotesAdapter(ArrayList<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false); //Создаем View из xml файла Item и присваеваем во view
        return new NotesViewHolder(view); //Возвращаем ViewHolder, созданный на основе макета note_item
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder viewHolder, int position) {
        Note note = notes.get(position);
        viewHolder.title.setText(note.getTitle());
        viewHolder.description.setText(note.getDescription());
        viewHolder.dayOfWeek.setText(note.getDayOfWeek().getTitle());

        int colorId;
        switch (note.getPriority()){
            case HIGH:
                colorId = viewHolder.itemView.getResources().getColor(android.R.color.holo_red_dark);
                break;
            case MEDIUM:
                colorId = viewHolder.itemView.getResources().getColor(android.R.color.holo_orange_dark);
                break;
            default:
                colorId = viewHolder.itemView.getResources().getColor(android.R.color.holo_green_dark);
                break;
        }
        viewHolder.title.setBackgroundColor(colorId);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView description;
        TextView dayOfWeek;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTextView);
            description = itemView.findViewById(R.id.descriptionTextView);
            dayOfWeek = itemView.findViewById(R.id.dayOfWeekTextView);
            itemView.setOnClickListener(view -> {
                    if(onNoteClickListener != null){
                        onNoteClickListener.onNoteCLick(getAdapterPosition());
                    }
            });

            itemView.setOnLongClickListener(view -> {
                if(onNoteClickListener != null){
                    onNoteClickListener.onNoteLongClick(getAdapterPosition());
                }
                return true;
            });


        }
    }

    interface OnNoteClickListener{
        void onNoteCLick(int position);
        void onNoteLongClick(int position);
    }

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

}
