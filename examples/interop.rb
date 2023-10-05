# Copyright 2022 Korandoru Contributors
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

puts 'Input 2 non-negative numbers...'
print 'First number: '
x = gets.chomp.to_i
print 'Second number: '
y = gets.chomp.to_i

add = Polyglot.eval('bf', '>[-<+>]<')
res = add.call([x, y], 0)
puts "The sum of these two numbers is: #{res.data[res.dp]}"
