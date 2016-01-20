(*6*)
let rec fct x y z = x + y - z in
let y = 5 in
let rec f x = x + 1 in
let rec g y = f(f y) + (fct (fct 4 5 6) 2 3) in
let c = (f 10) - y in
let a =
if (c > (f 5)) 
	then (f 5)
	else (f 10) - (g 5) in
	print_int (a)
