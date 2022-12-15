package io.korandoru.trufflebf.language;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.instrumentation.ProvidedTags;
import com.oracle.truffle.api.instrumentation.StandardTags;

@TruffleLanguage.Registration(
        id = BFLanguage.ID,
        name = BFLanguage.NAME,
        version = BFLanguage.VERSION,
        defaultMimeType = BFLanguage.MIME_TYPE,
        characterMimeTypes = BFLanguage.MIME_TYPE
)
@ProvidedTags({
        StandardTags.StatementTag.class,
        StandardTags.RootTag.class
})
@SuppressWarnings("unused")
public class BFLanguage extends TruffleLanguage<Object> {

    public static final String ID = "bf";
    public static final String NAME = "BF";
    public static final String VERSION = "1.0";
    public static final String MIME_TYPE = "application/x-bf";

    @Override
    protected Object createContext(Env env) {
        return null;
    }

    @Override
    protected CallTarget parse(ParsingRequest request) throws Exception {
        return super.parse(request);
    }
}
