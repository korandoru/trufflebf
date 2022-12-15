/*
 * Copyright 2022 Korandoru Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.korandoru.trufflebf.launcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class BFMain {

    private static final String BF = "bf";

    public static void main(String[] args) throws IOException {
        final Source source;
        switch (args.length) {
            case 0 -> source = Source.newBuilder(BF, new InputStreamReader(System.in), "<stdin>").build();
            case 1 -> source = Source.newBuilder(BF, new File(args[0])).build();
            default -> throw new IllegalArgumentException(Arrays.deepToString(args));
        }

        final Context context = Context.newBuilder(BF)
                .allowAllAccess(true)
                .in(System.in)
                .out(System.out)
                .build();
        try (context) {
            final Value state = context.eval(source);
            state.execute();
        }
    }

}
