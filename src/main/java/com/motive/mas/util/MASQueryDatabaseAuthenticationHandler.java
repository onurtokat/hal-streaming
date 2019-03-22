// 
// Decompiled by Procyon v0.5.30
// 

package com.motive.mas.util;

import java.security.GeneralSecurityException;
import java.util.List;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.Credential;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.springframework.dao.DataAccessException;
import org.jasig.cas.authentication.PreventedException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import javax.validation.constraints.NotNull;
import org.jasig.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;

public class MASQueryDatabaseAuthenticationHandler extends AbstractJdbcUsernamePasswordAuthenticationHandler
{
    @NotNull
    private String sql;
    
    protected final HandlerResult authenticateUsernamePasswordInternal(final UsernamePasswordCredential credential) throws GeneralSecurityException, PreventedException {
        final String username = credential.getUsername();
        final String dbPassword = (String)this.getJdbcTemplate().queryForObject(this.sql, (Class)String.class, new Object[] { username });
        boolean check = false;
        try {
            check = HashedPassword.check(credential.getPassword(), dbPassword);
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if (!check) {
                throw new FailedLoginException("Password does not match value on record.");
            }
        }
        catch (IncorrectResultSizeDataAccessException e2) {
            if (e2.getActualSize() == 0) {
                throw new AccountNotFoundException(username + " not found with SQL query");
            }
            throw new FailedLoginException("Multiple records found for " + username);
        }
        catch (DataAccessException e3) {
            throw new PreventedException("SQL exception while executing query for " + username, (Throwable)e3);
        }
        return this.createHandlerResult((Credential)credential, (Principal)new SimplePrincipal(username), (List)null);
    }
    
    public void setSql(final String sql) {
        this.sql = sql;
    }
}
