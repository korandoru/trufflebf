puts 'Input 2 non-negative numbers...'
print 'First number: '
x = gets.chomp.to_i
print 'Second number: '
y = gets.chomp.to_i

add = Polyglot.eval('bf', '>[-<+>]<')
res = add.call([x, y], 0)
puts 'The sum of these two numbers is: ' + res['data'][res['dp']].to_s
