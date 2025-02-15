do 
$$	
	begin
	for i in 65..67 loop
		for j in 1..5 loop
			insert into place (name) values (chr(i)||j);
		end loop;
	end loop;
	end;
$$