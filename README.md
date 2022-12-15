# TruffleBF

TruffleBF is a [brainfuck](https://en.wikipedia.org/wiki/Brainfuck) implementation on Truffle Framework.

## Prerequisites

- [GraalVM 22.3.0](http://www.graalvm.org/docs/getting-started/)
- Maven 3.8.6

## Build and run

```shell
mvn clean package -DskipTests
./trufflebf-native/bfnative examples/helloworld.bf
```
