// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.serializers;

import java.security.Key;
import java.io.InputStream;
import javax.crypto.CipherInputStream;
import com.esotericsoftware.kryo.io.Input;
import javax.crypto.Cipher;
import java.io.IOException;
import com.esotericsoftware.kryo.KryoException;
import java.io.OutputStream;
import javax.crypto.CipherOutputStream;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.Kryo;
import javax.crypto.spec.SecretKeySpec;
import com.esotericsoftware.kryo.Serializer;

public class BlowfishSerializer extends Serializer
{
    private final Serializer serializer;
    private static SecretKeySpec keySpec;
    
    public BlowfishSerializer(final Serializer serializer, final byte[] key) {
        this.serializer = serializer;
        BlowfishSerializer.keySpec = new SecretKeySpec(key, "Blowfish");
    }
    
    public void write(final Kryo kryo, final Output output, final Object object) {
        final Cipher cipher = getCipher(1);
        final CipherOutputStream cipherStream = new CipherOutputStream(output, cipher);
        final Output cipherOutput = new Output(cipherStream, 256) {
            public void close() throws KryoException {
            }
        };
        kryo.writeObject(cipherOutput, object, this.serializer);
        cipherOutput.flush();
        try {
            cipherStream.close();
        }
        catch (IOException ex) {
            throw new KryoException(ex);
        }
    }
    
    public Object read(final Kryo kryo, final Input input, final Class type) {
        final Cipher cipher = getCipher(2);
        final CipherInputStream cipherInput = new CipherInputStream(input, cipher);
        return kryo.readObject(new Input(cipherInput, 256), (Class<Object>)type, this.serializer);
    }
    
    public Object copy(final Kryo kryo, final Object original) {
        return this.serializer.copy(kryo, original);
    }
    
    private static Cipher getCipher(final int mode) {
        try {
            final Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(mode, BlowfishSerializer.keySpec);
            return cipher;
        }
        catch (Exception ex) {
            throw new KryoException(ex);
        }
    }
}
