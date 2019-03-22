// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.utils;

import scala.collection.JavaConverters;
import scala.collection.Iterator;
import scala.collection.Seq;
import java.util.List;

public class ScalaUtils
{
    public static <T> Seq<T> toScalaSeq(final List<T> list) {
        return JavaConverters.asScalaIteratorConverter(list.iterator()).asScala().toSeq();
    }
}
