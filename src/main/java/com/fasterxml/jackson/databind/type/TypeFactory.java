// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.WildcardType;
import java.lang.reflect.GenericArrayType;
import com.fasterxml.jackson.core.type.TypeReference;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Map;
import java.lang.reflect.Type;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.LRUMap;
import com.fasterxml.jackson.databind.JavaType;
import java.io.Serializable;

public final class TypeFactory implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final JavaType[] NO_TYPES;
    protected static final TypeFactory instance;
    protected static final SimpleType CORE_TYPE_STRING;
    protected static final SimpleType CORE_TYPE_BOOL;
    protected static final SimpleType CORE_TYPE_INT;
    protected static final SimpleType CORE_TYPE_LONG;
    protected final LRUMap<ClassKey, JavaType> _typeCache;
    protected transient HierarchicType _cachedHashMapType;
    protected transient HierarchicType _cachedArrayListType;
    protected final TypeModifier[] _modifiers;
    protected final TypeParser _parser;
    
    private TypeFactory() {
        this._typeCache = new LRUMap<ClassKey, JavaType>(16, 100);
        this._parser = new TypeParser(this);
        this._modifiers = null;
    }
    
    protected TypeFactory(final TypeParser p, final TypeModifier[] mods) {
        this._typeCache = new LRUMap<ClassKey, JavaType>(16, 100);
        this._parser = p;
        this._modifiers = mods;
    }
    
    public TypeFactory withModifier(final TypeModifier mod) {
        if (mod == null) {
            return new TypeFactory(this._parser, this._modifiers);
        }
        if (this._modifiers == null) {
            return new TypeFactory(this._parser, new TypeModifier[] { mod });
        }
        return new TypeFactory(this._parser, ArrayBuilders.insertInListNoDup(this._modifiers, mod));
    }
    
    public static TypeFactory defaultInstance() {
        return TypeFactory.instance;
    }
    
    public void clearCache() {
        this._typeCache.clear();
    }
    
    public static JavaType unknownType() {
        return defaultInstance()._unknownType();
    }
    
    public static Class<?> rawClass(final Type t) {
        if (t instanceof Class) {
            return (Class<?>)t;
        }
        return defaultInstance().constructType(t).getRawClass();
    }
    
    public JavaType constructSpecializedType(final JavaType baseType, final Class<?> subclass) {
        if (baseType.getRawClass() == subclass) {
            return baseType;
        }
        if (!(baseType instanceof SimpleType) || (!subclass.isArray() && !Map.class.isAssignableFrom(subclass) && !Collection.class.isAssignableFrom(subclass))) {
            return baseType.narrowBy(subclass);
        }
        if (!baseType.getRawClass().isAssignableFrom(subclass)) {
            throw new IllegalArgumentException("Class " + subclass.getClass().getName() + " not subtype of " + baseType);
        }
        JavaType subtype = this._fromClass(subclass, new TypeBindings(this, baseType.getRawClass()));
        Object h = baseType.getValueHandler();
        if (h != null) {
            subtype = subtype.withValueHandler(h);
        }
        h = baseType.getTypeHandler();
        if (h != null) {
            subtype = subtype.withTypeHandler(h);
        }
        return subtype;
    }
    
    public JavaType constructFromCanonical(final String canonical) throws IllegalArgumentException {
        return this._parser.parse(canonical);
    }
    
    public JavaType[] findTypeParameters(final JavaType type, final Class<?> expType) {
        final Class<?> raw = type.getRawClass();
        if (raw != expType) {
            return this.findTypeParameters(raw, expType, new TypeBindings(this, type));
        }
        final int count = type.containedTypeCount();
        if (count == 0) {
            return null;
        }
        final JavaType[] result = new JavaType[count];
        for (int i = 0; i < count; ++i) {
            result[i] = type.containedType(i);
        }
        return result;
    }
    
    public JavaType[] findTypeParameters(final Class<?> clz, final Class<?> expType) {
        return this.findTypeParameters(clz, expType, new TypeBindings(this, clz));
    }
    
    public JavaType[] findTypeParameters(final Class<?> clz, final Class<?> expType, TypeBindings bindings) {
        final HierarchicType subType = this._findSuperTypeChain(clz, expType);
        if (subType == null) {
            throw new IllegalArgumentException("Class " + clz.getName() + " is not a subtype of " + expType.getName());
        }
        HierarchicType superType = subType;
        while (superType.getSuperType() != null) {
            superType = superType.getSuperType();
            final Class<?> raw = superType.getRawClass();
            final TypeBindings newBindings = new TypeBindings(this, raw);
            if (superType.isGeneric()) {
                final ParameterizedType pt = superType.asGeneric();
                final Type[] actualTypes = pt.getActualTypeArguments();
                final TypeVariable<?>[] vars = raw.getTypeParameters();
                for (int len = actualTypes.length, i = 0; i < len; ++i) {
                    final String name = vars[i].getName();
                    final JavaType type = this._constructType(actualTypes[i], bindings);
                    newBindings.addBinding(name, type);
                }
            }
            bindings = newBindings;
        }
        if (!superType.isGeneric()) {
            return null;
        }
        return bindings.typesAsArray();
    }
    
    public JavaType moreSpecificType(final JavaType type1, final JavaType type2) {
        if (type1 == null) {
            return type2;
        }
        if (type2 == null) {
            return type1;
        }
        final Class<?> raw1 = type1.getRawClass();
        final Class<?> raw2 = type2.getRawClass();
        if (raw1 == raw2) {
            return type1;
        }
        if (raw1.isAssignableFrom(raw2)) {
            return type2;
        }
        return type1;
    }
    
    public JavaType constructType(final Type type) {
        return this._constructType(type, null);
    }
    
    public JavaType constructType(final Type type, final TypeBindings bindings) {
        return this._constructType(type, bindings);
    }
    
    public JavaType constructType(final TypeReference<?> typeRef) {
        return this._constructType(typeRef.getType(), null);
    }
    
    public JavaType constructType(final Type type, final Class<?> context) {
        final TypeBindings b = (context == null) ? null : new TypeBindings(this, context);
        return this._constructType(type, b);
    }
    
    public JavaType constructType(final Type type, final JavaType context) {
        final TypeBindings b = (context == null) ? null : new TypeBindings(this, context);
        return this._constructType(type, b);
    }
    
    protected JavaType _constructType(final Type type, final TypeBindings context) {
        JavaType resultType;
        if (type instanceof Class) {
            final Class<?> cls = (Class<?>)type;
            resultType = this._fromClass(cls, context);
        }
        else if (type instanceof ParameterizedType) {
            resultType = this._fromParamType((ParameterizedType)type, context);
        }
        else {
            if (type instanceof JavaType) {
                return (JavaType)type;
            }
            if (type instanceof GenericArrayType) {
                resultType = this._fromArrayType((GenericArrayType)type, context);
            }
            else if (type instanceof TypeVariable) {
                resultType = this._fromVariable((TypeVariable<?>)type, context);
            }
            else {
                if (!(type instanceof WildcardType)) {
                    throw new IllegalArgumentException("Unrecognized Type: " + ((type == null) ? "[null]" : type.toString()));
                }
                resultType = this._fromWildcard((WildcardType)type, context);
            }
        }
        if (this._modifiers != null && !resultType.isContainerType()) {
            for (final TypeModifier mod : this._modifiers) {
                resultType = mod.modifyType(resultType, type, context, this);
            }
        }
        return resultType;
    }
    
    public ArrayType constructArrayType(final Class<?> elementType) {
        return ArrayType.construct(this._constructType(elementType, null), null, null);
    }
    
    public ArrayType constructArrayType(final JavaType elementType) {
        return ArrayType.construct(elementType, null, null);
    }
    
    public CollectionType constructCollectionType(final Class<? extends Collection> collectionClass, final Class<?> elementClass) {
        return CollectionType.construct(collectionClass, this.constructType(elementClass));
    }
    
    public CollectionType constructCollectionType(final Class<? extends Collection> collectionClass, final JavaType elementType) {
        return CollectionType.construct(collectionClass, elementType);
    }
    
    public CollectionLikeType constructCollectionLikeType(final Class<?> collectionClass, final Class<?> elementClass) {
        return CollectionLikeType.construct(collectionClass, this.constructType(elementClass));
    }
    
    public CollectionLikeType constructCollectionLikeType(final Class<?> collectionClass, final JavaType elementType) {
        return CollectionLikeType.construct(collectionClass, elementType);
    }
    
    public MapType constructMapType(final Class<? extends Map> mapClass, final JavaType keyType, final JavaType valueType) {
        return MapType.construct(mapClass, keyType, valueType);
    }
    
    public MapType constructMapType(final Class<? extends Map> mapClass, final Class<?> keyClass, final Class<?> valueClass) {
        return MapType.construct(mapClass, this.constructType(keyClass), this.constructType(valueClass));
    }
    
    public MapLikeType constructMapLikeType(final Class<?> mapClass, final JavaType keyType, final JavaType valueType) {
        return MapLikeType.construct(mapClass, keyType, valueType);
    }
    
    public MapLikeType constructMapLikeType(final Class<?> mapClass, final Class<?> keyClass, final Class<?> valueClass) {
        return MapType.construct(mapClass, this.constructType(keyClass), this.constructType(valueClass));
    }
    
    public JavaType constructSimpleType(final Class<?> rawType, final JavaType[] parameterTypes) {
        final TypeVariable<?>[] typeVars = rawType.getTypeParameters();
        if (typeVars.length != parameterTypes.length) {
            throw new IllegalArgumentException("Parameter type mismatch for " + rawType.getName() + ": expected " + typeVars.length + " parameters, was given " + parameterTypes.length);
        }
        final String[] names = new String[typeVars.length];
        for (int i = 0, len = typeVars.length; i < len; ++i) {
            names[i] = typeVars[i].getName();
        }
        final JavaType resultType = new SimpleType(rawType, names, parameterTypes, null, null, false);
        return resultType;
    }
    
    public JavaType uncheckedSimpleType(final Class<?> cls) {
        return new SimpleType(cls);
    }
    
    public JavaType constructParametricType(final Class<?> parametrized, final Class<?>... parameterClasses) {
        final int len = parameterClasses.length;
        final JavaType[] pt = new JavaType[len];
        for (int i = 0; i < len; ++i) {
            pt[i] = this._fromClass(parameterClasses[i], null);
        }
        return this.constructParametricType(parametrized, pt);
    }
    
    public JavaType constructParametricType(final Class<?> parametrized, final JavaType... parameterTypes) {
        JavaType resultType;
        if (parametrized.isArray()) {
            if (parameterTypes.length != 1) {
                throw new IllegalArgumentException("Need exactly 1 parameter type for arrays (" + parametrized.getName() + ")");
            }
            resultType = this.constructArrayType(parameterTypes[0]);
        }
        else if (Map.class.isAssignableFrom(parametrized)) {
            if (parameterTypes.length != 2) {
                throw new IllegalArgumentException("Need exactly 2 parameter types for Map types (" + parametrized.getName() + ")");
            }
            resultType = this.constructMapType((Class<? extends Map>)parametrized, parameterTypes[0], parameterTypes[1]);
        }
        else if (Collection.class.isAssignableFrom(parametrized)) {
            if (parameterTypes.length != 1) {
                throw new IllegalArgumentException("Need exactly 1 parameter type for Collection types (" + parametrized.getName() + ")");
            }
            resultType = this.constructCollectionType((Class<? extends Collection>)parametrized, parameterTypes[0]);
        }
        else {
            resultType = this.constructSimpleType(parametrized, parameterTypes);
        }
        return resultType;
    }
    
    public CollectionType constructRawCollectionType(final Class<? extends Collection> collectionClass) {
        return CollectionType.construct(collectionClass, unknownType());
    }
    
    public CollectionLikeType constructRawCollectionLikeType(final Class<?> collectionClass) {
        return CollectionLikeType.construct(collectionClass, unknownType());
    }
    
    public MapType constructRawMapType(final Class<? extends Map> mapClass) {
        return MapType.construct(mapClass, unknownType(), unknownType());
    }
    
    public MapLikeType constructRawMapLikeType(final Class<?> mapClass) {
        return MapLikeType.construct(mapClass, unknownType(), unknownType());
    }
    
    protected JavaType _fromClass(final Class<?> clz, final TypeBindings context) {
        if (clz == String.class) {
            return TypeFactory.CORE_TYPE_STRING;
        }
        if (clz == Boolean.TYPE) {
            return TypeFactory.CORE_TYPE_BOOL;
        }
        if (clz == Integer.TYPE) {
            return TypeFactory.CORE_TYPE_INT;
        }
        if (clz == Long.TYPE) {
            return TypeFactory.CORE_TYPE_LONG;
        }
        final ClassKey key = new ClassKey(clz);
        JavaType result = this._typeCache.get(key);
        if (result != null) {
            return result;
        }
        if (clz.isArray()) {
            result = ArrayType.construct(this._constructType(clz.getComponentType(), null), null, null);
        }
        else if (clz.isEnum()) {
            result = new SimpleType(clz);
        }
        else if (Map.class.isAssignableFrom(clz)) {
            result = this._mapType(clz);
        }
        else if (Collection.class.isAssignableFrom(clz)) {
            result = this._collectionType(clz);
        }
        else {
            result = new SimpleType(clz);
        }
        this._typeCache.put(key, result);
        return result;
    }
    
    protected JavaType _fromParameterizedClass(final Class<?> clz, final List<JavaType> paramTypes) {
        if (clz.isArray()) {
            return ArrayType.construct(this._constructType(clz.getComponentType(), null), null, null);
        }
        if (clz.isEnum()) {
            return new SimpleType(clz);
        }
        if (Map.class.isAssignableFrom(clz)) {
            if (paramTypes.size() > 0) {
                final JavaType keyType = paramTypes.get(0);
                final JavaType contentType = (paramTypes.size() >= 2) ? paramTypes.get(1) : this._unknownType();
                return MapType.construct(clz, keyType, contentType);
            }
            return this._mapType(clz);
        }
        else if (Collection.class.isAssignableFrom(clz)) {
            if (paramTypes.size() >= 1) {
                return CollectionType.construct(clz, paramTypes.get(0));
            }
            return this._collectionType(clz);
        }
        else {
            if (paramTypes.size() == 0) {
                return new SimpleType(clz);
            }
            final JavaType[] pt = paramTypes.toArray(new JavaType[paramTypes.size()]);
            return this.constructSimpleType(clz, pt);
        }
    }
    
    protected JavaType _fromParamType(final ParameterizedType type, final TypeBindings context) {
        final Class<?> rawType = (Class<?>)type.getRawType();
        final Type[] args = type.getActualTypeArguments();
        final int paramCount = (args == null) ? 0 : args.length;
        JavaType[] pt;
        if (paramCount == 0) {
            pt = TypeFactory.NO_TYPES;
        }
        else {
            pt = new JavaType[paramCount];
            for (int i = 0; i < paramCount; ++i) {
                pt[i] = this._constructType(args[i], context);
            }
        }
        if (Map.class.isAssignableFrom(rawType)) {
            final JavaType subtype = this.constructSimpleType(rawType, pt);
            final JavaType[] mapParams = this.findTypeParameters(subtype, Map.class);
            if (mapParams.length != 2) {
                throw new IllegalArgumentException("Could not find 2 type parameters for Map class " + rawType.getName() + " (found " + mapParams.length + ")");
            }
            return MapType.construct(rawType, mapParams[0], mapParams[1]);
        }
        else if (Collection.class.isAssignableFrom(rawType)) {
            final JavaType subtype = this.constructSimpleType(rawType, pt);
            final JavaType[] collectionParams = this.findTypeParameters(subtype, Collection.class);
            if (collectionParams.length != 1) {
                throw new IllegalArgumentException("Could not find 1 type parameter for Collection class " + rawType.getName() + " (found " + collectionParams.length + ")");
            }
            return CollectionType.construct(rawType, collectionParams[0]);
        }
        else {
            if (paramCount == 0) {
                return new SimpleType(rawType);
            }
            return this.constructSimpleType(rawType, pt);
        }
    }
    
    protected JavaType _fromArrayType(final GenericArrayType type, final TypeBindings context) {
        final JavaType compType = this._constructType(type.getGenericComponentType(), context);
        return ArrayType.construct(compType, null, null);
    }
    
    protected JavaType _fromVariable(final TypeVariable<?> type, final TypeBindings context) {
        if (context == null) {
            return this._unknownType();
        }
        final String name = type.getName();
        final JavaType actualType = context.findType(name);
        if (actualType != null) {
            return actualType;
        }
        final Type[] bounds = type.getBounds();
        context._addPlaceholder(name);
        return this._constructType(bounds[0], context);
    }
    
    protected JavaType _fromWildcard(final WildcardType type, final TypeBindings context) {
        return this._constructType(type.getUpperBounds()[0], context);
    }
    
    private JavaType _mapType(final Class<?> rawClass) {
        final JavaType[] typeParams = this.findTypeParameters(rawClass, Map.class);
        if (typeParams == null) {
            return MapType.construct(rawClass, this._unknownType(), this._unknownType());
        }
        if (typeParams.length != 2) {
            throw new IllegalArgumentException("Strange Map type " + rawClass.getName() + ": can not determine type parameters");
        }
        return MapType.construct(rawClass, typeParams[0], typeParams[1]);
    }
    
    private JavaType _collectionType(final Class<?> rawClass) {
        final JavaType[] typeParams = this.findTypeParameters(rawClass, Collection.class);
        if (typeParams == null) {
            return CollectionType.construct(rawClass, this._unknownType());
        }
        if (typeParams.length != 1) {
            throw new IllegalArgumentException("Strange Collection type " + rawClass.getName() + ": can not determine type parameters");
        }
        return CollectionType.construct(rawClass, typeParams[0]);
    }
    
    protected JavaType _resolveVariableViaSubTypes(final HierarchicType leafType, final String variableName, final TypeBindings bindings) {
        if (leafType != null && leafType.isGeneric()) {
            final TypeVariable<?>[] typeVariables = leafType.getRawClass().getTypeParameters();
            int i = 0;
            final int len = typeVariables.length;
            while (i < len) {
                final TypeVariable<?> tv = typeVariables[i];
                if (variableName.equals(tv.getName())) {
                    final Type type = leafType.asGeneric().getActualTypeArguments()[i];
                    if (type instanceof TypeVariable) {
                        return this._resolveVariableViaSubTypes(leafType.getSubType(), ((TypeVariable)type).getName(), bindings);
                    }
                    return this._constructType(type, bindings);
                }
                else {
                    ++i;
                }
            }
        }
        return this._unknownType();
    }
    
    protected JavaType _unknownType() {
        return new SimpleType(Object.class);
    }
    
    protected HierarchicType _findSuperTypeChain(final Class<?> subtype, final Class<?> supertype) {
        if (supertype.isInterface()) {
            return this._findSuperInterfaceChain(subtype, supertype);
        }
        return this._findSuperClassChain(subtype, supertype);
    }
    
    protected HierarchicType _findSuperClassChain(final Type currentType, final Class<?> target) {
        final HierarchicType current = new HierarchicType(currentType);
        final Class<?> raw = current.getRawClass();
        if (raw == target) {
            return current;
        }
        final Type parent = raw.getGenericSuperclass();
        if (parent != null) {
            final HierarchicType sup = this._findSuperClassChain(parent, target);
            if (sup != null) {
                sup.setSubType(current);
                current.setSuperType(sup);
                return current;
            }
        }
        return null;
    }
    
    protected HierarchicType _findSuperInterfaceChain(final Type currentType, final Class<?> target) {
        final HierarchicType current = new HierarchicType(currentType);
        final Class<?> raw = current.getRawClass();
        if (raw == target) {
            return new HierarchicType(currentType);
        }
        if (raw == HashMap.class && target == Map.class) {
            return this._hashMapSuperInterfaceChain(current);
        }
        if (raw == ArrayList.class && target == List.class) {
            return this._arrayListSuperInterfaceChain(current);
        }
        return this._doFindSuperInterfaceChain(current, target);
    }
    
    protected HierarchicType _doFindSuperInterfaceChain(final HierarchicType current, final Class<?> target) {
        final Class<?> raw = current.getRawClass();
        final Type[] parents = raw.getGenericInterfaces();
        if (parents != null) {
            for (final Type parent : parents) {
                final HierarchicType sup = this._findSuperInterfaceChain(parent, target);
                if (sup != null) {
                    sup.setSubType(current);
                    current.setSuperType(sup);
                    return current;
                }
            }
        }
        final Type parent2 = raw.getGenericSuperclass();
        if (parent2 != null) {
            final HierarchicType sup2 = this._findSuperInterfaceChain(parent2, target);
            if (sup2 != null) {
                sup2.setSubType(current);
                current.setSuperType(sup2);
                return current;
            }
        }
        return null;
    }
    
    protected synchronized HierarchicType _hashMapSuperInterfaceChain(final HierarchicType current) {
        if (this._cachedHashMapType == null) {
            final HierarchicType base = current.deepCloneWithoutSubtype();
            this._doFindSuperInterfaceChain(base, Map.class);
            this._cachedHashMapType = base.getSuperType();
        }
        final HierarchicType t = this._cachedHashMapType.deepCloneWithoutSubtype();
        current.setSuperType(t);
        t.setSubType(current);
        return current;
    }
    
    protected synchronized HierarchicType _arrayListSuperInterfaceChain(final HierarchicType current) {
        if (this._cachedArrayListType == null) {
            final HierarchicType base = current.deepCloneWithoutSubtype();
            this._doFindSuperInterfaceChain(base, List.class);
            this._cachedArrayListType = base.getSuperType();
        }
        final HierarchicType t = this._cachedArrayListType.deepCloneWithoutSubtype();
        current.setSuperType(t);
        t.setSubType(current);
        return current;
    }
    
    static {
        NO_TYPES = new JavaType[0];
        instance = new TypeFactory();
        CORE_TYPE_STRING = new SimpleType(String.class);
        CORE_TYPE_BOOL = new SimpleType(Boolean.TYPE);
        CORE_TYPE_INT = new SimpleType(Integer.TYPE);
        CORE_TYPE_LONG = new SimpleType(Long.TYPE);
    }
}
