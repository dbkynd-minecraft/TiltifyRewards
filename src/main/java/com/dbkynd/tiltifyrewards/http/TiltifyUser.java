package com.dbkynd.tiltifyrewards.http;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class TiltifyUser {
    public final int id;
    public final String username;

    public TiltifyUser(@JsonProperty("meta") Map<String,Object> meta,
                       @JsonProperty("data") Map<String,Object> data) {
        this.id = (int)data.get("id");
        this.username = (String)data.get("username");
    }
}
