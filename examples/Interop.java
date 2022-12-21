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

package examples;

import org.graalvm.polyglot.*;
import org.graalvm.polyglot.proxy.*;

public class Interop {
    private static final String BF = "bf";
    public static void main(String[] args) {
        System.out.print("Your name: ");
        Context context = Context.newBuilder(BF)
            .allowAllAccess(true)
            .in(System.in).out(System.out)
            .build();
        Value value = context.eval(BF, ">+[++++++++++>,----------]");
        Value state = value.execute();
        Value data = state.getMember("data");

        System.out.print("Hello ");
        int i = 2;
        char ch = (char)data.getArrayElement(i).asLong();
        while (ch > 0) {
            System.out.print(ch);
            i = i + 1;
            ch = (char)data.getArrayElement(i).asLong();
        }
        System.out.println("!");
        System.out.println(state.toString());
    }
}

