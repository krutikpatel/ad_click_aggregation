package com.jsn.adevent.reducer.model;

public class AdClickCount {
    long adId;
    long count;
    long timestamp;

    public AdClickCount() {
    }
    public AdClickCount(long adId, long count, long timestamp) {
        this.adId = adId;
        this.count = count;
        this.timestamp = timestamp;
    }

    public long getAdId() {
        return this.adId;
    }

    public void setAdId(long adId) {
        this.adId = adId;
    }

    public long getCount() {
        return this.count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
