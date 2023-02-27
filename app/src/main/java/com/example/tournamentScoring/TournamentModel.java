package com.example.tournamentScoring;

public class TournamentModel {

    private String tournamentID;
    private int topScore;

    public TournamentModel(String tournamentID,Integer topScore){
        this.tournamentID = tournamentID;
        this.topScore = topScore;
    }

    public String getTournamentID(){
        return tournamentID;
    }

    public void setTournamentID(String tournamentID){

        this.tournamentID = tournamentID;
    }

    public int topScore(){

        return topScore;
    }

    public void setName(String name){
        this.topScore = topScore;
    }
}

