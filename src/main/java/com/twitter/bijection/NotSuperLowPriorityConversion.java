// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001]2q!\u0001\u0002\u0011\u0002\u0007\u0005\u0011BA\u000fO_R\u001cV\u000f]3s\u0019><\bK]5pe&$\u0018pQ8om\u0016\u00148/[8o\u0015\t\u0019A!A\u0005cS*,7\r^5p]*\u0011QAB\u0001\bi^LG\u000f^3s\u0015\u00059\u0011aA2p[\u000e\u00011c\u0001\u0001\u000b!A\u00111BD\u0007\u0002\u0019)\tQ\"A\u0003tG\u0006d\u0017-\u0003\u0002\u0010\u0019\t1\u0011I\\=SK\u001a\u0004\"!\u0005\n\u000e\u0003\tI!a\u0005\u0002\u00035M+\b/\u001a:M_^\u0004&/[8sSRL8i\u001c8wKJ\u001c\u0018n\u001c8\t\u000bU\u0001A\u0011\u0001\f\u0002\r\u0011Jg.\u001b;%)\u00059\u0002CA\u0006\u0019\u0013\tIBB\u0001\u0003V]&$\b\"B\u000e\u0001\t\u0007a\u0012\u0001\u00054s_6\u0014\u0015N[3di&|g.\u00138w+\rib\u0005\r\u000b\u0003=I\u00122a\b\u0006\"\r\u0011\u0001#\u0004\u0001\u0010\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \u0011\tE\u0011CeL\u0005\u0003G\t\u0011!bQ8om\u0016\u00148/[8o!\t)c\u0005\u0004\u0001\u0005\u000b\u001dR\"\u0019\u0001\u0015\u0003\u0003\u0005\u000b\"!\u000b\u0017\u0011\u0005-Q\u0013BA\u0016\r\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"aC\u0017\n\u00059b!aA!osB\u0011Q\u0005\r\u0003\u0006ci\u0011\r\u0001\u000b\u0002\u0002\u0005\")1G\u0007a\u0002i\u0005\u0011aM\u001c\t\u0005#UzC%\u0003\u00027\u0005\t\t\u0012*\u001c9mS\u000eLGOQ5kK\u000e$\u0018n\u001c8")
public interface NotSuperLowPriorityConversion extends SuperLowPriorityConversion
{
     <A, B> Object fromBijectionInv(final ImplicitBijection<B, A> p0);
}
