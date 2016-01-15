(*5*)
let rec inc x = x + 1.0 in
let rec plus2 x = x + 2 in
let x = plus2 (inc 2) in
print_int x
