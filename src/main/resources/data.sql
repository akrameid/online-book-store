INSERT INTO users (name, password, created_at, updated_at)
VALUES ('User 1', 'pw1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (name, password, created_at, updated_at)
VALUES ('User 2', 'pw2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT
INTO
  books
  (  name, author_name, price, in_stock, stock_level, created_at, updated_at, category, number_of_days_for_borrow)
VALUES
  (  'book1-1', 'author1-1', 10, 1, 1, NOW(), NOW(), 'Cat1', 1);

INSERT
INTO
  books
  (  name, author_name, price, in_stock, stock_level, created_at, updated_at, category, number_of_days_for_borrow)
VALUES
  (  'book1-2', 'author1-2', 10, 1, 1, NOW(), NOW(), 'Cat1', 1);

INSERT
INTO
  books
  (  name, author_name, price, in_stock, stock_level, created_at, updated_at, category, number_of_days_for_borrow)
VALUES
  (  'book2-1', 'author2-1', 20, 1, 1, NOW(), NOW(), 'Cat2', 2);

 INSERT
  INTO
    books
    (  name, author_name, price, in_stock, stock_level, created_at, updated_at, category, number_of_days_for_borrow)
  VALUES
    (  'book2-2', 'author2-2', 20, 0, 1, NOW(), NOW(), 'Cat2', 2);