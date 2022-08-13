DELETE FROM USER;
INSERT INTO USER(username, password) VALUES('miw', 'miw');
INSERT INTO USER(username, password) VALUES('test', 'pass');

DELETE FROM ORDER_ITEMS;
DELETE FROM BUY_ITEM_DETAILS;
DELETE FROM ITEM;
INSERT INTO ITEM(id, name, description, price) VALUES(101, 'Book', 'The Book', 100);
INSERT INTO ITEM(id, name, description, price) VALUES(102, 'Car', 'Lexus', 1000);
INSERT INTO ITEM(id, name, description, price) VALUES(103, 'Chocolate', 'KitKat', 10);
INSERT INTO ITEM(id, name, description, price) VALUES(104, 'Rose', 'flower', 1);
