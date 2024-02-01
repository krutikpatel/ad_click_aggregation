package com.jsn.adevent.mapper.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AdClickEvent {
    long adId;

    //timestamp in minutes. time since epoch in minutes
    long timestamp;

    public AdClickEvent() {
    }

    public AdClickEvent(long adId, long timestamp) {
        this.adId = adId;
        this.timestamp = timestamp;
    }

    public long getAdId() {
        return this.adId;
    }

    public void setAdId(long adId) {
        this.adId = adId;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
