package io.korandoru.trufflebf.language;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;

@NodeInfo(language = BFLanguage.NAME, description = "The root of all BF execution trees")
public class BFRootNode extends RootNode {
    private final CharSequence instructions;

    public BFRootNode(TruffleLanguage<?> language, Source source) {
        super(language);
        this.instructions = source.getCharacters();
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return this.instructions;
    }
}
