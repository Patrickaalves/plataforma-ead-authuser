select * from tb_users tu ;

insert into tb_users (user_id, username, email, password, full_name, user_status,
                      user_type, phone_number, creation_date, last_update_date)
values ('b015c717-5fa3-43fb-93f6-ac07904a956f',
        'patrick',
        'patrick.oliver@example.com',
        'Ab123456#',
        'Patrick alves',
        'ACTIVE',
        'ADMIN',
        '11985963214',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
       );

insert into tb_users (user_id, username, email, password, full_name, user_status,
                      user_type, phone_number, creation_date, last_update_date)
values ('b1876a65-94cc-4b07-876a-6594ccdb076b',
        'emilysilver',
        'emilysilver@example.com',
        'Dd111333#',
        'Emily Silver',
        'ACTIVE',
        'INSTRUCTOR',
        '15963256985',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
       );

insert into tb_users (user_id, username, email, password, full_name, user_status,
                      user_type, phone_number, creation_date, last_update_date)
values ('761cd390-7b7b-40a1-9cd3-907b7b80a1f2',
        'doraci',
        'doraci@example.com',
        'Dd111333#',
        'Doraci Alves',
        'ACTIVE',
        'STUDENT',
        '15963256986',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
       );

insert into tb_users (user_id, username, email, password, full_name, user_status,
                      user_type, phone_number, creation_date, last_update_date)
values ('7404ad7c-67d1-47b7-84ad-7c67d137b725',
        'Edu',
        'edu@example.com',
        'Dd111333#',
        'Edu Alves',
        'ACTIVE',
        'USER',
        '15963256987',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
       );