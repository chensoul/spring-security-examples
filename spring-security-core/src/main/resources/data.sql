insert into users (username, password, enabled)
  values ('user',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    1);

insert into authorities (username, authority)
  values ('user', 'ROLE_USER');

insert into users (username, password, enabled)
  values ('admin',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    1);

insert into authorities (username, authority)
  values ('admin', 'ROLE_ADMIN');
insert into authorities (username, authority)
  values ('admin', 'ROLE_USER');

