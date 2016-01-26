(*Warning : division by 0*)
let t = 0. in
let u = 16.	in
let rec div_forb x y = x/. y in 
let z = (div_forb u t) in
print_float z
