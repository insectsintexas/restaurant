SET CHARSET UTF8;
CREATE TABLE IF NOT EXISTS reservations (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  number VARCHAR(255),
  email VARCHAR(255),
  date DATE,
  time TIME,
  people INT,
  message VARCHAR(255) DEFAULT NULL
);

INSERT INTO reservations (name, number, email, date, time, people, message)
VALUES ('田中太郎', '08022224444', 'tanaka2244@gmail.com', '2023-04-12', '18:00', 2, 'カウンター席希望');

CREATE TABLE IF NOT EXISTS articles (
  id INT AUTO_INCREMENT PRIMARY KEY,
  date DATE,
  title VARCHAR(255),
  content VARCHAR(255),
  url VARCHAR(255) DEFAULT NULL
);

INSERT INTO articles (date, title, content)
VALUES ('2023-04-01', '開業します', 'テストですテストです');

