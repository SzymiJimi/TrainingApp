package com.example.rekas.tainingapplication.service;

import com.j256.ormlite.stmt.query.In;

/**
 * Created by rekas on 24.06.2018.
 */

public class CustomDateManager {


    public String dayOfWeekToString(Integer dayOfWeek){

        switch (dayOfWeek){
            case 1:{
                return "NIEDZIELA";

            }
            case 2:{
                return "PONIEDZIAŁEK";

            }
            case 3:{
                return "WTOREK";

            }
            case 4:{
                return "ŚRODA";

            }
            case 5:{
                return "CZWARTEK";

            }
            case 6:{
                return "PIĄTEK";

            }
            case 7:{
                return "SOBOTA";

            }

        }
            return "";
    }

}
