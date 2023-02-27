package com.example.tournamentScoring;

public class ReadWriteTournamentDetails{
    public String tournamentName, category, date, mobile, tournamentDesc;

    public ReadWriteTournamentDetails(){

    };




    public ReadWriteTournamentDetails(String tournamentName, String category, String date, String mobile, String tournamentDesc) {
        this.tournamentName = tournamentName;
        this.category= category;
        this.date = date;
        this.mobile = mobile;
        this.tournamentDesc = tournamentDesc;

    }
}


