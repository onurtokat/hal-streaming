// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.action.nest;

import com.alu.motive.ae.client.exception.AEException;
import com.alu.motive.ae.client.action.nest.model.QueryBuilderResult;
import com.alu.motive.ae.client.action.nest.model.QueryBuilderInputData;

public interface AEQueryBuilder
{
    QueryBuilderResult build(final QueryBuilderInputData p0) throws AEException;
}
