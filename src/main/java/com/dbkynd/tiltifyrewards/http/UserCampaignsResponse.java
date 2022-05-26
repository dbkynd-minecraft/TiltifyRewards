package com.dbkynd.tiltifyrewards.http;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class UserCampaignsResponse {
    public final TiltifyCampaign data;

    public UserCampaignsResponse(@JsonProperty("meta") Map<String,Object> meta,
                                 @JsonProperty("data") List<Map<String,Object>> data) {

        // Get the most recent started at campaign
        data.sort((o1, o2) -> {
            long a = (long)o1.get("startsAt");
            long b = (long)o2.get("startsAt");
            return Long.compare(b, a);
        });

        this.data = new TiltifyCampaign(data.get(0));
    }
}
