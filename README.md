# TruffleBF

TruffleBF is a [brainfuck](https://en.wikipedia.org/wiki/Brainfuck) implementation on Truffle Framework.

## Prerequisites

- [GraalVM for JDK 17](https://www.graalvm.org/jdk17/docs/getting-started/)
- Maven 3.8.6

## Build and run

```shell
mvn clean package -DskipTests
./trufflebf-native/bf examples/helloworld.bf
```

## Interop

See https://github.com/oracle/graal/issues/6852 and https://github.com/oracle/graal/issues/6855.
