(*6*)
let x = 1 in
let y = 2 in
let (t,p) = (x,y) in
let z = t + p in
let rec add_tuples x y z w = (x+y,z+w) in
let (f,g) = (add_tuples t p x y) in
let w= f+g in 
print_int w
