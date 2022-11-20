package com.example.tournamentScoring;

public class ReadWriteTournamentDetails{
    public String category, mobile, tournamentName, date, tournamentDesc;

    public ReadWriteTournamentDetails(){

    };


    public ReadWriteTournamentDetails(String category, String mobile, String tournamentName, String tournamentDesc, String date) {
        this.date = date;
        this.tournamentDesc = tournamentDesc;
        this.category= category;
        this.mobile = mobile;
        this.tournamentName = tournamentName;
    }
}


