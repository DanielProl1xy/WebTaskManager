ALTER TABLE task 
ADD foreign key(executor) references tbuser(id)