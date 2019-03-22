// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.reflectasm.shaded.org.objectweb.asm;

import java.io.IOException;
import java.io.InputStream;

public class ClassReader
{
    public static final int SKIP_CODE = 1;
    public static final int SKIP_DEBUG = 2;
    public static final int SKIP_FRAMES = 4;
    public static final int EXPAND_FRAMES = 8;
    public final byte[] b;
    private final int[] a;
    private final String[] c;
    private final int d;
    public final int header;
    
    public ClassReader(final byte[] array) {
        this(array, 0, array.length);
    }
    
    public ClassReader(final byte[] b, final int n, final int n2) {
        this.b = b;
        if (this.readShort(6) > 51) {
            throw new IllegalArgumentException();
        }
        this.a = new int[this.readUnsignedShort(n + 8)];
        final int length = this.a.length;
        this.c = new String[length];
        int d = 0;
        int header = n + 10;
        for (int i = 1; i < length; ++i) {
            this.a[i] = header + 1;
            int n3 = 0;
            switch (b[header]) {
                case 3:
                case 4:
                case 9:
                case 10:
                case 11:
                case 12:
                case 18: {
                    n3 = 5;
                    break;
                }
                case 5:
                case 6: {
                    n3 = 9;
                    ++i;
                    break;
                }
                case 1: {
                    n3 = 3 + this.readUnsignedShort(header + 1);
                    if (n3 > d) {
                        d = n3;
                        break;
                    }
                    break;
                }
                case 15: {
                    n3 = 4;
                    break;
                }
                default: {
                    n3 = 3;
                    break;
                }
            }
            header += n3;
        }
        this.d = d;
        this.header = header;
    }
    
    public int getAccess() {
        return this.readUnsignedShort(this.header);
    }
    
    public String getClassName() {
        return this.readClass(this.header + 2, new char[this.d]);
    }
    
    public String getSuperName() {
        final int n = this.a[this.readUnsignedShort(this.header + 4)];
        return (n == 0) ? null : this.readUTF8(n, new char[this.d]);
    }
    
    public String[] getInterfaces() {
        int n = this.header + 6;
        final int unsignedShort = this.readUnsignedShort(n);
        final String[] array = new String[unsignedShort];
        if (unsignedShort > 0) {
            final char[] array2 = new char[this.d];
            for (int i = 0; i < unsignedShort; ++i) {
                n += 2;
                array[i] = this.readClass(n, array2);
            }
        }
        return array;
    }
    
    void a(final ClassWriter classWriter) {
        final char[] array = new char[this.d];
        final int length = this.a.length;
        final Item[] e = new Item[length];
        for (int i = 1; i < length; ++i) {
            final int n = this.a[i];
            final byte b = this.b[n - 1];
            final Item \u0131tem = new Item(i);
            switch (b) {
                case 9:
                case 10:
                case 11: {
                    final int n2 = this.a[this.readUnsignedShort(n + 2)];
                    \u0131tem.a(b, this.readClass(n, array), this.readUTF8(n2, array), this.readUTF8(n2 + 2, array));
                    break;
                }
                case 3: {
                    \u0131tem.a(this.readInt(n));
                    break;
                }
                case 4: {
                    \u0131tem.a(Float.intBitsToFloat(this.readInt(n)));
                    break;
                }
                case 12: {
                    \u0131tem.a(b, this.readUTF8(n, array), this.readUTF8(n + 2, array), null);
                    break;
                }
                case 5: {
                    \u0131tem.a(this.readLong(n));
                    ++i;
                    break;
                }
                case 6: {
                    \u0131tem.a(Double.longBitsToDouble(this.readLong(n)));
                    ++i;
                    break;
                }
                case 1: {
                    String s = this.c[i];
                    if (s == null) {
                        final int n3 = this.a[i];
                        final String[] c = this.c;
                        final int n4 = i;
                        final String a = this.a(n3 + 2, this.readUnsignedShort(n3), array);
                        c[n4] = a;
                        s = a;
                    }
                    \u0131tem.a(b, s, null, null);
                    break;
                }
                case 15: {
                    final int n5 = this.a[this.readUnsignedShort(n + 1)];
                    final int n6 = this.a[this.readUnsignedShort(n5 + 2)];
                    \u0131tem.a(20 + this.readByte(n), this.readClass(n5, array), this.readUTF8(n6, array), this.readUTF8(n6 + 2, array));
                    break;
                }
                case 18: {
                    if (classWriter.A == null) {
                        this.a(classWriter, e, array);
                    }
                    final int n7 = this.a[this.readUnsignedShort(n + 2)];
                    \u0131tem.a(this.readUTF8(n7, array), this.readUTF8(n7 + 2, array), this.readUnsignedShort(n));
                    break;
                }
                default: {
                    \u0131tem.a(b, this.readUTF8(n, array), null, null);
                    break;
                }
            }
            final int n8 = \u0131tem.j % e.length;
            \u0131tem.k = e[n8];
            e[n8] = \u0131tem;
        }
        final int n9 = this.a[1] - 1;
        classWriter.d.putByteArray(this.b, n9, this.header - n9);
        classWriter.e = e;
        classWriter.f = (int)(0.75 * length);
        classWriter.c = length;
    }
    
    private void a(final ClassWriter classWriter, final Item[] array, final char[] array2) {
        final int header = this.header;
        int n = header + (8 + (this.readUnsignedShort(header + 6) << 1));
        int i = this.readUnsignedShort(n);
        n += 2;
        while (i > 0) {
            int j = this.readUnsignedShort(n + 6);
            n += 8;
            while (j > 0) {
                n += 6 + this.readInt(n + 2);
                --j;
            }
            --i;
        }
        int k = this.readUnsignedShort(n);
        n += 2;
        while (k > 0) {
            int l = this.readUnsignedShort(n + 6);
            n += 8;
            while (l > 0) {
                n += 6 + this.readInt(n + 2);
                --l;
            }
            --k;
        }
        int unsignedShort = this.readUnsignedShort(n);
        n += 2;
        while (unsignedShort > 0) {
            final String utf8 = this.readUTF8(n, array2);
            final int \u0131nt = this.readInt(n + 2);
            if ("BootstrapMethods".equals(utf8)) {
                final int unsignedShort2 = this.readUnsignedShort(n + 6);
                int n2 = n + 8;
                for (int n3 = 0; n3 < unsignedShort2; ++n3) {
                    int hashCode = this.readConst(this.readUnsignedShort(n2), array2).hashCode();
                    int unsignedShort3 = this.readUnsignedShort(n2 + 2);
                    int n4 = n2 + 4;
                    while (unsignedShort3 > 0) {
                        hashCode ^= this.readConst(this.readUnsignedShort(n4), array2).hashCode();
                        n4 += 2;
                        --unsignedShort3;
                    }
                    final Item \u0131tem = new Item(n3);
                    \u0131tem.a(n2 - n - 8, hashCode & Integer.MAX_VALUE);
                    final int n5 = \u0131tem.j % array.length;
                    \u0131tem.k = array[n5];
                    array[n5] = \u0131tem;
                    n2 = n4;
                }
                classWriter.z = unsignedShort2;
                final ByteVector a = new ByteVector(\u0131nt + 62);
                a.putByteArray(this.b, n + 8, \u0131nt - 2);
                classWriter.A = a;
                return;
            }
            n += 6 + \u0131nt;
            --unsignedShort;
        }
    }
    
