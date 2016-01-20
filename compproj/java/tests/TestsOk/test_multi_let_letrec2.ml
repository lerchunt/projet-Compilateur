(*0*)
let rec f x = 
	x in
let x = f 1 in
let y = f 2 in
let z = f 3 in
let rec plus x y = 
	x + y in
let rec moins x y =
	x - y in
let r = plus x y in
let res = moins z r in
print_int res
