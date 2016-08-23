package com.example.horry.footbasket.entity.football;

/**
 * Created by Administrator on 2016/8/16.
 */
public class Assit {
    public String person_id;
    public String name;
    public String team_id;
    public String team_name;
    public String count;

    public Assit(String person_id, String name, String team_id, String team_name, String count) {
        this.person_id = person_id;
        this.name = name;
        this.team_id = team_id;
        this.team_name = team_name;
        this.count = count;
    }
}
