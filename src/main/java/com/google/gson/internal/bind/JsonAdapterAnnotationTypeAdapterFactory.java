// 
// Decompiled by Procyon v0.5.30
// 

package com.google.gson.internal.bind;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.TypeAdapterFactory;

public final class JsonAdapterAnnotationTypeAdapterFactory implements TypeAdapterFactory
{
    private final ConstructorConstructor constructorConstructor;
    
    public JsonAdapterAnnotationTypeAdapterFactory(final ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }
    
    @Override
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> targetType) {
        final JsonAdapter annotation = targetType.getRawType().getAnnotation(JsonAdapter.class);
        if (annotation == null) {
            return null;
        }
        return (TypeAdapter<T>)getTypeAdapter(this.constructorConstructor, gson, targetType, annotation);
    }
    
    static TypeAdapter<?> getTypeAdapter(final ConstructorConstructor constructorConstructor, final Gson gson, final TypeToken<?> fieldType, final JsonAdapter annotation) {
        final Class<?> value = annotation.value();
        TypeAdapter<?> typeAdapter;
        if (TypeAdapter.class.isAssignableFrom(value)) {
            final Class<TypeAdapter<?>> typeAdapterClass = (Class<TypeAdapter<?>>)value;
            typeAdapter = constructorConstructor.get((TypeToken<TypeAdapter<?>>)TypeToken.get((Class<T>)typeAdapterClass)).construct();
        }
        else {
            if (!TypeAdapterFactory.class.isAssignableFrom(value)) {
                throw new IllegalArgumentException("@JsonAdapter value must be TypeAdapter or TypeAdapterFactory reference.");
            }
            final Class<TypeAdapterFactory> typeAdapterFactory = (Class<TypeAdapterFactory>)value;
            typeAdapter = constructorConstructor.get((TypeToken<TypeAdapterFactory>)TypeToken.get((Class<T>)typeAdapterFactory)).construct().create(gson, fieldType);
        }
        if (typeAdapter != null) {
            typeAdapter = typeAdapter.nullSafe();
        }
        return typeAdapter;
    }
}
