(*1*)
let y = 1+0 in
let rec inc x = 
	x + y in
let rec double x = 
	x * 2 in
let rec sub1 x = 
	x - 1 in
print_int (inc(double(sub1(1))))
