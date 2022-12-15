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

package io.korandoru.trufflebf.language;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

@ExportLibrary(InteropLibrary.class)
public class BFState implements TruffleObject {

    // code segment
    private final Map<Integer, Integer> jump;
    private final Deque<Integer> stack;
    private final CharSequence instructions;
    private final int end;
    private int ip;

    // data segment
    private long[] data;
    private int dp;

    @CompilerDirectives.TruffleBoundary
    public BFState(CharSequence instructions) {
        this.instructions = instructions;
        this.jump = new HashMap<>();
        this.stack = new ArrayDeque<>();
        this.end = instructions.length();
    }

    @ExportMessage
    @SuppressWarnings("unused")
    boolean hasLanguage() {
        return true;
    }

    @ExportMessage
    @SuppressWarnings("unused")
    Class<? extends TruffleLanguage<?>> getLanguage() {
        return BFLanguage.class;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    @SuppressWarnings("unused")
    Object toDisplayString(boolean allowSideEffects) {
        return "State";
    }

    @ExportMessage
    @SuppressWarnings("unused")
    boolean isExecutable() {
        return true;
    }

    @ExportMessage
    @SuppressWarnings("unused")
    Object execute(Object[] arguments) throws UnsupportedMessageException {
        // 1. reset instruction state
        this.ip = 0;

        // 2. reset data state
        switch (arguments.length) {
            case 0 -> {
                this.data = new long[1024];
                this.dp = 0;
            }
            case 2 -> resetDataState(arguments[0], arguments[1]);
            default -> {
                final Throwable cause = new IllegalArgumentException(Arrays.deepToString(arguments));
                throw UnsupportedMessageException.create(cause);
            }
        }

        // 3. run state machine
        return eval();
    }

    @CompilerDirectives.TruffleBoundary
    private void resetDataState(Object segment, Object pointer) throws UnsupportedMessageException {
        if (segment instanceof TruffleObject object) {
            InteropLibrary library = InteropLibrary.getFactory().getUncached();
            int size = (int) library.getArraySize(object);
            this.data = new long[size];
            for (int i = 0; i < size; i++) {
                try {
                    this.data[i] = ((Number) library.readArrayElement(object, i)).longValue();
                } catch (InvalidArrayIndexException e) {
                    throw UnsupportedMessageException.create(e);
                }
            }

            if (pointer instanceof Number number) {
                this.dp = number.intValue();
                return;
            }
        }

        final Throwable cause = new IllegalArgumentException(Arrays.deepToString(new Object[]{segment, pointer}));
        throw UnsupportedMessageException.create(cause);
    }

    @CompilerDirectives.TruffleBoundary
    private Object eval() {
        while (ip < end) {
            switch (instructions.charAt(ip)) {
                case '>' -> dp++;
                case '<' -> dp--;
                case '+' -> data[dp]++;
                case '-' -> data[dp]--;
                case '.' -> System.out.print((char) data[dp]);
                case ',' -> {
                    try {
                        data[dp] = System.in.read();
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
                case '[' -> {
                    if (!jump.containsKey(ip)) {
                        stack.addLast(ip);
                    }
                    if (data[dp] == 0) {
                        if (jump.containsKey(ip)) {
                            ip = jump.get(ip) + 1;
                            continue;
                        } else {
                            while (instructions.charAt(ip) != ']') {
                                ip++;
                            }

                            int m = stack.removeLast();
                            jump.put(m, ip);
                            jump.put(ip, m);
                        }
                    }
                }
                case ']' -> {
                    if (!jump.containsKey(ip)) {
                        int m = stack.removeLast();
                        jump.put(m, ip);
                        jump.put(ip, m);
                    }
                    if (data[dp] != 0) {
                        ip = jump.get(ip) + 1;
                        continue;
                    }
                }
            }
            ip++;
        }

        return new BFObject(dp, ip, instructions, data);
    }
}
