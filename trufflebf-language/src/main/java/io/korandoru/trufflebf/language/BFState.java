package io.korandoru.trufflebf.language;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import java.util.Arrays;

@ExportLibrary(InteropLibrary.class)
public class BFState implements TruffleObject {

    // code segment
    private final CharSequence instructions;
    private final int end;
    private int ip;

    // data segment
    private long[] data;
    private int dp;

    public BFState(CharSequence instructions) {
        this.instructions = instructions;
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
        // reset instruction state
        this.ip = 0;

        // reset data state
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
        return this.instructions;
    }
}