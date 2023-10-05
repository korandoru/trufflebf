/**
 *
 */

module io.korandoru.trufflebf {
    requires java.base;
    requires java.logging;
    requires jdk.unsupported;
    requires org.graalvm.truffle;
    provides  com.oracle.truffle.api.provider.TruffleLanguageProvider with
            io.korandoru.trufflebf.language.BFLanguageProvider;
}