ALTER TABLE "auth_permission"
  ADD "created_by" INTEGER NOT NULL DEFAULT 1,
  ADD "created_date" TIMESTAMP NOT NULL DEFAULT NOW(),
  ADD "deleted_by" INTEGER,
  ADD "deleted_date" TIMESTAMP,
  ADD "updated_by" INTEGER NOT NULL DEFAULT 1,
  ADD "updated_date" TIMESTAMP NOT NULL DEFAULT NOW(),
  ADD "version" INTEGER NOT NULL DEFAULT 1
;