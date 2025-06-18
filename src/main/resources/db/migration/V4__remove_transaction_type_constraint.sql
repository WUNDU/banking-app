ALTER TABLE transaction DROP CONSTRAINT IF EXISTS chk_type;

-- Add a more flexible constraint that just ensures type is not empty
ALTER TABLE transaction ADD CONSTRAINT chk_type_not_empty CHECK (type <> '' AND type IS NOT NULL);