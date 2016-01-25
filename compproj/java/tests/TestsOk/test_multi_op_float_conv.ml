(*1*)
let x = 3.5 in
  let y = 2.3 in
    let z = y /. x in
	let w = z *. float_of_int (8) in
	  let u = w -. x in
	    print_int (int_of_float (u))

