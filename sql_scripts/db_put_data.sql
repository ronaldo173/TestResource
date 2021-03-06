insert into testing.roles (role, description) values ('admin','has access to all parts of application');
insert into testing.roles (role, description) values ('user','has access to SOME of application');

# Users password stored in MD5(Message Digest 5) ---> login password
 # admin -> admin
insert into testing.users (login, password, first_name, last_name, email) values ('admin', '21232f297a57a5a743894a0e4a801fc3',
 'Alexander', 'Efimov', 'ronaldo173@email.ua' );
 # user1 -> user1
 insert into testing.users (login, password, first_name, last_name, email) values ('user1', '24c9e15e52afc47c225b757e7bee1f9d',
 'Dmitriy', 'Medvedev', 'medvedev@mail.ru' );
   # user2 -> user2
  insert into testing.users (login, password, first_name, last_name, email) values ('user2', '7e58d63b60197ceb55a1c487989a3720',
 'Steve ', 'Jobs', 'Jobs@apple.com' );

INSERT INTO users_roles (login, role) VALUES ('admin','admin');
INSERT INTO users_roles (login, role) VALUES ('user1','user');
INSERT INTO users_roles (login, role) VALUES ('user2','user');

INSERT INTO questions (id, body) VALUES ('1','You have to put the car in reverse. Now, back the car ......... and take a right turn after that.');
INSERT INTO answers (variant, body, question_id) VALUES ('a','off', 1);
INSERT INTO answers (variant, body, question_id) VALUES ('b','over', 1);
INSERT INTO answers (variant, body, question_id) VALUES ('c','down', 1);
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('d','up', 1, 1);

INSERT INTO questions (id, body) VALUES ('2','If you want to disagree, that\'s fine. But, you have to back ......... your arguments with some support.');
INSERT INTO answers (variant, body, question_id) VALUES ('a','over', 2);
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('b','up', 2, 1);
INSERT INTO answers (variant, body, question_id) VALUES ('c','on', 2);
INSERT INTO answers (variant, body, question_id) VALUES ('d','off', 2);

INSERT INTO questions (id, body) VALUES ('3','I don\'t want anyone to use the front door. I want everyone to use the back ......... when they come to visit us');
INSERT INTO answers (variant, body, question_id) VALUES ('a','book', 3);
INSERT INTO answers (variant, body, question_id) VALUES ('b','mountain', 3);
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('c','entrance', 3, 1);
INSERT INTO answers (variant, body, question_id) VALUES ('d','plate',3);

INSERT INTO questions (id, body) VALUES ('4','The company owes me a lot of back ......... due to all of the overtime hours I worked last year. I hope they give it to me soon, or I\'ll have to get a lawyer.');
INSERT INTO answers (variant, body, question_id) VALUES ('a','logs', 4);
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('b','pay', 4, 1);
INSERT INTO answers (variant, body, question_id) VALUES ('c','money', 4);
INSERT INTO answers (variant, body, question_id) VALUES ('d','labor', 4);

INSERT INTO questions (id, body) VALUES ('5','I have to ask my father for financial ......... until my credit score improves. I hope he will co-sign that car loan for me. ');
INSERT INTO answers (variant, body, question_id) VALUES ('a','backs', 5);
INSERT INTO answers (variant, body, question_id) VALUES ('b','backed', 5);
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('c','backing', 5, 1);
INSERT INTO answers (variant, body, question_id) VALUES ('d','back', 5);


INSERT INTO subjects (subject) VALUES ('English');
INSERT INTO subjects (subject) VALUES ('География');
INSERT INTO subjects (subject) VALUES ('Development');

INSERT INTO tests (name, subject_id, description, difficulty, add_date, pass_time) VALUES ('\'Back\' Common Usages', 1, 
'Intermediate level → \'Back\' Common Usages (1)', '3', STR_TO_DATE('12-08-2016', '%d-%m-%Y'), 150);

INSERT INTO test_questions (test_id, question_id) VALUES (1 ,1);
INSERT INTO test_questions (test_id, question_id) VALUES (1 ,2);
INSERT INTO test_questions (test_id, question_id) VALUES (1 ,3);
INSERT INTO test_questions (test_id, question_id) VALUES (1 ,4);
INSERT INTO test_questions (test_id, question_id) VALUES (1 ,5);


#next test ->java
INSERT INTO questions (id, body) VALUES ('6','Можно ли описать конструкторы в абстрактном классе? ');
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('a','Можно', 6, 1);
INSERT INTO answers (variant, body, question_id) VALUES ('b','Нельзя', 6);
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('c','нельзя напрямую ими воспользоваться (потому что запрещено создавать объекты абстрактного класса)', 6, 1);

