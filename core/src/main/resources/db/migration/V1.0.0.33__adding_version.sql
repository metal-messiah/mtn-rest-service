ALTER TABLE "auth_group" ADD "version" INTEGER NOT NULL DEFAULT 1;
ALTER TABLE "auth_role" ADD "version" INTEGER NOT NULL DEFAULT 1;
ALTER TABLE "user_profile" ADD "version" INTEGER NOT NULL DEFAULT 1;
