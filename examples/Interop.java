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

