(*0*)
let rec add x =
  if x >= (float_of_int 10) then 0.0 else
  add (x +. 1.0) -. 0.1 in
  print_float (add 10.1)
  

