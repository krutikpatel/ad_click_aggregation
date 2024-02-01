package com.jsn.adevent.reducer.reducerworker;

import java.util.HashMap;
import java.util.Map;

public class WindowState {
    private Map<Long, Integer> windowMap = new HashMap<>();
    private long windowOffset = -1;

    public WindowState() {
        windowMap = new HashMap<>();
    }

    public WindowState(WindowState otherWindowState) {
        this.windowMap =  new HashMap<>(otherWindowState.getWindowMap());//make copy
        this.windowOffset = otherWindowState.getWindowOffset();
    }

    public long getWindowOffset() {
        return windowOffset;
    }

    public void setWindowOffset(long windowOffset) {
        this.windowOffset = windowOffset;
    }

    public Map<Long, Integer> getWindowMap() {
        return windowMap;
    }

    public void clearState() {
        windowMap.clear();
        windowOffset = -1;
    }
}
