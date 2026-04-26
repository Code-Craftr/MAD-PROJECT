package com.example.qmanageapplication.network.responses;

import com.example.qmanageapplication.models.Outlet;
import java.util.List;

public class OutletResponse {
    private boolean success;
    private List<Outlet> outlets;

    public boolean isSuccess() { return success; }
    public List<Outlet> getOutlets() { return outlets; }
}