    public ClassReader(final InputStream \u0131nputStream) throws IOException {
        this(a(\u0131nputStream, false));
    }
    
    public ClassReader(final String s) throws IOException {
        this(a(ClassLoader.getSystemResourceAsStream(s.replace('.', '/') + ".class"), true));
    }
    
    private static byte[] a(final InputStream \u0131nputStream, final boolean b) throws IOException {
        if (\u0131nputStream == null) {
            throw new IOException("Class not found");
        }
        try {
            byte[] array = new byte[\u0131nputStream.available()];
            int n = 0;
            while (true) {
                final int read = \u0131nputStream.read(array, n, array.length - n);
                if (read == -1) {
                    if (n < array.length) {
                        final byte[] array2 = new byte[n];
                        System.arraycopy(array, 0, array2, 0, n);
                        array = array2;
                    }
                    return array;
                }
                n += read;
                if (n != array.length) {
                    continue;
                }
                final int read2 = \u0131nputStream.read();
                if (read2 < 0) {
                    return array;
                }
                final byte[] array3 = new byte[array.length + 1000];
                System.arraycopy(array, 0, array3, 0, n);
                array3[n++] = (byte)read2;
                array = array3;
            }
        }
        finally {
            if (b) {
                \u0131nputStream.close();
            }
        }
    }
    
    public void accept(final ClassVisitor classVisitor, final int n) {
        this.accept(classVisitor, new Attribute[0], n);
    }
    
