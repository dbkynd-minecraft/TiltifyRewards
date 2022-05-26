package com.dbkynd.tiltifyrewards.http;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class CampaignResponse {
    public final TiltifyCampaign data;

    public CampaignResponse(@JsonProperty("meta") Map<String,Object> meta,
                            @JsonProperty("data") Map<String,Object> data) {


        this.data = new TiltifyCampaign(data);
    }
}
