(*type error : int != boolean*)
let x = 1 in 
let z = 1 in 
let y = (Array.create 1 (true)) in
let r = y.(1) in 
let a = r + x in 
print_int x
