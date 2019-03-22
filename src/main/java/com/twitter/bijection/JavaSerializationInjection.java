// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.util.Try;
import scala.Function1;
import scala.reflect.ScalaSignature;
import java.io.Serializable;

@ScalaSignature(bytes = "\u0006\u0001\u001d<Q!\u0001\u0002\t\u0002%\t!DS1wCN+'/[1mSj\fG/[8o\u0013:TWm\u0019;j_:T!a\u0001\u0003\u0002\u0013\tL'.Z2uS>t'BA\u0003\u0007\u0003\u001d!x/\u001b;uKJT\u0011aB\u0001\u0004G>l7\u0001\u0001\t\u0003\u0015-i\u0011A\u0001\u0004\u0006\u0019\tA\t!\u0004\u0002\u001b\u0015\u00064\u0018mU3sS\u0006d\u0017N_1uS>t\u0017J\u001c6fGRLwN\\\n\u0004\u00179!\u0002CA\b\u0013\u001b\u0005\u0001\"\"A\t\u0002\u000bM\u001c\u0017\r\\1\n\u0005M\u0001\"AB!osJ+g\r\u0005\u0002\u001655\taC\u0003\u0002\u00181\u0005\u0011\u0011n\u001c\u0006\u00023\u0005!!.\u0019<b\u0013\tYbC\u0001\u0007TKJL\u0017\r\\5{C\ndW\rC\u0003\u001e\u0017\u0011\u0005a$\u0001\u0004=S:LGO\u0010\u000b\u0002\u0013!)\u0001e\u0003C\u0001C\u0005)\u0011\r\u001d9msV\u0011!%\u0016\u000b\u0003GY\u00032A\u0003\u0013U\r\u0011a!\u0001A\u0013\u0016\u0005\u0019b3c\u0001\u0013\u000fOA!!\u0002\u000b\u00163\u0013\tI#AA\u0005J]*,7\r^5p]B\u00111\u0006\f\u0007\u0001\t\u0015iCE1\u0001/\u0005\u0005!\u0016CA\u0018\u0015!\ty\u0001'\u0003\u00022!\t9aj\u001c;iS:<\u0007cA\b4k%\u0011A\u0007\u0005\u0002\u0006\u0003J\u0014\u0018-\u001f\t\u0003\u001fYJ!a\u000e\t\u0003\t\tKH/\u001a\u0005\ts\u0011\u0012\t\u0011)A\u0005u\u0005)1\u000e\\1tgB\u00191H\u0010\u0016\u000f\u0005=a\u0014BA\u001f\u0011\u0003\u0019\u0001&/\u001a3fM&\u0011q\b\u0011\u0002\u0006\u00072\f7o\u001d\u0006\u0003{AAQ!\b\u0013\u0005\u0002\t#\"a\u0011#\u0011\u0007)!#\u0006C\u0003:\u0003\u0002\u0007!\bC\u0003!I\u0011\u0005a\t\u0006\u00023\u000f\")\u0001*\u0012a\u0001U\u0005\tA\u000fC\u0003KI\u0011\u00051*\u0001\u0004j]Z,'\u000f\u001e\u000b\u0003\u0019J\u00032!\u0014)+\u001b\u0005q%BA(\u0011\u0003\u0011)H/\u001b7\n\u0005Es%a\u0001+ss\")1+\u0013a\u0001e\u0005)!-\u001f;fgB\u00111&\u0016\u0003\u0006[}\u0011\rA\f\u0005\u0006/~\u0001\u001d\u0001W\u0001\u0003GR\u00042!\u0017/U\u001b\u0005Q&BA.\u0011\u0003\u001d\u0011XM\u001a7fGRL!!\u0018.\u0003\u0011\rc\u0017m]:UC\u001eDqaX\u0006\u0002\u0002\u0013%\u0001-A\u0006sK\u0006$'+Z:pYZ,G#A1\u0011\u0005\t,W\"A2\u000b\u0005\u0011D\u0012\u0001\u00027b]\u001eL!AZ2\u0003\r=\u0013'.Z2u\u0001")
public class JavaSerializationInjection<T extends Serializable> implements Injection<T, byte[]>
{
    public final Class<T> com$twitter$bijection$JavaSerializationInjection$$klass;
    
    @Override
    public <C> Injection<T, C> andThen(final Injection<byte[], C> g) {
        return (Injection<T, C>)Injection$class.andThen(this, g);
    }
    