    public void accept(final ClassVisitor classVisitor, final Attribute[] array, final int n) {
        final byte[] b = this.b;
        final char[] array2 = new char[this.d];
        int n2 = 0;
        int n3 = 0;
        Attribute a = null;
        int header = this.header;
        int unsignedShort = this.readUnsignedShort(header);
        final String class1 = this.readClass(header + 2, array2);
        final int n4 = this.a[this.readUnsignedShort(header + 4)];
        final String s = (n4 == 0) ? null : this.readUTF8(n4, array2);
        final String[] array3 = new String[this.readUnsignedShort(header + 6)];
        int n5 = 0;
        header += 8;
        for (int i = 0; i < array3.length; ++i) {
            array3[i] = this.readClass(header, array2);
            header += 2;
        }
        final boolean b2 = (n & 0x1) != 0x0;
        final boolean b3 = (n & 0x2) != 0x0;
        final int n6 = ((n & 0x8) != 0x0) ? 1 : 0;
        int n7 = header;
        int j = this.readUnsignedShort(n7);
        n7 += 2;
        while (j > 0) {
            int k = this.readUnsignedShort(n7 + 6);
            n7 += 8;
            while (k > 0) {
                n7 += 6 + this.readInt(n7 + 2);
                --k;
            }
            --j;
        }
        int l = this.readUnsignedShort(n7);
        n7 += 2;
        while (l > 0) {
            int unsignedShort2 = this.readUnsignedShort(n7 + 6);
            n7 += 8;
            while (unsignedShort2 > 0) {
                n7 += 6 + this.readInt(n7 + 2);
                --unsignedShort2;
            }
            --l;
        }
        String utf8 = null;
        String utf9 = null;
        String a2 = null;
        String class2 = null;
        String utf10 = null;
        String utf11 = null;
        int[] array4 = null;
        int unsignedShort3 = this.readUnsignedShort(n7);
        n7 += 2;
        while (unsignedShort3 > 0) {
            final String utf12 = this.readUTF8(n7, array2);
            if ("SourceFile".equals(utf12)) {
                utf9 = this.readUTF8(n7 + 6, array2);
            }
            else if ("InnerClasses".equals(utf12)) {
                n5 = n7 + 6;
            }
            else if ("EnclosingMethod".equals(utf12)) {
                class2 = this.readClass(n7 + 6, array2);
                final int unsignedShort4 = this.readUnsignedShort(n7 + 8);
                if (unsignedShort4 != 0) {
                    utf10 = this.readUTF8(this.a[unsignedShort4], array2);
                    utf11 = this.readUTF8(this.a[unsignedShort4] + 2, array2);
                }
            }
            else if ("Signature".equals(utf12)) {
                utf8 = this.readUTF8(n7 + 6, array2);
            }
            else if ("RuntimeVisibleAnnotations".equals(utf12)) {
                n2 = n7 + 6;
            }
            else if ("Deprecated".equals(utf12)) {
                unsignedShort |= 0x20000;
            }
            else if ("Synthetic".equals(utf12)) {
                unsignedShort |= 0x41000;
            }
            else if ("SourceDebugExtension".equals(utf12)) {
                final int \u0131nt = this.readInt(n7 + 2);
                a2 = this.a(n7 + 6, \u0131nt, new char[\u0131nt]);
            }
            else if ("RuntimeInvisibleAnnotations".equals(utf12)) {
                n3 = n7 + 6;
            }
            else if ("BootstrapMethods".equals(utf12)) {
                final int unsignedShort5 = this.readUnsignedShort(n7 + 6);
                array4 = new int[unsignedShort5];
                int n8 = n7 + 8;
                for (int n9 = 0; n9 < unsignedShort5; ++n9) {
                    array4[n9] = n8;
                    n8 += 2 + this.readUnsignedShort(n8 + 2) << 1;
                }
            }
            else {
                final Attribute a3 = this.a(array, utf12, n7 + 6, this.readInt(n7 + 2), array2, -1, null);
                if (a3 != null) {
                    a3.a = a;
                    a = a3;
                }
            }
            n7 += 6 + this.readInt(n7 + 2);
            --unsignedShort3;
        }
        classVisitor.visit(this.readInt(4), unsignedShort, class1, utf8, s, array3);
        if (!b3 && (utf9 != null || a2 != null)) {
            classVisitor.visitSource(utf9, a2);
        }
        if (class2 != null) {
            classVisitor.visitOuterClass(class2, utf10, utf11);
        }
        for (int n10 = 1; n10 >= 0; --n10) {
            int a4 = (n10 == 0) ? n3 : n2;
            if (a4 != 0) {
                int unsignedShort6 = this.readUnsignedShort(a4);
                a4 += 2;
                while (unsignedShort6 > 0) {
                    a4 = this.a(a4 + 2, array2, true, classVisitor.visitAnnotation(this.readUTF8(a4, array2), n10 != 0));
                    --unsignedShort6;
                }
            }
        }
        while (a != null) {
            final Attribute a5 = a.a;
            a.a = null;
            classVisitor.visitAttribute(a);
            a = a5;
        }
        if (n5 != 0) {
            int unsignedShort7 = this.readUnsignedShort(n5);
            n5 += 2;
            while (unsignedShort7 > 0) {
                classVisitor.visitInnerClass((this.readUnsignedShort(n5) == 0) ? null : this.readClass(n5, array2), (this.readUnsignedShort(n5 + 2) == 0) ? null : this.readClass(n5 + 2, array2), (this.readUnsignedShort(n5 + 4) == 0) ? null : this.readUTF8(n5 + 4, array2), this.readUnsignedShort(n5 + 6));
                n5 += 8;
                --unsignedShort7;
            }
        }
        int unsignedShort8 = this.readUnsignedShort(header);
        header += 2;
        while (unsignedShort8 > 0) {
            int unsignedShort9 = this.readUnsignedShort(header);
            final String utf13 = this.readUTF8(header + 2, array2);
            final String utf14 = this.readUTF8(header + 4, array2);
            int unsignedShort10 = 0;
            String utf15 = null;
            int n11 = 0;
            int n12 = 0;
            Attribute a6 = null;
            int unsignedShort11 = this.readUnsignedShort(header + 6);
            header += 8;
            while (unsignedShort11 > 0) {
                final String utf16 = this.readUTF8(header, array2);
                if ("ConstantValue".equals(utf16)) {
                    unsignedShort10 = this.readUnsignedShort(header + 6);
                }
                else if ("Signature".equals(utf16)) {
                    utf15 = this.readUTF8(header + 6, array2);
                }
                else if ("Deprecated".equals(utf16)) {
                    unsignedShort9 |= 0x20000;
                }
                else if ("Synthetic".equals(utf16)) {
                    unsignedShort9 |= 0x41000;
                }
                else if ("RuntimeVisibleAnnotations".equals(utf16)) {
                    n11 = header + 6;
                }
                else if ("RuntimeInvisibleAnnotations".equals(utf16)) {
                    n12 = header + 6;
                }
                else {
                    final Attribute a7 = this.a(array, utf16, header + 6, this.readInt(header + 2), array2, -1, null);
                    if (a7 != null) {
                        a7.a = a6;
                        a6 = a7;
                    }
                }
                header += 6 + this.readInt(header + 2);
                --unsignedShort11;
            }
            final FieldVisitor visitField = classVisitor.visitField(unsignedShort9, utf13, utf14, utf15, (unsignedShort10 == 0) ? null : this.readConst(unsignedShort10, array2));
            if (visitField != null) {
                for (int n13 = 1; n13 >= 0; --n13) {
                    int a8 = (n13 == 0) ? n12 : n11;
                    if (a8 != 0) {
                        int unsignedShort12 = this.readUnsignedShort(a8);
                        a8 += 2;
                        while (unsignedShort12 > 0) {
                            a8 = this.a(a8 + 2, array2, true, visitField.visitAnnotation(this.readUTF8(a8, array2), n13 != 0));
                            --unsignedShort12;
                        }
                    }
                }
                while (a6 != null) {
                    final Attribute a9 = a6.a;
                    a6.a = null;
                    visitField.visitAttribute(a6);
                    a6 = a9;
                }
                visitField.visitEnd();
            }
            --unsignedShort8;
        }
        int unsignedShort13 = this.readUnsignedShort(header);
        header += 2;
        while (unsignedShort13 > 0) {
            final int h = header + 6;
            int unsignedShort14 = this.readUnsignedShort(header);
            final String utf17 = this.readUTF8(header + 2, array2);
            final String utf18 = this.readUTF8(header + 4, array2);
            String utf19 = null;
            int n14 = 0;
            int n15 = 0;
            int n16 = 0;
            int n17 = 0;
            int n18 = 0;
            Attribute a10 = null;
            int n19 = 0;
            int n20 = 0;
            int unsignedShort15 = this.readUnsignedShort(header + 6);
            header += 8;
            while (unsignedShort15 > 0) {
                final String utf20 = this.readUTF8(header, array2);
                final int \u0131nt2 = this.readInt(header + 2);
                header += 6;
                if ("Code".equals(utf20)) {
                    if (!b2) {
                        n19 = header;
                    }
                }
                else if ("Exceptions".equals(utf20)) {
                    n20 = header;
                }
                else if ("Signature".equals(utf20)) {
                    utf19 = this.readUTF8(header, array2);
                }
                else if ("Deprecated".equals(utf20)) {
                    unsignedShort14 |= 0x20000;
                }
                else if ("RuntimeVisibleAnnotations".equals(utf20)) {
                    n14 = header;
                }
                else if ("AnnotationDefault".equals(utf20)) {
                    n16 = header;
                }
                else if ("Synthetic".equals(utf20)) {
                    unsignedShort14 |= 0x41000;
                }
                else if ("RuntimeInvisibleAnnotations".equals(utf20)) {
                    n15 = header;
                }
                else if ("RuntimeVisibleParameterAnnotations".equals(utf20)) {
                    n17 = header;
                }
                else if ("RuntimeInvisibleParameterAnnotations".equals(utf20)) {
                    n18 = header;
                }
                else {
                    final Attribute a11 = this.a(array, utf20, header, \u0131nt2, array2, -1, null);
                    if (a11 != null) {
                        a11.a = a10;
                        a10 = a11;
                    }
                }
                header += \u0131nt2;
                --unsignedShort15;
            }
            String[] array5;
            if (n20 == 0) {
                array5 = null;
            }
            else {
                array5 = new String[this.readUnsignedShort(n20)];
                n20 += 2;
                for (int n21 = 0; n21 < array5.length; ++n21) {
                    array5[n21] = this.readClass(n20, array2);
                    n20 += 2;
                }
            }
            final MethodVisitor visitMethod = classVisitor.visitMethod(unsignedShort14, utf17, utf18, utf19, array5);
            Label_5747: {
                if (visitMethod != null) {
                    if (visitMethod instanceof MethodWriter) {
                        final MethodWriter methodWriter = (MethodWriter)visitMethod;
                        if (methodWriter.b.M == this && utf19 == methodWriter.g) {
                            int n22 = 0;
                            if (array5 == null) {
                                n22 = ((methodWriter.j == 0) ? 1 : 0);
                            }
                            else if (array5.length == methodWriter.j) {
                                n22 = 1;
                                for (int n23 = array5.length - 1; n23 >= 0; --n23) {
                                    n20 -= 2;
                                    if (methodWriter.k[n23] != this.readUnsignedShort(n20)) {
                                        n22 = 0;
                                        break;
                                    }
                                }
                            }
                            if (n22 != 0) {
                                methodWriter.h = h;
                                methodWriter.i = header - h;
                                break Label_5747;
                            }
                        }
                    }
                    if (n16 != 0) {
                        final AnnotationVisitor visitAnnotationDefault = visitMethod.visitAnnotationDefault();
                        this.a(n16, array2, null, visitAnnotationDefault);
                        if (visitAnnotationDefault != null) {
                            visitAnnotationDefault.visitEnd();
                        }
                    }
                    for (int n24 = 1; n24 >= 0; --n24) {
                        int a12 = (n24 == 0) ? n15 : n14;
                        if (a12 != 0) {
                            int unsignedShort16 = this.readUnsignedShort(a12);
                            a12 += 2;
                            while (unsignedShort16 > 0) {
                                a12 = this.a(a12 + 2, array2, true, visitMethod.visitAnnotation(this.readUTF8(a12, array2), n24 != 0));
                                --unsignedShort16;
                            }
                        }
                    }
                    if (n17 != 0) {
                        this.a(n17, utf18, array2, true, visitMethod);
                    }
                    if (n18 != 0) {
                        this.a(n18, utf18, array2, false, visitMethod);
                    }
                    while (a10 != null) {
                        final Attribute a13 = a10.a;
                        a10.a = null;
                        visitMethod.visitAttribute(a10);
                        a10 = a13;
                    }
                }
                if (visitMethod != null && n19 != 0) {
                    final int unsignedShort17 = this.readUnsignedShort(n19);
                    final int unsignedShort18 = this.readUnsignedShort(n19 + 2);
                    final int \u0131nt3 = this.readInt(n19 + 4);
                    n19 += 8;
                    final int n25 = n19;
                    final int n26 = n19 + \u0131nt3;
                    visitMethod.visitCode();
                    final Label[] array6 = new Label[\u0131nt3 + 2];
                    this.readLabel(\u0131nt3 + 1, array6);
                    while (n19 < n26) {
                        final int n27 = n19 - n25;
                        switch (ClassWriter.a[b[n19] & 0xFF]) {
                            case 0:
                            case 4: {
                                ++n19;
                                continue;
                            }
                            case 9: {
                                this.readLabel(n27 + this.readShort(n19 + 1), array6);
                                n19 += 3;
                                continue;
                            }
                            case 10: {
                                this.readLabel(n27 + this.readInt(n19 + 1), array6);
                                n19 += 5;
                                continue;
                            }
                            case 17: {
                                if ((b[n19 + 1] & 0xFF) == 0x84) {
                                    n19 += 6;
                                    continue;
                                }
                                n19 += 4;
                                continue;
                            }
                            case 14: {
                                n19 = n19 + 4 - (n27 & 0x3);
                                this.readLabel(n27 + this.readInt(n19), array6);
                                int n28 = this.readInt(n19 + 8) - this.readInt(n19 + 4) + 1;
                                n19 += 12;
                                while (n28 > 0) {
                                    this.readLabel(n27 + this.readInt(n19), array6);
                                    n19 += 4;
                                    --n28;
                                }
                                continue;
                            }
                            case 15: {
                                n19 = n19 + 4 - (n27 & 0x3);
                                this.readLabel(n27 + this.readInt(n19), array6);
                                int \u0131nt4 = this.readInt(n19 + 4);
                                n19 += 8;
                                while (\u0131nt4 > 0) {
                                    this.readLabel(n27 + this.readInt(n19 + 4), array6);
                                    n19 += 8;
                                    --\u0131nt4;
                                }
                                continue;
                            }
                            case 1:
                            case 3:
                            case 11: {
                                n19 += 2;
                                continue;
                            }
                            case 2:
                            case 5:
                            case 6:
                            case 12:
                            case 13: {
                                n19 += 3;
                                continue;
                            }
                            case 7:
                            case 8: {
                                n19 += 5;
                                continue;
                            }
                            default: {
                                n19 += 4;
                                continue;
                            }
                        }
                    }
                    int unsignedShort19 = this.readUnsignedShort(n19);
                    n19 += 2;
                    while (unsignedShort19 > 0) {
                        final Label label = this.readLabel(this.readUnsignedShort(n19), array6);
                        final Label label2 = this.readLabel(this.readUnsignedShort(n19 + 2), array6);
                        final Label label3 = this.readLabel(this.readUnsignedShort(n19 + 4), array6);
                        final int unsignedShort20 = this.readUnsignedShort(n19 + 6);
                        if (unsignedShort20 == 0) {
                            visitMethod.visitTryCatchBlock(label, label2, label3, null);
                        }
                        else {
                            visitMethod.visitTryCatchBlock(label, label2, label3, this.readUTF8(this.a[unsignedShort20], array2));
                        }
                        n19 += 8;
                        --unsignedShort19;
                    }
                    int n29 = 0;
                    int n30 = 0;
                    int n31 = 0;
                    int n32 = 0;
                    int n33 = 0;
                    int n34 = 0;
                    int n35 = 0;
                    int unsignedShort21 = 0;
                    int n36 = 0;
                    int n37 = 0;
                    Object[] array7 = null;
                    Object[] array8 = null;
                    boolean b4 = true;
                    Attribute a14 = null;
                    int unsignedShort22 = this.readUnsignedShort(n19);
                    n19 += 2;
                    while (unsignedShort22 > 0) {
                        final String utf21 = this.readUTF8(n19, array2);
                        if ("LocalVariableTable".equals(utf21)) {
                            if (!b3) {
                                n29 = n19 + 6;
                                int unsignedShort23 = this.readUnsignedShort(n19 + 6);
                                int n38 = n19 + 8;
                                while (unsignedShort23 > 0) {
                                    final int unsignedShort24 = this.readUnsignedShort(n38);
                                    if (array6[unsignedShort24] == null) {
                                        final Label label4 = this.readLabel(unsignedShort24, array6);
                                        label4.a |= 0x1;
                                    }
                                    final int n39 = unsignedShort24 + this.readUnsignedShort(n38 + 2);
                                    if (array6[n39] == null) {
                                        final Label label5 = this.readLabel(n39, array6);
                                        label5.a |= 0x1;
                                    }
                                    n38 += 10;
                                    --unsignedShort23;
                                }
                            }
                        }
                        else if ("LocalVariableTypeTable".equals(utf21)) {
                            n30 = n19 + 6;
                        }
                        else if ("LineNumberTable".equals(utf21)) {
                            if (!b3) {
                                int unsignedShort25 = this.readUnsignedShort(n19 + 6);
                                int n40 = n19 + 8;
                                while (unsignedShort25 > 0) {
                                    final int unsignedShort26 = this.readUnsignedShort(n40);
                                    if (array6[unsignedShort26] == null) {
                                        final Label label6 = this.readLabel(unsignedShort26, array6);
                                        label6.a |= 0x1;
                                    }
                                    array6[unsignedShort26].b = this.readUnsignedShort(n40 + 2);
                                    n40 += 4;
                                    --unsignedShort25;
                                }
                            }
                        }
                        else if ("StackMapTable".equals(utf21)) {
                            if ((n & 0x4) == 0x0) {
                                n31 = n19 + 8;
                                n32 = this.readInt(n19 + 2);
                                n33 = this.readUnsignedShort(n19 + 6);
                            }
                        }
                        else if ("StackMap".equals(utf21)) {
                            if ((n & 0x4) == 0x0) {
                                n31 = n19 + 8;
                                n32 = this.readInt(n19 + 2);
                                n33 = this.readUnsignedShort(n19 + 6);
                                b4 = false;
                            }
                        }
                        else {
                            for (int n41 = 0; n41 < array.length; ++n41) {
                                if (array[n41].type.equals(utf21)) {
                                    final Attribute read = array[n41].read(this, n19 + 6, this.readInt(n19 + 2), array2, n25 - 8, array6);
                                    if (read != null) {
                                        read.a = a14;
                                        a14 = read;
                                    }
                                }
                            }
                        }
                        n19 += 6 + this.readInt(n19 + 2);
                        --unsignedShort22;
                    }
                    if (n31 != 0) {
                        array7 = new Object[unsignedShort18];
                        array8 = new Object[unsignedShort17];
                        Label_3784: {
                            if (n6 != 0) {
                                int n42 = 0;
                                if ((unsignedShort14 & 0x8) == 0x0) {
                                    if ("<init>".equals(utf17)) {
                                        array7[n42++] = Opcodes.UNINITIALIZED_THIS;
                                    }
                                    else {
                                        array7[n42++] = this.readClass(this.header + 2, array2);
                                    }
                                }
                                int n43 = 1;
                                while (true) {
                                    final int n44 = n43;
                                    switch (utf18.charAt(n43++)) {
                                        case 'B':
                                        case 'C':
                                        case 'I':
                                        case 'S':
                                        case 'Z': {
                                            array7[n42++] = Opcodes.INTEGER;
                                            continue;
                                        }
                                        case 'F': {
                                            array7[n42++] = Opcodes.FLOAT;
                                            continue;
                                        }
                                        case 'J': {
                                            array7[n42++] = Opcodes.LONG;
                                            continue;
                                        }
                                        case 'D': {
                                            array7[n42++] = Opcodes.DOUBLE;
                                            continue;
                                        }
                                        case '[': {
                                            while (utf18.charAt(n43) == '[') {
                                                ++n43;
                                            }
                                            if (utf18.charAt(n43) == 'L') {
                                                ++n43;
                                                while (utf18.charAt(n43) != ';') {
                                                    ++n43;
                                                }
                                            }
                                            array7[n42++] = utf18.substring(n44, ++n43);
                                            continue;
                                        }
                                        case 'L': {
                                            while (utf18.charAt(n43) != ';') {
                                                ++n43;
                                            }
                                            array7[n42++] = utf18.substring(n44 + 1, n43++);
                                            continue;
                                        }
                                        default: {
                                            unsignedShort21 = n42;
                                            break Label_3784;
                                        }
                                    }
                                }
                            }
                        }
                        n35 = -1;
                        for (int n45 = n31; n45 < n31 + n32 - 2; ++n45) {
                            if (b[n45] == 8) {
                                final int unsignedShort27 = this.readUnsignedShort(n45 + 1);
                                if (unsignedShort27 >= 0 && unsignedShort27 < \u0131nt3 && (b[n25 + unsignedShort27] & 0xFF) == 0xBB) {
                                    this.readLabel(unsignedShort27, array6);
                                }
                            }
                        }
                    }
                    int n46 = n25;
                    while (n46 < n26) {
                        final int n47 = n46 - n25;
                        final Label label7 = array6[n47];
                        if (label7 != null) {
                            visitMethod.visitLabel(label7);
                            if (!b3 && label7.b > 0) {
                                visitMethod.visitLineNumber(label7.b, label7);
                            }
                        }
                        while (array7 != null && (n35 == n47 || n35 == -1)) {
                            if (!b4 || n6) {
                                visitMethod.visitFrame(-1, unsignedShort21, array7, n37, array8);
                            }
                            else if (n35 != -1) {
                                visitMethod.visitFrame(n34, n36, array7, n37, array8);
                            }
                            if (n33 > 0) {
                                int n48;
                                if (b4) {
                                    n48 = (b[n31++] & 0xFF);
                                }
                                else {
                                    n48 = 255;
                                    n35 = -1;
                                }
                                n36 = 0;
                                int unsignedShort28;
                                if (n48 < 64) {
                                    unsignedShort28 = n48;
                                    n34 = 3;
                                    n37 = 0;
                                }
                                else if (n48 < 128) {
                                    unsignedShort28 = n48 - 64;
                                    n31 = this.a(array8, 0, n31, array2, array6);
                                    n34 = 4;
                                    n37 = 1;
                                }
                                else {
                                    unsignedShort28 = this.readUnsignedShort(n31);
                                    n31 += 2;
                                    if (n48 == 247) {
                                        n31 = this.a(array8, 0, n31, array2, array6);
                                        n34 = 4;
                                        n37 = 1;
                                    }
                                    else if (n48 >= 248 && n48 < 251) {
                                        n34 = 2;
                                        n36 = 251 - n48;
                                        unsignedShort21 -= n36;
                                        n37 = 0;
                                    }
                                    else if (n48 == 251) {
                                        n34 = 3;
                                        n37 = 0;
                                    }
                                    else if (n48 < 255) {
                                        int n49 = (n6 != 0) ? unsignedShort21 : 0;
                                        for (int n50 = n48 - 251; n50 > 0; --n50) {
                                            n31 = this.a(array7, n49++, n31, array2, array6);
                                        }
                                        n34 = 1;
                                        n36 = n48 - 251;
                                        unsignedShort21 += n36;
                                        n37 = 0;
                                    }
                                    else {
                                        n34 = 0;
                                        int n51;
                                        n36 = (n51 = (unsignedShort21 = this.readUnsignedShort(n31)));
                                        n31 += 2;
                                        int n52 = 0;
                                        while (n51 > 0) {
                                            n31 = this.a(array7, n52++, n31, array2, array6);
                                            --n51;
                                        }
                                        int unsignedShort29;
                                        n37 = (unsignedShort29 = this.readUnsignedShort(n31));
                                        n31 += 2;
                                        int n53 = 0;
                                        while (unsignedShort29 > 0) {
                                            n31 = this.a(array8, n53++, n31, array2, array6);
                                            --unsignedShort29;
                                        }
                                    }
                                }
                                n35 += unsignedShort28 + 1;
                                this.readLabel(n35, array6);
                                --n33;
                            }
                            else {
                                array7 = null;
                            }
                        }
                        int n54 = b[n46] & 0xFF;
                        switch (ClassWriter.a[n54]) {
                            case 0: {
                                visitMethod.visitInsn(n54);
                                ++n46;
                                continue;
                            }
                            case 4: {
                                if (n54 > 54) {
                                    n54 -= 59;
                                    visitMethod.visitVarInsn(54 + (n54 >> 2), n54 & 0x3);
                                }
                                else {
                                    n54 -= 26;
                                    visitMethod.visitVarInsn(21 + (n54 >> 2), n54 & 0x3);
                                }
                                ++n46;
                                continue;
                            }
                            case 9: {
                                visitMethod.visitJumpInsn(n54, array6[n47 + this.readShort(n46 + 1)]);
                                n46 += 3;
                                continue;
                            }
                            case 10: {
                                visitMethod.visitJumpInsn(n54 - 33, array6[n47 + this.readInt(n46 + 1)]);
                                n46 += 5;
                                continue;
                            }
                            case 17: {
                                final int n55 = b[n46 + 1] & 0xFF;
                                if (n55 == 132) {
                                    visitMethod.visitIincInsn(this.readUnsignedShort(n46 + 2), this.readShort(n46 + 4));
                                    n46 += 6;
                                    continue;
                                }
                                visitMethod.visitVarInsn(n55, this.readUnsignedShort(n46 + 2));
                                n46 += 4;
                                continue;
                            }
                            case 14: {
                                n46 = n46 + 4 - (n47 & 0x3);
                                final int n56 = n47 + this.readInt(n46);
                                final int \u0131nt5 = this.readInt(n46 + 4);
                                final int \u0131nt6 = this.readInt(n46 + 8);
                                n46 += 12;
                                final Label[] array9 = new Label[\u0131nt6 - \u0131nt5 + 1];
                                for (int n57 = 0; n57 < array9.length; ++n57) {
                                    array9[n57] = array6[n47 + this.readInt(n46)];
                                    n46 += 4;
                                }
                                visitMethod.visitTableSwitchInsn(\u0131nt5, \u0131nt6, array6[n56], array9);
                                continue;
                            }
                            case 15: {
                                n46 = n46 + 4 - (n47 & 0x3);
                                final int n58 = n47 + this.readInt(n46);
                                final int \u0131nt7 = this.readInt(n46 + 4);
                                n46 += 8;
                                final int[] array10 = new int[\u0131nt7];
                                final Label[] array11 = new Label[\u0131nt7];
                                for (int n59 = 0; n59 < array10.length; ++n59) {
                                    array10[n59] = this.readInt(n46);
                                    array11[n59] = array6[n47 + this.readInt(n46 + 4)];
                                    n46 += 8;
                                }
                                visitMethod.visitLookupSwitchInsn(array6[n58], array10, array11);
                                continue;
                            }
                            case 3: {
                                visitMethod.visitVarInsn(n54, b[n46 + 1] & 0xFF);
                                n46 += 2;
                                continue;
                            }
                            case 1: {
                                visitMethod.visitIntInsn(n54, b[n46 + 1]);
                                n46 += 2;
                                continue;
                            }
                            case 2: {
                                visitMethod.visitIntInsn(n54, this.readShort(n46 + 1));
                                n46 += 3;
                                continue;
                            }
                            case 11: {
                                visitMethod.visitLdcInsn(this.readConst(b[n46 + 1] & 0xFF, array2));
                                n46 += 2;
                                continue;
                            }
                            case 12: {
                                visitMethod.visitLdcInsn(this.readConst(this.readUnsignedShort(n46 + 1), array2));
                                n46 += 3;
                                continue;
                            }
                            case 6:
                            case 7: {
                                final int n60 = this.a[this.readUnsignedShort(n46 + 1)];
                                final String class3 = this.readClass(n60, array2);
                                final int n61 = this.a[this.readUnsignedShort(n60 + 2)];
                                final String utf22 = this.readUTF8(n61, array2);
                                final String utf23 = this.readUTF8(n61 + 2, array2);
                                if (n54 < 182) {
                                    visitMethod.visitFieldInsn(n54, class3, utf22, utf23);
                                }
                                else {
                                    visitMethod.visitMethodInsn(n54, class3, utf22, utf23);
                                }
                                if (n54 == 185) {
                                    n46 += 5;
                                    continue;
                                }
                                n46 += 3;
                                continue;
                            }
                            case 8: {
                                final int n62 = this.a[this.readUnsignedShort(n46 + 1)];
                                int n63 = array4[this.readUnsignedShort(n62)];
                                final int n64 = this.a[this.readUnsignedShort(n62 + 2)];
                                final String utf24 = this.readUTF8(n64, array2);
                                final String utf25 = this.readUTF8(n64 + 2, array2);
                                final Handle handle = (Handle)this.readConst(this.readUnsignedShort(n63), array2);
                                final int unsignedShort30 = this.readUnsignedShort(n63 + 2);
                                final Object[] array12 = new Object[unsignedShort30];
                                n63 += 4;
                                for (int n65 = 0; n65 < unsignedShort30; ++n65) {
                                    array12[n65] = this.readConst(this.readUnsignedShort(n63), array2);
                                    n63 += 2;
                                }
                                visitMethod.visitInvokeDynamicInsn(utf24, utf25, handle, array12);
                                n46 += 5;
                                continue;
                            }
                            case 5: {
                                visitMethod.visitTypeInsn(n54, this.readClass(n46 + 1, array2));
                                n46 += 3;
                                continue;
                            }
                            case 13: {
                                visitMethod.visitIincInsn(b[n46 + 1] & 0xFF, b[n46 + 2]);
                                n46 += 3;
                                continue;
                            }
                            default: {
                                visitMethod.visitMultiANewArrayInsn(this.readClass(n46 + 1, array2), b[n46 + 3] & 0xFF);
                                n46 += 4;
                                continue;
                            }
                        }
                    }
                    final Label label8 = array6[n26 - n25];
                    if (label8 != null) {
                        visitMethod.visitLabel(label8);
                    }
                    if (!b3 && n29 != 0) {
                        int[] array13 = null;
                        if (n30 != 0) {
                            int n66;
                            int n67;
                            for (n66 = this.readUnsignedShort(n30) * 3, n67 = n30 + 2, array13 = new int[n66]; n66 > 0; array13[--n66] = n67 + 6, array13[--n66] = this.readUnsignedShort(n67 + 8), array13[--n66] = this.readUnsignedShort(n67), n67 += 10) {}
                        }
                        int unsignedShort31 = this.readUnsignedShort(n29);
                        int n68 = n29 + 2;
                        while (unsignedShort31 > 0) {
                            final int unsignedShort32 = this.readUnsignedShort(n68);
                            final int unsignedShort33 = this.readUnsignedShort(n68 + 2);
                            final int unsignedShort34 = this.readUnsignedShort(n68 + 8);
                            String utf26 = null;
                            if (array13 != null) {
                                for (int n69 = 0; n69 < array13.length; n69 += 3) {
                                    if (array13[n69] == unsignedShort32 && array13[n69 + 1] == unsignedShort34) {
                                        utf26 = this.readUTF8(array13[n69 + 2], array2);
                                        break;
                                    }
                                }
                            }
                            visitMethod.visitLocalVariable(this.readUTF8(n68 + 4, array2), this.readUTF8(n68 + 6, array2), utf26, array6[unsignedShort32], array6[unsignedShort32 + unsignedShort33], unsignedShort34);
                            n68 += 10;
                            --unsignedShort31;
                        }
                    }
                    while (a14 != null) {
                        final Attribute a15 = a14.a;
                        a14.a = null;
                        visitMethod.visitAttribute(a14);
                        a14 = a15;
                    }
                    visitMethod.visitMaxs(unsignedShort17, unsignedShort18);
                }
                if (visitMethod != null) {
                    visitMethod.visitEnd();
                }
            }
            --unsignedShort13;
        }
        classVisitor.visitEnd();
    }
    
