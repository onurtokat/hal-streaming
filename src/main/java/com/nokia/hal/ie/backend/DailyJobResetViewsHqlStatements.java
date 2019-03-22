// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.ie.backend;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class DailyJobResetViewsHqlStatements
{
    public static void main(final String[] args) throws IOException {
        final String hdfsUrl = args[0];
        final String appFileFolder = args[1];
        final String viewsHqlFile = "updateViews.hql";
        DailyJobNewInsightsProcessingHandler.saveToHdfs(appFileFolder + "/hqls/" + "updateViews.hql", new ArrayList<String>());
    }
}
