package models;

import android.content.Context;

public class User {

    public String firster, laster, gender;
    public long id;

    public User(String first, String last, Long id, String gender){


        this.firster = first;
        this.laster = last;
        this.gender = gender;
        this.id = id;

    }


    //// getters ////

    public String getFirst(){
        return this.firster;
    }

    public String getLast() {
        return this.laster;
    }

    public String getGender() {
        return this.gender;
    }

    public long getId(){
        return this.id;
    }

    //// setters ////

    //public void setFirst(String value){
     //   this.first = value;
    //}

    //public void setLast(String value){
     //   this.last = value;
   // }



    public String toString(){
        return "First name: "+ firster + ". \nLast name: "+ laster + ".\nGender: "+ gender +".";
    }

}