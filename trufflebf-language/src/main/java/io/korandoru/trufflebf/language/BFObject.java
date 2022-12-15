package io.korandoru.trufflebf.language;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import java.util.Arrays;

@ExportLibrary(InteropLibrary.class)
public class BFObject implements TruffleObject {

    private final int dp;
    private final int ip;
    private final LongSeq data;
    private final CharSeq ins;

    public BFObject(int dp, int ip, CharSequence ins, long[] data) {
        this.dp = dp;
        this.ip = ip;
        this.data = new LongSeq(data);
        this.ins = new CharSeq(ins);
    }

    @ExportMessage
    @SuppressWarnings("unused")
    Object readMember(String member) {
        return switch (member) {
            case "dp" -> dp;
            case "ip" -> ip;
            case "data" -> data;
            case "ins" -> ins;
            default -> throw new IllegalArgumentException("No such member " + member);
        };
    }

    @ExportMessage
    @SuppressWarnings("unused")
    boolean hasMembers() {
        return true;
    }

    @ExportMessage
    @SuppressWarnings("unused")
    boolean isMemberReadable(String member) {
        return switch (member) {
            case "dp", "ip", "ins", "data" -> true;
            default -> false;
        };
    }

    @ExportMessage
    @SuppressWarnings("unused")
    Object getMembers(boolean includeInternal) {
        return null;
    }

    @ExportLibrary(InteropLibrary.class)
    static class LongSeq implements TruffleObject {
        private final long[] data;

        LongSeq(long[] data) {
            this.data = data;
        }

        @ExportMessage
        @SuppressWarnings("unused")
        boolean hasArrayElements() {
            return true;
        }

        @ExportMessage
        @SuppressWarnings("unused")
        Object readArrayElement(long index) {
            return data[(int) index];
        }

        @ExportMessage
        @SuppressWarnings("unused")
        long getArraySize() {
            return data.length;
        }

        @ExportMessage
        @SuppressWarnings("unused")
        boolean isArrayElementReadable(long index) {
            return index < data.length;
        }
    }

    @ExportLibrary(InteropLibrary.class)
    static class CharSeq implements TruffleObject {
        private final CharSequence ins;
        private final long length;

        CharSeq(CharSequence ins) {
            this.ins = ins;
            this.length = ins.length();
        }

        @ExportMessage
        @SuppressWarnings("unused")
        boolean hasArrayElements() {
            return true;
        }

        @ExportMessage
        @SuppressWarnings("unused")
        Object readArrayElement(long index) {
            return ins.charAt((int) index);
        }

        @ExportMessage
        @SuppressWarnings("unused")
        long getArraySize() {
            return length;
        }

        @ExportMessage
        @SuppressWarnings("unused")
        boolean isArrayElementReadable(long index) {
            return index < length;
        }
    }

    @ExportMessage
    @SuppressWarnings("unused")
    boolean isString() {
        return true;
    }

    @ExportMessage
    @SuppressWarnings("unused")
    String asString() {
        return toString();
    }

    @ExportMessage
    @SuppressWarnings("unused")
    Object toDisplayString(boolean allowSideEffects) {
        return toString();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return "BFObject{" +
                "dp=" + dp +
                ", ip=" + ip +
                ", data=" + Arrays.toString(data.data) +
                ", ins=" + ins.ins +
                '}';
    }
}
