(*3628800*)
let rec facto x =
if x <=1 then 1 
else 
let y = x-1 in  x * (facto y) in 
print_int (facto 10)
