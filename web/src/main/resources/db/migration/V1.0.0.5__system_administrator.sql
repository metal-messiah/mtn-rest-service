INSERT INTO user_profile (id, email, first_name, last_name, created_by, created_date, updated_by, updated_date) VALUES
    (NEXTVAL('seq_user_profile_id'), 'system.administrator@mtnra.com', 'System', 'Administrator', 1, NOW(), 1, NOW());

INSERT INTO user_identity (id, user_profile_id, provider, provider_user_id) VALUES
    (NEXTVAL('seq_user_identity_id'), 1, 'auth0', '58e2c990319f9f0ebc00e057');