    private void a(int a, final String s, final char[] array, final boolean b, final MethodVisitor methodVisitor) {
        final int n = this.b[a++] & 0xFF;
        int n2;
        int i;
        for (n2 = Type.getArgumentTypes(s).length - n, i = 0; i < n2; ++i) {
            final AnnotationVisitor visitParameterAnnotation = methodVisitor.visitParameterAnnotation(i, "Ljava/lang/Synthetic;", false);
            if (visitParameterAnnotation != null) {
                visitParameterAnnotation.visitEnd();
            }
        }
        while (i < n + n2) {
            int j = this.readUnsignedShort(a);
            a += 2;
            while (j > 0) {
                a = this.a(a + 2, array, true, methodVisitor.visitParameterAnnotation(i, this.readUTF8(a, array), b));
                --j;
            }
            ++i;
        }
    }
    
    private int a(int n, final char[] array, final boolean b, final AnnotationVisitor annotationVisitor) {
        int i = this.readUnsignedShort(n);
        n += 2;
        if (b) {
            while (i > 0) {
                n = this.a(n + 2, array, this.readUTF8(n, array), annotationVisitor);
                --i;
            }
        }
        else {
            while (i > 0) {
                n = this.a(n, array, null, annotationVisitor);
                --i;
            }
        }
        if (annotationVisitor != null) {
            annotationVisitor.visitEnd();
        }
        return n;
    }
    
