package com.serhatleventyavas.gezgin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesAdapter extends BaseAdapter {

    private ArrayList<NoteModel> list = null;

    public NotesAdapter() {
        list = new ArrayList<>();
    }

    public void setNotesList(ArrayList<NoteModel> list) {
        if (this.list == null)
            this.list = new ArrayList<>();
        this.list.clear();

        this.list.addAll(list);
        notifyDataSetChanged();
    }

    // liste'nin boyutunu döndürüyordu.
    @Override
    public int getCount() {
        if (this.list == null) return 0;
        return list.size();
    }

    // liste'nin verilen posizyondaki item'i döndürür.
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notes, parent, false);

        NoteModel noteModel = list.get(position);
        TextView txtNote = convertView.findViewById(R.id.item_notes_txtNote);
        txtNote.setText(noteModel.getNote());
        return convertView;
    }
}
