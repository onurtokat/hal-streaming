// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.reflectasm.shaded.org.objectweb.asm;

final class Item
{
    int a;
    int b;
    int c;
    long d;
    String g;
    String h;
    String i;
    int j;
    Item k;
    
    Item() {
    }
    
    Item(final int a) {
        this.a = a;
    }
    
    Item(final int a, final Item \u0131tem) {
        this.a = a;
        this.b = \u0131tem.b;
        this.c = \u0131tem.c;
        this.d = \u0131tem.d;
        this.g = \u0131tem.g;
        this.h = \u0131tem.h;
        this.i = \u0131tem.i;
        this.j = \u0131tem.j;
    }
    
    void a(final int c) {
        this.b = 3;
        this.c = c;
        this.j = (Integer.MAX_VALUE & this.b + c);
    }
    
    void a(final long d) {
        this.b = 5;
        this.d = d;
        this.j = (Integer.MAX_VALUE & this.b + (int)d);
    }
    
    void a(final float n) {
        this.b = 4;
        this.c = Float.floatToRawIntBits(n);
        this.j = (Integer.MAX_VALUE & this.b + (int)n);
    }
    
    void a(final double n) {
        this.b = 6;
        this.d = Double.doubleToRawLongBits(n);
        this.j = (Integer.MAX_VALUE & this.b + (int)n);
    }
    
    void a(final int b, final String g, final String h, final String i) {
        this.b = b;
        this.g = g;
        this.h = h;
        this.i = i;
        switch (b) {
            case 1:
            case 7:
            case 8:
            case 16:
            case 30: {
                this.j = (Integer.MAX_VALUE & b + g.hashCode());
            }
            case 12: {
                this.j = (Integer.MAX_VALUE & b + g.hashCode() * h.hashCode());
            }
            default: {
                this.j = (Integer.MAX_VALUE & b + g.hashCode() * h.hashCode() * i.hashCode());
            }
        }
    }
    
    void a(final String g, final String h, final int n) {
        this.b = 18;
        this.d = n;
        this.g = g;
        this.h = h;
        this.j = (Integer.MAX_VALUE & 18 + n * this.g.hashCode() * this.h.hashCode());
    }
    
    void a(final int c, final int j) {
        this.b = 33;
        this.c = c;
        this.j = j;
    }
    
    boolean a(final Item \u0131tem) {
        switch (this.b) {
            case 1:
            case 7:
            case 8:
            case 16:
            case 30: {
                return \u0131tem.g.equals(this.g);
            }
            case 5:
            case 6:
            case 32: {
                return \u0131tem.d == this.d;
            }
            case 3:
            case 4: {
                return \u0131tem.c == this.c;
            }
            case 31: {
                return \u0131tem.c == this.c && \u0131tem.g.equals(this.g);
            }
            case 12: {
                return \u0131tem.g.equals(this.g) && \u0131tem.h.equals(this.h);
            }
            case 18: {
                return \u0131tem.d == this.d && \u0131tem.g.equals(this.g) && \u0131tem.h.equals(this.h);
            }
            default: {
                return \u0131tem.g.equals(this.g) && \u0131tem.h.equals(this.h) && \u0131tem.i.equals(this.i);
            }
        }
    }
}