    private int a(int n, final char[] array, final String s, final AnnotationVisitor annotationVisitor) {
        if (annotationVisitor != null) {
            Label_1221: {
                switch (this.b[n++] & 0xFF) {
                    case 68:
                    case 70:
                    case 73:
                    case 74: {
                        annotationVisitor.visit(s, this.readConst(this.readUnsignedShort(n), array));
                        n += 2;
                        break;
                    }
                    case 66: {
                        annotationVisitor.visit(s, new Byte((byte)this.readInt(this.a[this.readUnsignedShort(n)])));
                        n += 2;
                        break;
                    }
                    case 90: {
                        annotationVisitor.visit(s, (this.readInt(this.a[this.readUnsignedShort(n)]) == 0) ? Boolean.FALSE : Boolean.TRUE);
                        n += 2;
                        break;
                    }
                    case 83: {
                        annotationVisitor.visit(s, new Short((short)this.readInt(this.a[this.readUnsignedShort(n)])));
                        n += 2;
                        break;
                    }
                    case 67: {
                        annotationVisitor.visit(s, new Character((char)this.readInt(this.a[this.readUnsignedShort(n)])));
                        n += 2;
                        break;
                    }
                    case 115: {
                        annotationVisitor.visit(s, this.readUTF8(n, array));
                        n += 2;
                        break;
                    }
                    case 101: {
                        annotationVisitor.visitEnum(s, this.readUTF8(n, array), this.readUTF8(n + 2, array));
                        n += 4;
                        break;
                    }
                    case 99: {
                        annotationVisitor.visit(s, Type.getType(this.readUTF8(n, array)));
                        n += 2;
                        break;
                    }
                    case 64: {
                        n = this.a(n + 2, array, true, annotationVisitor.visitAnnotation(s, this.readUTF8(n, array)));
                        break;
                    }
                    case 91: {
                        final int unsignedShort = this.readUnsignedShort(n);
                        n += 2;
                        if (unsignedShort == 0) {
                            return this.a(n - 2, array, false, annotationVisitor.visitArray(s));
                        }
                        switch (this.b[n++] & 0xFF) {
                            case 66: {
                                final byte[] array2 = new byte[unsignedShort];
                                for (int i = 0; i < unsignedShort; ++i) {
                                    array2[i] = (byte)this.readInt(this.a[this.readUnsignedShort(n)]);
                                    n += 3;
                                }
                                annotationVisitor.visit(s, array2);
                                --n;
                                break Label_1221;
                            }
                            case 90: {
                                final boolean[] array3 = new boolean[unsignedShort];
                                for (int j = 0; j < unsignedShort; ++j) {
                                    array3[j] = (this.readInt(this.a[this.readUnsignedShort(n)]) != 0);
                                    n += 3;
                                }
                                annotationVisitor.visit(s, array3);
                                --n;
                                break Label_1221;
                            }
                            case 83: {
                                final short[] array4 = new short[unsignedShort];
                                for (int k = 0; k < unsignedShort; ++k) {
                                    array4[k] = (short)this.readInt(this.a[this.readUnsignedShort(n)]);
                                    n += 3;
                                }
                                annotationVisitor.visit(s, array4);
                                --n;
                                break Label_1221;
                            }
                            case 67: {
                                final char[] array5 = new char[unsignedShort];
                                for (int l = 0; l < unsignedShort; ++l) {
                                    array5[l] = (char)this.readInt(this.a[this.readUnsignedShort(n)]);
                                    n += 3;
                                }
                                annotationVisitor.visit(s, array5);
                                --n;
                                break Label_1221;
                            }
                            case 73: {
                                final int[] array6 = new int[unsignedShort];
                                for (int n2 = 0; n2 < unsignedShort; ++n2) {
                                    array6[n2] = this.readInt(this.a[this.readUnsignedShort(n)]);
                                    n += 3;
                                }
                                annotationVisitor.visit(s, array6);
                                --n;
                                break Label_1221;
                            }
                            case 74: {
                                final long[] array7 = new long[unsignedShort];
                                for (int n3 = 0; n3 < unsignedShort; ++n3) {
                                    array7[n3] = this.readLong(this.a[this.readUnsignedShort(n)]);
                                    n += 3;
                                }
                                annotationVisitor.visit(s, array7);
                                --n;
                                break Label_1221;
                            }
                            case 70: {
                                final float[] array8 = new float[unsignedShort];
                                for (int n4 = 0; n4 < unsignedShort; ++n4) {
                                    array8[n4] = Float.intBitsToFloat(this.readInt(this.a[this.readUnsignedShort(n)]));
                                    n += 3;
                                }
                                annotationVisitor.visit(s, array8);
                                --n;
                                break Label_1221;
                            }
                            case 68: {
                                final double[] array9 = new double[unsignedShort];
                                for (int n5 = 0; n5 < unsignedShort; ++n5) {
                                    array9[n5] = Double.longBitsToDouble(this.readLong(this.a[this.readUnsignedShort(n)]));
                                    n += 3;
                                }
                                annotationVisitor.visit(s, array9);
                                --n;
                                break Label_1221;
                            }
                            default: {
                                n = this.a(n - 3, array, false, annotationVisitor.visitArray(s));
                                break Label_1221;
                            }
                        }
                        break;
                    }
                }
            }
            return n;
        }
        switch (this.b[n] & 0xFF) {
            case 101: {
                return n + 5;
            }
            case 64: {
                return this.a(n + 3, array, true, null);
            }
            case 91: {
                return this.a(n + 1, array, false, null);
            }
            default: {
                return n + 3;
            }
        }
    }
    
