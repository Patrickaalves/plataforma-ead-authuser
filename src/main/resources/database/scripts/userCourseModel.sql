select * from tb_users_course tuc ;

select * from tb_users tu ;

INSERT INTO public.tb_users_course
(id, course_id, user_user_id)
VALUES(gen_random_uuid(), '30a3245e-e2f7-4d55-a324-5ee2f74d5536', '761cd390-7b7b-40a1-9cd3-907b7b80a1f2');

INSERT INTO public.tb_users_course
(id, course_id, user_user_id)
VALUES(gen_random_uuid(), 'f8a4b0a0-a2aa-48e2-a4b0-a0a2aa58e2a3', '761cd390-7b7b-40a1-9cd3-907b7b80a1f2');

INSERT INTO public.tb_users_course
(id, course_id, user_user_id)
VALUES(gen_random_uuid(), '17d584e5-cb6f-4a2a-9584-e5cb6f4a2a62', '761cd390-7b7b-40a1-9cd3-907b7b80a1f2');