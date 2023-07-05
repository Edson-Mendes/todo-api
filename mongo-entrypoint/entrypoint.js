var db = connect("mongodb://admin:1234@localhost:27017/admin");

db = db.getSiblingDB('todo-db');

db.createUser({ user: "user", pwd: "1234", roles: [ { role: "readWrite", db: "todo-db"} ]})