    private int a(final Object[] array, final int n, int n2, final char[] array2, final Label[] array3) {
        switch (this.b[n2++] & 0xFF) {
            case 0: {
                array[n] = Opcodes.TOP;
                break;
            }
            case 1: {
                array[n] = Opcodes.INTEGER;
                break;
            }
            case 2: {
                array[n] = Opcodes.FLOAT;
                break;
            }
            case 3: {
                array[n] = Opcodes.DOUBLE;
                break;
            }
            case 4: {
                array[n] = Opcodes.LONG;
                break;
            }
            case 5: {
                array[n] = Opcodes.NULL;
                break;
            }
            case 6: {
                array[n] = Opcodes.UNINITIALIZED_THIS;
                break;
            }
            case 7: {
                array[n] = this.readClass(n2, array2);
                n2 += 2;
                break;
            }
            default: {
                array[n] = this.readLabel(this.readUnsignedShort(n2), array3);
                n2 += 2;
                break;
            }
        }
        return n2;
    }
    
    protected Label readLabel(final int n, final Label[] array) {
        if (array[n] == null) {
            array[n] = new Label();
        }
        return array[n];
    }
    
    private Attribute a(final Attribute[] array, final String s, final int n, final int n2, final char[] array2, final int n3, final Label[] array3) {
        for (int i = 0; i < array.length; ++i) {
            if (array[i].type.equals(s)) {
                return array[i].read(this, n, n2, array2, n3, array3);
            }
        }
        return new Attribute(s).read(this, n, n2, null, -1, null);
    }
    
