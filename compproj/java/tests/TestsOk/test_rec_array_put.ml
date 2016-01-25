(*15*)
let rec p1 x = 
	let rec p2 y = x.(0)+y in
	p2 in 

let l = 13 in
let tab =Array.create(3)(6) in
let u = (tab).(0)<-l in
let o = (p1 tab) 2 in
print_int o

