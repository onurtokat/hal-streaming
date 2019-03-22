// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Option;
import java.io.Serializable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001a3q!\u0001\u0002\u0011\u0002G\u0005\u0011BA\u0002SKBT!a\u0001\u0003\u0002\u0013\tL'.Z2uS>t'BA\u0003\u0007\u0003\u001d!x/\u001b;uKJT\u0011aB\u0001\u0004G>l7\u0001A\u000b\u0003\u0015E\u0019\"\u0001A\u0006\u0011\u00051yQ\"A\u0007\u000b\u00039\tQa]2bY\u0006L!\u0001E\u0007\u0003\r\u0005s\u0017PU3g\t\u0015\u0011\u0002A1\u0001\u0014\u0005\u0005\t\u0015C\u0001\u000b\u0018!\taQ#\u0003\u0002\u0017\u001b\t9aj\u001c;iS:<\u0007C\u0001\u0007\u0019\u0013\tIRBA\u0002B]f<Qa\u0007\u0002\t\u0002q\t1AU3q!\tib$D\u0001\u0003\r\u0015\t!\u0001#\u0001 '\tq2\u0002C\u0003\"=\u0011\u0005!%\u0001\u0004=S:LGO\u0010\u000b\u00029!)AE\bC\u0002K\u0005\u0011Bo\u001c*fa>\u00038/\u00128sS\u000eDW.\u001a8u+\t1c\u000b\u0006\u0002(/B\u0019\u0001&K+\u000e\u0003y1AA\u000b\u0010\u0001W\tAAk\u001c*fa>\u00038/\u0006\u0002-sM\u0019\u0011fC\u0017\u0011\u00059\u001aT\"A\u0018\u000b\u0005A\n\u0014AA5p\u0015\u0005\u0011\u0014\u0001\u00026bm\u0006L!\u0001N\u0018\u0003\u0019M+'/[1mSj\f'\r\\3\t\u0011YJ#\u0011!Q\u0001\n]\n\u0011!\u0019\t\u0003qeb\u0001\u0001B\u0003\u0013S\t\u00071\u0003C\u0003\"S\u0011\u00051\b\u0006\u0002={A\u0019\u0001&K\u001c\t\u000bYR\u0004\u0019A\u001c\t\u000b}JC\u0011\u0001!\u0002\u000bQ|'+\u001a9\u0016\u0005\u0005sEC\u0001\"Q!\ra1)R\u0005\u0003\t6\u0011aa\u00149uS>t\u0007\u0003\u0002$Jo1s!!H$\n\u0005!\u0013\u0011a\u00029bG.\fw-Z\u0005\u0003\u0015.\u0013a\u0001J1uI\u0005$(B\u0001%\u0003!\ri\u0002!\u0014\t\u0003q9#Qa\u0014 C\u0002M\u0011\u0011A\u0011\u0005\u0006#z\u0002\u001dAU\u0001\u0003KZ\u0004B!H*8\u001b&\u0011AK\u0001\u0002\u0007\u0011\u0006\u001c(+\u001a9\u0011\u0005a2F!\u0002\n$\u0005\u0004\u0019\u0002\"\u0002\u001c$\u0001\u0004)\u0006")
public interface Rep<A>
{
    public static class ToRepOps<A> implements Serializable
    {
        private final A a;
        
        public <B> Option<A> toRep(final HasRep<A, B> ev) {
            return ev.toRep(this.a);
        }
        
        public ToRepOps(final A a) {
            this.a = a;
        }
    }
}
