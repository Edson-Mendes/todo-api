var db = connect("mongodb://admin:1234@localhost:27017/admin");

db = db.getSiblingDB('todo-db');

db.createUser({ user: "user", pwd: "1234", roles: [ { role: "readWrite", db: "todo-db"} ]})

db.createCollection("user");

db.user.insert({
    _id: ObjectId("64ac38cf0717dd5160c018f1"),
    name: 'lorem',
    email: 'lorem@email.com',
    password: '{bcrypt}$2a$10$uJusBO7zXJLASGQqbl.uROPfSQLTbNgkcwFj37OZSSenu95DKnu4e',
    creationDate: ISODate("2023-07-10T16:58:55.000Z"),
    authorities: [ 'ROLE_USER' ],
    _class: 'com.emendes.todoapi.model.User'
});