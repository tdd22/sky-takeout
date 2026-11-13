INSERT INTO employee (
    name,
    username,
    password,
    phone,
    sex,
    id_number,
    status,
    create_time,
    update_time,
    create_user,
    update_user
)
VALUES (
           '管理员',
           'admin',
           '${admin-password-hash}',
           '13800138000',
           0,
           '110101199001010000',
           1,
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP,
           NULL,
           NULL
       );