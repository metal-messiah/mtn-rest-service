ALTER TABLE store
  DROP is_active;
ALTER TABLE store
  ADD COLUMN type VARCHAR(64);