    public int getItemCount() {
        return this.a.length;
    }
    
    public int getItem(final int n) {
        return this.a[n];
    }
    
    public int getMaxStringLength() {
        return this.d;
    }
    
    public int readByte(final int n) {
        return this.b[n] & 0xFF;
    }
    
    public int readUnsignedShort(final int n) {
        final byte[] b = this.b;
        return (b[n] & 0xFF) << 8 | (b[n + 1] & 0xFF);
    }
    
    public short readShort(final int n) {
        final byte[] b = this.b;
        return (short)((b[n] & 0xFF) << 8 | (b[n + 1] & 0xFF));
    }
    
    public int readInt(final int n) {
        final byte[] b = this.b;
        return (b[n] & 0xFF) << 24 | (b[n + 1] & 0xFF) << 16 | (b[n + 2] & 0xFF) << 8 | (b[n + 3] & 0xFF);
    }
    
    public long readLong(final int n) {
        return this.readInt(n) << 32 | (this.readInt(n + 4) & 0xFFFFFFFFL);
    }
    
    public String readUTF8(int n, final char[] array) {
        final int unsignedShort = this.readUnsignedShort(n);
        final String s = this.c[unsignedShort];
        if (s != null) {
            return s;
        }
        n = this.a[unsignedShort];
        return this.c[unsignedShort] = this.a(n + 2, this.readUnsignedShort(n), array);
    }
    
