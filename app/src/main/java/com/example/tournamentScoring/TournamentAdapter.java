package com.example.tournamentScoring;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.ViewHolder> {

    public static boolean position;
    private List<TournamentModel> tournamentList;
    public TournamentAdapter (List<TournamentModel> tournamentList) {
    this.tournamentList = tournamentList;
    }

    @NonNull
    @Override
    public TournamentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TournamentAdapter.ViewHolder holder, int position) {

        int progress = tournamentList.get(position).topScore();
        holder.setData(position, progress);
    }

    @Override
    public int getItemCount() {

        return tournamentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tournamentName;
        private TextView topScore;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            tournamentName = itemView.findViewById(R.id.tournamentName);
            topScore = itemView.findViewById(R.id.score);
            progressBar = itemView.findViewById(R.id.progressBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(),TournamentRegistration.class);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        private void setData(int pos, int progress){
            tournamentName.setText("Tournament Name: " + String.valueOf(pos + 1));
            topScore.setText(String.valueOf(progress) + "%");

            progressBar.setProgress(progress);
        }
    }
}