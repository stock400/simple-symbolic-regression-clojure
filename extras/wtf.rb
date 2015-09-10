

a_script = ":* :- :- :+ 28 :÷
:÷ :- :* :- :+ :÷ 14 :+ 3 :- :- :÷ 8 :÷ :x :* :* :÷ :x :+ :* :+ :x :- :* :x 34
40 :* 70 :* :x :+ :+ :x :÷ :+ :* 14 :x :* :* :x :÷ :x :x :+ :+ :÷ 93 :x :+ :-
:- :+ :* :* :- :- :x :- 69 :x :* :÷ :x 59 :+ :- :* :- :x :- :÷ :* :* :- :- :-
:* :* :+ :x 83 :* :- :- :- :x :÷ :- 32 :+ :+ 4 19 :÷ :- :* :+ :* :÷ :÷ :x 6 :*
:÷ :* :- :* :÷ :x :x :- 79 :+ 94 :÷ :- :* :÷ :x :- 32 :÷ :* 4 :- :x :* 75 :÷ :-
:-"




def run_this(script,x)
  stack = []
  wtf_stack = []

  tokens = script.split(/\s/)
  tokens.each do |token|
    case token
    when /\d+/
      stack.push token.to_f
      wtf_stack.push token
    when /:\+/
      if stack.length > 1
        arg1,arg2 = stack.pop(2)
        stack.push(arg1+arg2)

        wtf1,wtf2 = wtf_stack.pop(2)
        wtf_stack.push "(#{wtf1}+#{wtf2})"
      end
    when /:\-/
      if stack.length > 1
        arg1,arg2 = stack.pop(2)
        stack.push(arg2-arg1)

        wtf1,wtf2 = wtf_stack.pop(2)
        wtf_stack.push "(#{wtf2}-#{wtf1})"
      end
    when /:\*/
      if stack.length > 1
        arg1,arg2 = stack.pop(2)
        stack.push(arg1*arg2)

        wtf1,wtf2 = wtf_stack.pop(2)
        wtf_stack.push "(#{wtf1}*#{wtf2})"
      end
    when /:\÷/
      if stack.length > 1
        arg1,arg2 = stack.pop(2)
        if (arg2 != 0)
          stack.push(arg1/arg2)

          wtf1,wtf2 = wtf_stack.pop(2)
          wtf_stack.push "(#{wtf1}/#{wtf2})"
        else
          stack.push(arg1,arg2) # we do not consume arguments if it would lead to div0
        end
      end
    when /:x/
      stack.push(x.to_f)
      wtf_stack.push "x"
    else
      puts "that's not right!"
    end
  end
  return {:stack => stack, :wtf => wtf_stack}
end


try_me = run_this(a_script,0.0)
puts try_me[:stack].inspect
puts try_me[:wtf].inspect



def print_comparison_table(script)
  range = 2 * (Math.const_get :PI)
  (0..32).each do |i|
    x = range*i/32.0
    result = run_this(script,x)[:stack][-1]
    puts "#{x},#{Math.sin(x)},#{result},#{(Math.sin(x) - result).abs}"
  end
end

print_comparison_table(a_script)