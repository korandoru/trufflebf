package io.korandoru.trufflebf.language;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(InteropLibrary.class)
@SuppressWarnings("unused")
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
    boolean hasLanguage() {
        return true;
    }

    @ExportMessage
    Class<? extends TruffleLanguage<?>> getLanguage() {
        return BFLanguage.class;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    Object toDisplayString(boolean allowSideEffects) {
        return "State";
    }

    @ExportMessage
    boolean isExecutable() {
        return true;
    }

    @ExportMessage
    Object execute(Object[] arguments) throws UnsupportedMessageException {
        return this.instructions;
    }
}
