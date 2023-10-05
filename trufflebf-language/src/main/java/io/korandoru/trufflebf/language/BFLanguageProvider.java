package io.korandoru.trufflebf.language;

import com.oracle.truffle.api.provider.TruffleLanguageProvider;
import java.util.Collection;
import java.util.List;

public class BFLanguageProvider extends TruffleLanguageProvider {
    @Override
    protected String getLanguageClassName() {
        return BFLanguage.ID;
    }

    @Override
    protected Object create() {
        return null;
    }

    @Override
    protected Collection<String> getServicesClassNames() {
        return null;
    }

    @Override
    protected List<?> createFileTypeDetectors() {
        return null;
    }
}
