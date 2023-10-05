/*
 * Copyright 2022 Korandoru Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

const data = [72, 101, 108, 108, 111, 32, 102, 114, 111, 109, 32, 84, 114, 117, 102, 102, 108, 101, 66, 70, 32, 105, 110, 32, 74, 97, 118, 97, 83, 99, 114, 105, 112, 116, 46, 10]
const program = Polyglot.eval('bf', '.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.>.')
program(data, 0)

Polyglot.evalFile('bf', 'examples/helloworld.bf')()
