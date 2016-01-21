(*Syntax Error at <line 4, column 28>*)
let x = 1 in 
let z = 1 in 
let y = (Array.create (5) (+,9.0,6,x,5)) in
let r = y.(1) in 
let a = r + x in 
print_int x
