#!/usr/bin/env bash

# Copyright 2022-2023 Korandoru Contributors
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

declare -r GRAALVM_VERSION="${1:?The first argument must be GraalVM version.}"
declare -r OS_DETECTED_ARCH="${2:?The second argument must be machine arch.}"

case $OS_DETECTED_ARCH in
x86_64)
  OS_RESOLVED_ARCH=amd64
  ;;
aarch_64)
  OS_RESOLVED_ARCH=aarch64
  ;;
*)
  echo "Unsupported arch: $OS_DETECTED_ARCH"
  exit 1
esac

readonly COMPONENT_DIR="component_temp_dir"
readonly LANGUAGE_PATH="$COMPONENT_DIR/languages/bf"

rm -rf COMPONENT_DIR

mkdir -p "$LANGUAGE_PATH"
cp ../trufflebf-language/target/bf-language.jar "$LANGUAGE_PATH"

mkdir -p "$LANGUAGE_PATH/launcher"
cp ../trufflebf-launcher/target/bf-launcher.jar "$LANGUAGE_PATH/launcher/"

mkdir -p "$LANGUAGE_PATH/bin"
cp ../trufflebf-native/bf $LANGUAGE_PATH/bin/

touch "$LANGUAGE_PATH/native-image.properties"
mkdir -p "$COMPONENT_DIR/META-INF"
{
    echo "Bundle-Name: Truffle BF";
    echo "Bundle-Symbolic-Name: io.korandoru.trufflebf";
    echo "Bundle-Version: $GRAALVM_VERSION";
    echo "Bundle-RequireCapability: org.graalvm; filter:=\"(&(graalvm_version=$GRAALVM_VERSION)(os_arch=$OS_RESOLVED_ARCH))\"";
} > "$COMPONENT_DIR/META-INF/MANIFEST.MF"

(
cd $COMPONENT_DIR || exit 1
"$JAVA_HOME"/bin/jar cfm ../bf-component.jar META-INF/MANIFEST.MF .
echo "bin/bf = ../languages/bf/bin/bf" >> META-INF/symlinks
"$JAVA_HOME"/bin/jar uf ../bf-component.jar META-INF/symlinks
{
    echo 'languages/bf/bin/bf = rwxr-xr-x'
} > META-INF/permissions
"$JAVA_HOME"/bin/jar uf ../bf-component.jar META-INF/permissions
)
