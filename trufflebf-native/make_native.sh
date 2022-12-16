#!/usr/bin/env bash

# Copyright 2022 Korandoru Contributors
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.


if [[ $BF_BUILD_NATIVE == "false" ]]; then
    echo "Skipping the native image build because BF_BUILD_NATIVE is set to false."
    exit 0
fi

"$JAVA_HOME"/bin/native-image \
    --macro:truffle --no-fallback --initialize-at-build-time \
    -cp ../trufflebf-language/target/bf-language.jar:../trufflebf-launcher/target/bf-launcher.jar \
    io.korandoru.trufflebf.launcher.BFMain \
    bfnative