    @Override
    public <C> Injection<T, C> andThen(final Bijection<byte[], C> bij) {
        return (Injection<T, C>)Injection$class.andThen(this, bij);
    }
    
    @Override
    public <C> Function1<T, C> andThen(final Function1<byte[], C> g) {
        return (Function1<T, C>)Injection$class.andThen(this, g);
    }
    
    @Override
    public <T> Injection<T, byte[]> compose(final Injection<T, T> g) {
        return (Injection<T, byte[]>)Injection$class.compose(this, g);
    }
    
    @Override
    public <T> Injection<T, byte[]> compose(final Bijection<T, T> bij) {
        return (Injection<T, byte[]>)Injection$class.compose(this, bij);
    }
    
    @Override
    public <T> Function1<T, byte[]> compose(final Function1<T, T> g) {
        return (Function1<T, byte[]>)Injection$class.compose(this, g);
    }
    
    @Override
    public Function1<T, byte[]> toFunction() {
        return (Function1<T, byte[]>)Injection$class.toFunction(this);
    }
    
    @Override
    public byte[] apply(final T t) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: new             Ljava/io/ByteArrayOutputStream;
        //     3: dup            
        //     4: \u0131nvokespec\u0131al   java/io/ByteArrayOutputStream.<init>:()V
        //     7: astore_2        /* bos */
        //     8: new             Ljava/io/ObjectOutputStream;
        //    11: dup            
        //    12: aload_2         /* bos */
        //    13: \u0131nvokespec\u0131al   java/io/ObjectOutputStream.<init>:(Ljava/io/OutputStream;)V
        //    16: astore_3        /* out */
        //    17: aload_3         /* out */
        //    18: aload_1         /* t */
        //    19: \u0131nvokev\u0131rtual   java/io/ObjectOutputStream.writeObject:(Ljava/lang/Object;)V
        //    22: aload_2         /* bos */
        //    23: \u0131nvokev\u0131rtual   java/io/ByteArrayOutputStream.toByteArray:()[B
        //    26: aload_3         /* out */
        //    27: \u0131nvokev\u0131rtual   java/io/ObjectOutputStream.close:()V
        //    30: aload_2         /* bos */
        //    31: \u0131nvokev\u0131rtual   java/io/ByteArrayOutputStream.close:()V
        //    34: areturn        
        //    35: astore          4
        //    37: aload_3        
        //    38: \u0131nvokev\u0131rtual   java/io/ObjectOutputStream.close:()V
        //    41: aload_2        
        //    42: \u0131nvokev\u0131rtual   java/io/ByteArrayOutputStream.close:()V
        //    45: aload           4
        //    47: athrow         
        //    Signature:
        //  (TT;)[B
        //    LocalVariableTable:
        //  Start  Length  Slot  Name  Signature
        //  -----  ------  ----  ----  --------------------------------------------------
        //  0      48      0     this  Lcom/twitter/bijection/JavaSerializationInjection;
        //  0      48      1     t     Ljava/io/Serializable;
        //  8      26      2     bos   Ljava/io/ByteArrayOutputStream;
        //  17     17      3     out   Ljava/io/ObjectOutputStream;
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type
        //  -----  -----  -----  -----  ----
        //  17     26     35     48     Any
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2985)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:123)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public Try<T> invert(final byte[] bytes) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: new             Ljava/io/ByteArrayInputStream;
        //     3: dup            
        //     4: aload_1         /* bytes */
        //     5: \u0131nvokespec\u0131al   java/io/ByteArrayInputStream.<init>:([B)V
        //     8: astore_2        /* bis */
        //     9: getstat\u0131c       scala/util/Try$.MODULE$:Lscala/util/Try$;
        //    12: new             Lcom/twitter/bijection/JavaSerializationInjection$$anonfun$1;
        //    15: dup            
        //    16: aload_0         /* this */
        //    17: aload_2         /* bis */
        //    18: \u0131nvokespec\u0131al   com/twitter/bijection/JavaSerializationInjection$$anonfun$1.<init>:(Lcom/twitter/bijection/JavaSerializationInjection;Ljava/io/ByteArrayInputStream;)V
        //    21: \u0131nvokev\u0131rtual   scala/util/Try$.apply:(Lscala/Function0;)Lscala/util/Try;
        //    24: astore_3        /* inOpt */
        //    25: aload_3         /* inOpt */
        //    26: new             Lcom/twitter/bijection/JavaSerializationInjection$$anonfun$invert$1;
        //    29: dup            
        //    30: aload_0         /* this */
        //    31: \u0131nvokespec\u0131al   com/twitter/bijection/JavaSerializationInjection$$anonfun$invert$1.<init>:(Lcom/twitter/bijection/JavaSerializationInjection;)V
        //    34: \u0131nvokev\u0131rtual   scala/util/Try.map:(Lscala/Function1;)Lscala/util/Try;
        //    37: getstat\u0131c       com/twitter/bijection/InversionFailure$.MODULE$:Lcom/twitter/bijection/InversionFailure$;
        //    40: aload_1         /* bytes */
        //    41: \u0131nvokev\u0131rtual   com/twitter/bijection/InversionFailure$.partialFailure:(Ljava/lang/Object;)Lscala/PartialFunction;
        //    44: \u0131nvokev\u0131rtual   scala/util/Try.recoverWith:(Lscala/PartialFunction;)Lscala/util/Try;
        //    47: goto            94
        //    50: astore          4
        //    52: new             Lscala/util/Failure;
        //    55: dup            
        //    56: new             Lcom/twitter/bijection/InversionFailure;
        //    59: dup            
        //    60: aload_1         /* bytes */
        //    61: aload           4
        //    63: \u0131nvokespec\u0131al   com/twitter/bijection/InversionFailure.<init>:(Ljava/lang/Object;Ljava/lang/Throwable;)V
        //    66: \u0131nvokespec\u0131al   scala/util/Failure.<init>:(Ljava/lang/Throwable;)V
        //    69: goto            94
        //    72: astore          5
        //    74: aload_2         /* bis */
        //    75: \u0131nvokev\u0131rtual   java/io/ByteArrayInputStream.close:()V
        //    78: aload_3         /* inOpt */
        //    79: new             Lcom/twitter/bijection/JavaSerializationInjection$$anonfun$invert$2;
        //    82: dup            
        //    83: aload_0         /* this */
        //    84: \u0131nvokespec\u0131al   com/twitter/bijection/JavaSerializationInjection$$anonfun$invert$2.<init>:(Lcom/twitter/bijection/JavaSerializationInjection;)V
        //    87: \u0131nvokev\u0131rtual   scala/util/Try.map:(Lscala/Function1;)Lscala/util/Try;
        //    90: pop            
        //    91: aload           5
        //    93: athrow         
        //    94: aload_2         /* bis */
        //    95: \u0131nvokev\u0131rtual   java/io/ByteArrayInputStream.close:()V
        //    98: aload_3         /* inOpt */
        //    99: new             Lcom/twitter/bijection/JavaSerializationInjection$$anonfun$invert$2;
        //   102: dup            
        //   103: aload_0         /* this */
        //   104: \u0131nvokespec\u0131al   com/twitter/bijection/JavaSerializationInjection$$anonfun$invert$2.<init>:(Lcom/twitter/bijection/JavaSerializationInjection;)V
        //   107: \u0131nvokev\u0131rtual   scala/util/Try.map:(Lscala/Function1;)Lscala/util/Try;
        //   110: pop            
        //   111: areturn        
        //    Signature:
        //  ([B)Lscala/util/Try<TT;>;
        //    LocalVariableTable:
        //  Start  Length  Slot  Name   Signature
        //  -----  ------  ----  -----  --------------------------------------------------
        //  0      112     0     this   Lcom/twitter/bijection/JavaSerializationInjection;
        //  0      112     1     bytes  [B
        //  9      102     2     bis    Ljava/io/ByteArrayInputStream;
        //  25     86      3     inOpt  Lscala/util/Try;
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type
        //  -----  -----  -----  -----  ----
        //  25     50     50     72     Any
        //  25     72     72     94     Any
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException: -1
        //     at java.util.ArrayList.elementData(Unknown Source)
        //     at java.util.ArrayList.remove(Unknown Source)
        //     at com.strobel.assembler.ir.StackMappingVisitor.pop(StackMappingVisitor.java:267)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.execute(StackMappingVisitor.java:595)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.visit(StackMappingVisitor.java:398)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2030)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:123)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public JavaSerializationInjection(final Class<T> klass) {
        this.com$twitter$bijection$JavaSerializationInjection$$klass = klass;
        Injection$class.$init$(this);
    }
}
