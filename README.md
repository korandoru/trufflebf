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

## Interoperate

After install TruffleBF into GraalVM, you can run the interop examples.

## Interop between GraalVM and TruffleBF

```shell
javac examples/Interop.java
java examples.Interop
```
