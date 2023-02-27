package com.example.tournamentScoring;

public class CategoryModel {
    private String docID;
    private String name;
    private int noOfTournament;

    public CategoryModel(String docID, String name, int noOfTournament){
        this.docID = docID;
        this.name = name;
        this.noOfTournament = noOfTournament;
    }

    public String getDocID(){
        return docID;
    }

    public void setDocID(String docID){

        this.docID = docID;
    }

    public String getName(){

        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getNoOfTournament(){

        return noOfTournament;
    }

    public void setNoOfTournament(int noOfTournament){
        this.noOfTournament = noOfTournament;
    }
}
