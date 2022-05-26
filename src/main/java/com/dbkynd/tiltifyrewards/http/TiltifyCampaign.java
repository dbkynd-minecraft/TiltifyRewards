package com.dbkynd.tiltifyrewards.http;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class TiltifyCampaign {
    public final int id;
    public final String name;
    public final String username;

    public TiltifyCampaign(@JsonProperty("data") Map<String,Object> data) {

        Map<String,String> user = (Map<String,String>)data.get("user");

        this.id = (int)data.get("id");
        this.name = (String)data.get("name");
        this.username = user.get("username");
    }
}
