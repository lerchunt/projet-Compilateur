(*15*)
let rec facto x p=
	if x=1 then p
	else  facto (x-1 x*p) in 
print_int (facto 10 1)