    private String a(int i, final int n, final char[] array) {
        final int n2 = i + n;
        final byte[] b = this.b;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        while (i < n2) {
            final byte b2 = b[i++];
            switch (n4) {
                case 0: {
                    final int n6 = b2 & 0xFF;
                    if (n6 < 128) {
                        array[n3++] = (char)n6;
                        continue;
                    }
                    if (n6 < 224 && n6 > 191) {
                        n5 = (char)(n6 & 0x1F);
                        n4 = 1;
                        continue;
                    }
                    n5 = (char)(n6 & 0xF);
                    n4 = 2;
                    continue;
                }
                case 1: {
                    array[n3++] = (char)(n5 << 6 | (b2 & 0x3F));
                    n4 = 0;
                    continue;
                }
                case 2: {
                    n5 = (char)(n5 << 6 | (b2 & 0x3F));
                    n4 = 1;
                    continue;
                }
            }
        }
        return new String(array, 0, n3);
    }
    
    public String readClass(final int n, final char[] array) {
        return this.readUTF8(this.a[this.readUnsignedShort(n)], array);
    }
    
    public Object readConst(final int n, final char[] array) {
        final int n2 = this.a[n];
        switch (this.b[n2 - 1]) {
            case 3: {
                return new Integer(this.readInt(n2));
            }
            case 4: {
                return new Float(Float.intBitsToFloat(this.readInt(n2)));
            }
            case 5: {
                return new Long(this.readLong(n2));
            }
            case 6: {
                return new Double(Double.longBitsToDouble(this.readLong(n2)));
            }
            case 7: {
                return Type.getObjectType(this.readUTF8(n2, array));
            }
            case 8: {
                return this.readUTF8(n2, array);
            }
            case 16: {
                return Type.getMethodType(this.readUTF8(n2, array));
            }
            default: {
                final int byte1 = this.readByte(n2);
                final int[] a = this.a;
                final int n3 = a[this.readUnsignedShort(n2 + 1)];
                final String class1 = this.readClass(n3, array);
                final int n4 = a[this.readUnsignedShort(n3 + 2)];
                return new Handle(byte1, class1, this.readUTF8(n4, array), this.readUTF8(n4 + 2, array));
            }
        }
    }
}
