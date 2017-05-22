ALTER TABLE shopping_center
  ADD COLUMN legacy_casing_id INT;
ALTER TABLE shopping_center
  ADD COLUMN legacy_shopping_center_id INT;
DROP INDEX idx_shopping_center_native_id;