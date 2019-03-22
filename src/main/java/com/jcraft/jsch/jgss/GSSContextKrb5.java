// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch.jgss;

import org.ietf.jgss.MessageProp;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import com.jcraft.jsch.JSchException;
import java.net.UnknownHostException;
import java.net.InetAddress;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.Oid;
import com.jcraft.jsch.GSSContext;

public class GSSContextKrb5 implements GSSContext
{
    private static final String pUseSubjectCredsOnly = "javax.security.auth.useSubjectCredsOnly";
    private static String useSubjectCredsOnly;
    private org.ietf.jgss.GSSContext context;
    
    public GSSContextKrb5() {
        this.context = null;
    }
    
    public void create(final String s, final String s2) throws JSchException {
        try {
            final Oid oid = new Oid("1.2.840.113554.1.2.2");
            final Oid oid2 = new Oid("1.2.840.113554.1.2.2.1");
            final GSSManager \u0131nstance = GSSManager.getInstance();
            final GSSCredential gssCredential = null;
            String canonicalHostName = s2;
            try {
                canonicalHostName = InetAddress.getByName(canonicalHostName).getCanonicalHostName();
            }
            catch (UnknownHostException ex2) {}
            (this.context = \u0131nstance.createContext(\u0131nstance.createName("host/" + canonicalHostName, oid2), oid, gssCredential, 0)).requestMutualAuth(true);
            this.context.requestConf(true);
            this.context.requestInteg(true);
            this.context.requestCredDeleg(true);
            this.context.requestAnonymity(false);
        }
        catch (GSSException ex) {
            throw new JSchException(ex.toString());
        }
    }
    
    public boolean isEstablished() {
        return this.context.isEstablished();
    }
    
    public byte[] init(final byte[] array, final int n, final int n2) throws JSchException {
        try {
            if (GSSContextKrb5.useSubjectCredsOnly == null) {
                setSystemProperty("javax.security.auth.useSubjectCredsOnly", "false");
            }
            return this.context.initSecContext(array, 0, n2);
        }
        catch (GSSException ex) {
            throw new JSchException(ex.toString());
        }
        catch (SecurityException ex2) {
            throw new JSchException(ex2.toString());
        }
        finally {
            if (GSSContextKrb5.useSubjectCredsOnly == null) {
                setSystemProperty("javax.security.auth.useSubjectCredsOnly", "true");
            }
        }
    }
    
    public byte[] getMIC(final byte[] array, final int n, final int n2) {
        try {
            return this.context.getMIC(array, n, n2, new MessageProp(0, true));
        }
        catch (GSSException ex) {
            return null;
        }
    }
    
    public void dispose() {
        try {
            this.context.dispose();
        }
        catch (GSSException ex) {}
    }
    
    private static String getSystemProperty(final String s) {
        try {
            return System.getProperty(s);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    private static void setSystemProperty(final String s, final String s2) {
        try {
            System.setProperty(s, s2);
        }
        catch (Exception ex) {}
    }
    
    static {
        GSSContextKrb5.useSubjectCredsOnly = getSystemProperty("javax.security.auth.useSubjectCredsOnly");
    }
}