INSERT INTO questions (id, body) VALUES ('7','Может ли быть класс абстрактным без единого абстрактного метода? А наоборот? ');
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('a','Может, может', 7, 0);
INSERT INTO answers (variant, body, question_id) VALUES ('b','Нет, нет', 7);
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('c','Класс может быть абстрактным без единого абстрактного метода, если у него указан модификатор abstract. Класс имеющий хоть один абстрактный метод обязан быть абстрактным классом', 7, 1);

INSERT INTO questions (id, body) VALUES ('8','При определении метода в интерфейсе подразумевается модификатор public и abstract. Можно ли при определении интерфейса явно указать модификатор abstract?');
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('a','Можно', 8, 1);
INSERT INTO answers (variant, body, question_id) VALUES ('b','Нельзя', 8);
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('c',' При определении метода в интерфейсе подразумевается модификатор public и abstract, но ИХ также можно указать явно', 8, 1);

INSERT INTO questions (id, body) VALUES ('9','Что такое контейнер сервлетов? ');
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('a','Программа, управляющая жизненным циклом сервлетов.', 9, 1);
INSERT INTO answers (variant, body, question_id) VALUES ('b','Одна из коллекци Java, как ArrayList...', 9);
INSERT INTO answers (variant, body, question_id) VALUES ('c','Контейнера сервлетов не существует', 9);

INSERT INTO questions (id, body) VALUES ('10','Передача параметра по ссылке или по значению? ');
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('a','В Java все передается по значению', 10, 1);
INSERT INTO answers (variant, body, question_id) VALUES ('b','В Java все передается по ссылке', 10);
INSERT INTO answers (variant, body, question_id) VALUES ('c','Примитивы - по значению, ссылочные типы - по ссылке', 10);

INSERT INTO tests (name, subject_id, description, difficulty, add_date, pass_time) VALUES ('Основы Java core+ee', 3, 
'Some questions from java core + simple enterprise', '2', STR_TO_DATE('13-08-2016', '%d-%m-%Y'), 150);

INSERT INTO test_questions (test_id, question_id) VALUES (2 ,6);
INSERT INTO test_questions (test_id, question_id) VALUES (2 ,7);
INSERT INTO test_questions (test_id, question_id) VALUES (2 ,8);
INSERT INTO test_questions (test_id, question_id) VALUES (2 ,9);
INSERT INTO test_questions (test_id, question_id) VALUES (2 ,10);


#geogr test
INSERT INTO questions (id, body) VALUES ('11','Столицей Буркина-Фасо является город');
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('a','Бамако', 11, 0);
INSERT INTO answers (variant, body, question_id) VALUES ('b','Аккра', 11);
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('c','Уагадугу', 11, 1);

INSERT INTO questions (id, body) VALUES ('12','Столицей Южной Кореи является город');
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('a','Пхеньян', 12, 0);
INSERT INTO answers (variant, body, question_id) VALUES ('b','Тайбей', 12);
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('c','Сеул', 12, 1);

INSERT INTO questions (id, body) VALUES ('13','Столицей Турции  является город');
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('a','Анталия', 13, 0);
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('b','Анкара', 13, 1);
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('c','Стамбул', 13, 0);

INSERT INTO tests (name, subject_id, description, difficulty, add_date, pass_time) VALUES ('Столицы мира', 2, 
'Выбор столиц мира', '1', STR_TO_DATE('22-08-2016', '%d-%m-%Y'), 60);

INSERT INTO test_questions (test_id, question_id) VALUES (3 ,11);
INSERT INTO test_questions (test_id, question_id) VALUES (3 ,12);
INSERT INTO test_questions (test_id, question_id) VALUES (3 ,13);

INSERT INTO tests (name, subject_id, description, difficulty, add_date, pass_time) VALUES ('Столицы Европы', 2, 
'Выбор столиц мира', '1', STR_TO_DATE('25-08-2016', '%d-%m-%Y'), 10);
INSERT INTO test_questions (test_id, question_id) VALUES (4 ,13);


#new test 
INSERT INTO tests (name, subject_id, description, difficulty, add_date, pass_time) VALUES ('Выбор столиц', 2, 
'Выбор столиц Азии', '4', STR_TO_DATE('29-08-2016', '%d-%m-%Y'), 20);
INSERT INTO questions (id, body) VALUES ('14','Столицей Таиланд  является город');
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('a','Бангкок', 14, 1);
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('b','Бандар-Сери-Бегаван', 14, 0);
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('c','Дамаск', 14, 0);
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('d','Вьентьян', 14, 0);

INSERT INTO questions (id, body) VALUES ('15','Столицей Индонезии  является город');
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('a','Ереван', 15, 0);
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('b','Бандар-Сери-Бегаван', 15, 0);
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('c','Иерусалим', 15, 0);
INSERT INTO answers (variant, body, question_id, is_correct) VALUES ('d','Джакарта', 15, 1);
INSERT INTO test_questions (test_id, question_id) VALUES (5 ,14);
INSERT INTO test_questions (test_id, question_id) VALUES (5 ,15);