db.createUser(
    {
        user: 'root',
        pwd: 'root',
        roles: [
            {
                role: "userAdminAnyDatabase",
                db: "admin"
            }
        ]
    }
);

db.createUser(
    {
        user: "tacs",
        pwd: "tacs",
        roles: [
            {
                role: "readWrite",
                db: "TP"
            }
        ]
    }
);
