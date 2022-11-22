package com.example.tournamentScoring;

public class ReadWriteTournamentDetails{
    public String name, category, mobile, tournamentName, date, tournamentDesc;

    public ReadWriteTournamentDetails(){

    };


    public ReadWriteTournamentDetails(String category, String name, String mobile, String tournamentName, String tournamentDesc, String date) {
        this.name = name;
        this.date = date;
        this.tournamentDesc = tournamentDesc;
        this.category= category;
        this.mobile = mobile;
        this.tournamentName = tournamentName;
    }
}


