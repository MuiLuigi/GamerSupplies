-- Seed data – runs on startup, skips existing rows

INSERT INTO products (name, description, price, quantity, category, sku, image_url, created)
SELECT 'PlayStation 5 Console',
       'Sony PlayStation 5 – next-gen gaming with ultra-high-speed SSD and ray tracing.',
       699.99, 15, 'CONSOLE', 'PS5-001',
       'https://images.unsplash.com/photo-1606813907291-d86efa9b94db?w=400',
       NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE sku = 'PS5-001');

INSERT INTO products (name, description, price, quantity, category, sku, image_url, created)
SELECT 'Xbox Series X',
       'Microsoft Xbox Series X – the fastest, most powerful Xbox ever.',
       649.99, 12, 'CONSOLE', 'XSX-001',
       'https://images.unsplash.com/photo-1621259182978-fbf93132d53d?w=400',
       NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE sku = 'XSX-001');

INSERT INTO products (name, description, price, quantity, category, sku, image_url, created)
SELECT 'Nintendo Switch OLED',
       'Nintendo Switch OLED Model – vibrant 7-inch OLED screen, enhanced audio.',
       449.99, 20, 'CONSOLE', 'NSW-OLED-001',
       'https://images.unsplash.com/photo-1585689039261-d3b17d400dce?w=400',
       NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE sku = 'NSW-OLED-001');

INSERT INTO products (name, description, price, quantity, category, sku, image_url, created)
SELECT 'Redragon K552 Mechanical Keyboard',
       'Compact TKL mechanical gaming keyboard with tactile blue switches and RGB lighting.',
       49.99, 50, 'ACCESSORY', 'ACC-KB-001',
       'https://images.unsplash.com/photo-1618384887929-16ec33fab9ef?w=400',
       NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE sku = 'ACC-KB-001');

INSERT INTO products (name, description, price, quantity, category, sku, image_url, created)
SELECT 'Razer DeathAdder V3 Mouse',
       'Ergonomic gaming mouse with Focus Pro 30K optical sensor and 90-hour battery.',
       89.99, 35, 'ACCESSORY', 'ACC-MS-001',
       'https://images.unsplash.com/photo-1527814050087-3793815479db?w=400',
       NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE sku = 'ACC-MS-001');

INSERT INTO products (name, description, price, quantity, category, sku, image_url, created)
SELECT 'HyperX Cloud II Gaming Headset',
       '7.1 virtual surround sound, memory foam ear cushions, detachable noise-cancelling mic.',
       99.99, 28, 'ACCESSORY', 'ACC-HS-001',
       'https://images.unsplash.com/photo-1612198790700-4f8a56fa4f6c?w=400',
       NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE sku = 'ACC-HS-001');

INSERT INTO products (name, description, price, quantity, category, sku, image_url, created)
SELECT 'Logitech G Pro X Gaming Headset',
       'Pro-grade detachable Blue VO!CE mic, DTS Headphone:X 2.0 surround sound.',
       129.99, 22, 'ACCESSORY', 'ACC-HS-002',
       'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400',
       NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE sku = 'ACC-HS-002');

INSERT INTO products (name, description, price, quantity, category, sku, image_url, created)
SELECT 'The Legend of Zelda: Tears of the Kingdom',
       'Open-world adventure for Nintendo Switch – explore Hyrule above and below.',
       79.99, 40, 'GAME', 'GAME-TK-001',
       'https://images.unsplash.com/photo-1552820728-8b83bb6b773f?w=400',
       NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE sku = 'GAME-TK-001');

INSERT INTO products (name, description, price, quantity, category, sku, image_url, created)
SELECT 'Spider-Man 2',
       'Marvel''s Spider-Man 2 for PlayStation 5 – swing through New York as Peter and Miles.',
       89.99, 30, 'GAME', 'GAME-SM2-001',
       'https://images.unsplash.com/photo-1493711662062-fa541adb3fc8?w=400',
       NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE sku = 'GAME-SM2-001');

INSERT INTO products (name, description, price, quantity, category, sku, image_url, created)
SELECT 'Halo Infinite',
       'Xbox exclusive – the Master Chief returns in a vast open world.',
       59.99, 25, 'GAME', 'GAME-HI-001',
       'https://images.unsplash.com/photo-1612287230202-1ff1d85d1bdf?w=400',
       NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE sku = 'GAME-HI-001');
