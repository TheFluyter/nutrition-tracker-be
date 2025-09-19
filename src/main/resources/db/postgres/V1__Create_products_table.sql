-- Create products table
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    calories DECIMAL(10,2),
    protein DECIMAL(10,2),
    carbohydrates DECIMAL(10,2),
    fat DECIMAL(10,2),
    fiber DECIMAL(10,2),
    sugar DECIMAL(10,2),
    sodium DECIMAL(10,2),
    vitamin_c DECIMAL(10,2),
    potassium DECIMAL(10,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create index on name for faster searches
CREATE INDEX idx_products_name ON products(name);

-- Create index on calories for filtering
CREATE INDEX idx_products_calories ON products(calories);

-- Insert sample data
INSERT INTO products (name, description, calories, protein, carbohydrates, fat, fiber, sugar, sodium, vitamin_c, potassium) VALUES
('Banana', 'A yellow tropical fruit rich in potassium and vitamin C', 89.0, 1.1, 22.8, 0.3, 2.6, 12.2, 1.0, 8.7, 358.0),
('Apple', 'A crisp and sweet fruit, great source of fiber and vitamin C', 52.0, 0.3, 13.8, 0.2, 2.4, 10.4, 1.0, 4.6, 107.0);

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_products_updated_at 
    BEFORE UPDATE ON products 
    FOR EACH ROW 
    EXECUTE FUNCTION update_updated_at_column();
