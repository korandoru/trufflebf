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
        return new BFState(this.instructions);
    }
}
