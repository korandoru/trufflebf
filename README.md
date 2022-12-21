# TruffleBF

TruffleBF is a [brainfuck](https://en.wikipedia.org/wiki/Brainfuck) implementation on Truffle Framework.

## Prerequisites

- [GraalVM 22.3.0](http://www.graalvm.org/docs/getting-started/)
- Maven 3.8.6

## Build and run

```shell
mvn clean package -DskipTests
./trufflebf-native/bf examples/helloworld.bf
```

## Install TruffleBF into GraalVM

```shell
mvn package -DskipTests
gu install -L trufflebf-component/bf-component.jar 
```

After install TruffleBF into GraalVM, you can run the interop examples.

### Interop between Java and TruffleBF

```shell
javac examples/Interop.java
java examples.Interop
```

### Interop between JavaScript and TruffleBF

```shell
gu install nodejs
node --polyglot --jvm examples/interop.js
```

### Interop between C and TruffleBF

```shell
gu install llvm
clang -g -O1 -c -emit-llvm -I$JAVA_HOME/languages/llvm/include examples/interop.c
lli --polyglot --jvm interop.bc
```

### Interop between Ruby and TruffleBF

```
gu install ruby
ruby --experimental-options --single-threaded --polyglot --jvm examples/interop.rb
```
