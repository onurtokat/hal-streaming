// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.util;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.minlog.Log;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ClassResolver;

public class DefaultClassResolver implements ClassResolver
{
    public static final byte NAME = -1;
    protected Kryo kryo;
    protected final IntMap<Registration> idToRegistration;
    protected final ObjectMap<Class, Registration> classToRegistration;
    protected IdentityObjectIntMap<Class> classToNameId;
    protected IntMap<Class> nameIdToClass;
    protected ObjectMap<String, Class> nameToClass;
    protected int nextNameId;
    private int memoizedClassId;
    private Registration memoizedClassIdValue;
    private Class memoizedClass;
    private Registration memoizedClassValue;
    
    public DefaultClassResolver() {
        this.idToRegistration = new IntMap<Registration>();
        this.classToRegistration = new ObjectMap<Class, Registration>();
        this.memoizedClassId = -1;
    }
    
    public void setKryo(final Kryo kryo) {
        this.kryo = kryo;
    }
    
    public Registration register(final Registration registration) {
        if (registration == null) {
            throw new IllegalArgumentException("registration cannot be null.");
        }
        if (Log.TRACE) {
            if (registration.getId() == -1) {
                Log.trace("kryo", "Register class name: " + Util.className(registration.getType()) + " (" + registration.getSerializer().getClass().getName() + ")");
            }
            else {
                Log.trace("kryo", "Register class ID " + registration.getId() + ": " + Util.className(registration.getType()) + " (" + registration.getSerializer().getClass().getName() + ")");
            }
        }
        this.classToRegistration.put(registration.getType(), registration);
        this.idToRegistration.put(registration.getId(), registration);
        if (registration.getType().isPrimitive()) {
            this.classToRegistration.put(Util.getWrapperClass(registration.getType()), registration);
        }
        return registration;
    }
    
    public Registration registerImplicit(final Class type) {
        return this.register(new Registration(type, this.kryo.getDefaultSerializer(type), -1));
    }
    
    public Registration getRegistration(final Class type) {
        if (type == this.memoizedClass) {
            return this.memoizedClassValue;
        }
        final Registration registration = this.classToRegistration.get(type);
        if (registration != null) {
            this.memoizedClass = type;
            this.memoizedClassValue = registration;
        }
        return registration;
    }
    
    public Registration getRegistration(final int classID) {
        return this.idToRegistration.get(classID);
    }
    
    public Registration writeClass(final Output output, final Class type) {
        if (type == null) {
            if (Log.TRACE || (Log.DEBUG && this.kryo.getDepth() == 1)) {
                Util.log("Write", null);
            }
            output.writeByte((byte)0);
            return null;
        }
        final Registration registration = this.kryo.getRegistration(type);
        if (registration.getId() == -1) {
            this.writeName(output, type, registration);
        }
        else {
            if (Log.TRACE) {
                Log.trace("kryo", "Write class " + registration.getId() + ": " + Util.className(type));
            }
            output.writeInt(registration.getId() + 2, true);
        }
        return registration;
    }
    
    protected void writeName(final Output output, final Class type, final Registration registration) {
        output.writeByte(1);
        if (this.classToNameId != null) {
            final int nameId = this.classToNameId.get(type, -1);
            if (nameId != -1) {
                if (Log.TRACE) {
                    Log.trace("kryo", "Write class name reference " + nameId + ": " + Util.className(type));
                }
                output.writeInt(nameId, true);
                return;
            }
        }
        if (Log.TRACE) {
            Log.trace("kryo", "Write class name: " + Util.className(type));
        }
        final int nameId = this.nextNameId++;
        if (this.classToNameId == null) {
            this.classToNameId = new IdentityObjectIntMap<Class>();
        }
        this.classToNameId.put(type, nameId);
        output.writeInt(nameId, true);
        output.writeString(type.getName());
    }
    
    public Registration readClass(final Input input) {
        final int classID = input.readInt(true);
        switch (classID) {
            case 0: {
                if (Log.TRACE || (Log.DEBUG && this.kryo.getDepth() == 1)) {
                    Util.log("Read", null);
                }
                return null;
            }
            case 1: {
                return this.readName(input);
            }
            default: {
                if (classID == this.memoizedClassId) {
                    return this.memoizedClassIdValue;
                }
                final Registration registration = this.idToRegistration.get(classID - 2);
                if (registration == null) {
                    throw new KryoException("Encountered unregistered class ID: " + (classID - 2));
                }
                if (Log.TRACE) {
                    Log.trace("kryo", "Read class " + (classID - 2) + ": " + Util.className(registration.getType()));
                }
                this.memoizedClassId = classID;
                return this.memoizedClassIdValue = registration;
            }
        }
    }
    
    protected Registration readName(final Input input) {
        final int nameId = input.readInt(true);
        if (this.nameIdToClass == null) {
            this.nameIdToClass = new IntMap<Class>();
        }
        Class type = this.nameIdToClass.get(nameId);
        if (type == null) {
            final String className = input.readString();
            if (this.nameToClass != null) {
                type = this.nameToClass.get(className);
            }
            if (type == null) {
                try {
                    type = Class.forName(className, false, this.kryo.getClassLoader());
                }
                catch (ClassNotFoundException ex) {
                    throw new KryoException("Unable to find class: " + className, ex);
                }
                if (this.nameToClass == null) {
                    this.nameToClass = new ObjectMap<String, Class>();
                }
                this.nameToClass.put(className, type);
            }
            this.nameIdToClass.put(nameId, type);
            if (Log.TRACE) {
                Log.trace("kryo", "Read class name: " + className);
            }
        }
        else if (Log.TRACE) {
            Log.trace("kryo", "Read class name reference " + nameId + ": " + Util.className(type));
        }
        return this.kryo.getRegistration(type);
    }
    
    public void reset() {
        if (!this.kryo.isRegistrationRequired()) {
            if (this.classToNameId != null) {
                this.classToNameId.clear();
            }
            if (this.nameIdToClass != null) {
                this.nameIdToClass.clear();
            }
            this.nextNameId = 0;
        }
    }
}
