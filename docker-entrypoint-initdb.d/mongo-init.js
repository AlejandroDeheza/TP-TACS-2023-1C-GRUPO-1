db = db.getSiblingDB('TP');

db.createUser(
    {
        user: process.env.MONGO_BACKEND_USER,
        pwd: process.env.MONGO_BACKEND_PWD,
        roles: [
            {
                role: "readWrite",
                db: process.env.MONGO_BACKEND_DB
            }
        ],
    }
);
