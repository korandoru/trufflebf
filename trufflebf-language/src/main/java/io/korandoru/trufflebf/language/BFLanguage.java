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
    protected CallTarget parse(ParsingRequest request) {
        final BFRootNode node = new BFRootNode(this, request.getSource());
        return node.getCallTarget();
    }
}
