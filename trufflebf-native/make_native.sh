#!/usr/bin/env bash

if [[ $SL_BUILD_NATIVE == "false" ]]; then
    echo "Skipping the native image build because SL_BUILD_NATIVE is set to false."
    exit 0
fi
"$JAVA_HOME"/bin/native-image \
    --macro:truffle --no-fallback --initialize-at-build-time \
    -cp ../trufflebf-language/target/bf-language.jar:../trufflebf-launcher/target/bf-launcher.jar \
    io.korandoru.trufflebf.launcher.BFMain \
    bfnative
