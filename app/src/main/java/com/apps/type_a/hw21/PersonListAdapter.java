package com.apps.type_a.hw21;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.type_a.hw21.helpers.Person;

import java.util.ArrayList;

/**
 * Created by Type_A on 23.08.2016.
 */
public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.ViewHolder> {
    Context context;
    ArrayList<Person> persons;

    public PersonListAdapter(ArrayList<Person> persons, Context context) {
        this.persons = persons;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(persons.get(position).getName());
        holder.surname.setText(persons.get(position).getSurname());
        holder.age.setText(persons.get(position).getAge());
    }

    @Override
    public int getItemCount() {
        return persons == null ? 0 : persons.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, surname, age;

        public ViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View itemView) {
            name = (TextView) itemView.findViewById(R.id.personName);
            surname = (TextView) itemView.findViewById(R.id.personSurname);
            age = (TextView) itemView.findViewById(R.id.personAge);
        }
    }
}
