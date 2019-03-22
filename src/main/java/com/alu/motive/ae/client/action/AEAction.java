// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.action;

import com.alu.motive.ae.client.exception.AEException;
import com.alu.motive.ae.client.AEWorkflowPart;

public interface AEAction
{
    AEWorkflowPart buildResult(final AEActionInputData p0) throws AEException;
}
