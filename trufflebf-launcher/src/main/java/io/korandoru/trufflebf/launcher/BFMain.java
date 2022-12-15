package io.korandoru.trufflebf.launcher;

import java.io.File;
import java.io.IOException;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class BFMain {

    private static final String BF = "bf";

    public static void main(String[] args) throws IOException {
        final Source source = Source.newBuilder(BF, new File(args[0])).build();
        final Context context = Context.newBuilder(BF)
                .allowAllAccess(true)
                .in(System.in)
                .out(System.out)
                .build();
        try (context) {
            final Value result = context.eval(source);
            result.execute(new long[1024], 0);
        }
    }

}
