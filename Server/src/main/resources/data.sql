-- Script de inicialização de dados para Spring Boot
-- Este arquivo será executado automaticamente na inicialização

-- Inserir produtos na tabela 'produto' (seguindo a entidade Produto.java)
INSERT INTO produto (nome, descricao, preco, quantidade_estoque, ativo, avaliacao) VALUES
('Café Bourbon Santos', 'Café especial da região de Santos, com notas de chocolate e caramelo. Ideal para quem aprecia sabores marcantes e encorpados.', 25.90, 100, 1, 5),
('Espresso Gourmet', 'Blend especial para espresso, corpo intenso e cremosidade única. Perfeito para momentos especiais.', 32.50, 80, 1, 5),
('Café Origem Etiópia', 'Café 100% arábica da Etiópia, com notas florais e frutadas. Torra média para preservar os sabores únicos.', 42.90, 60, 1, 5),
('Café do Cerrado', 'Café especial do Cerrado Mineiro, doçura natural e corpo equilibrado. Torra clara para realçar a acidez.', 28.50, 75, 1, 4),
('Café Geisha Panama', 'Café premium da variedade Geisha, cultivado no Panamá. Notas florais e sabor complexo.', 89.90, 30, 1, 5),
('Prensa Francesa Bodum', 'Cafeteira francesa clássica da Bodum, para preparo tradicional e saboroso. Capacidade de 1 litro.', 189.90, 25, 1, 4),
('Hario V60 Kit Completo', 'Kit completo Hario V60 com dripper, filtros e jarra. Para quem busca precisão no preparo.', 159.90, 35, 1, 5),
('Moedor de Café Baratza', 'Moedor elétrico profissional com 40 níveis de moagem. Ideal para todos os métodos de preparo.', 799.90, 15, 1, 5),
('Chemex 6 Xícaras', 'Cafeteira Chemex para 6 xícaras, design icônico e preparo limpo. Inclui filtros especiais.', 289.90, 20, 1, 4),
('Aeropress Go', 'Cafeteira portátil Aeropress Go, perfeita para viagens. Preparo rápido e saboroso.', 249.90, 40, 1, 4);

-- Inserir imagens dos produtos na tabela produto_imagens
INSERT INTO produto_imagens (produto_id, imagem_base64) VALUES
(1, 'https://images.unsplash.com/photo-1559056199-641a0ac8b55e?w=400'),
(2, 'https://images.unsplash.com/photo-1610889556528-9a770e32642f?w=400'),
(3, 'https://images.unsplash.com/photo-1587734195503-904fca47e0d9?w=400'),
(4, 'https://images.unsplash.com/photo-1559056199-641a0ac8b55e?w=400'),
(5, 'https://images.unsplash.com/photo-1559056199-641a0ac8b55e?w=400'),
(6, 'https://images.unsplash.com/photo-1594736797933-d0601ba2fe65?w=400'),
(7, 'https://images.unsplash.com/photo-1594736797933-d0601ba2fe65?w=400'),
(8, 'https://images.unsplash.com/photo-1523694576729-dc99e9c0f9b5?w=400'),
(9, 'https://images.unsplash.com/photo-1594736797933-d0601ba2fe65?w=400'),
(10, 'https://images.unsplash.com/photo-1594736797933-d0601ba2fe65?w=400');

-- Inserir dados na tabela cafe_especial (para produtos 1-5)
INSERT INTO cafe_especial (produto_id, origem, variedade, torrefacao, notas_sensoriais, torra, beneficiamento, data_torra, data_validade, recomendacoes_preparo) VALUES
(1, 'Brasil - Santos', 'Bourbon', 'Artesanal', 'Chocolate, Caramelo, Nozes', 'Média', 'Natural', '2024-12-01', '2025-03-01', 'Temperatura: 92-96°C, Tempo: 4-6 minutos'),
(2, 'Brasil - Minas Gerais', 'Catuaí', 'Tradicional', 'Intenso, Cremoso, Doce', 'Escura', 'Lavado', '2024-12-01', '2025-03-01', 'Temperatura: 88-92°C, Tempo: 25-30 segundos'),
(3, 'Etiópia - Yirgacheffe', 'Heirloom', 'Artesanal', 'Floral, Frutas Vermelhas, Cítrico', 'Clara', 'Natural', '2024-12-01', '2025-03-01', 'Temperatura: 94-96°C, Tempo: 3-4 minutos'),
(4, 'Brasil - Cerrado Mineiro', 'Mundo Novo', 'Sustentável', 'Doce, Corpo Equilibrado, Acidez Suave', 'Clara', 'Lavado', '2024-12-01', '2025-03-01', 'Temperatura: 93-95°C, Tempo: 4-5 minutos'),
(5, 'Panamá - Boquete', 'Geisha', 'Artesanal', 'Floral, Jasmim, Frutas Tropicais', 'Clara', 'Lavado', '2024-12-01', '2025-03-01', 'Temperatura: 94-96°C, Tempo: 3-4 minutos');

-- Inserir dados na tabela metodo_preparo (para produtos 6-10)
INSERT INTO metodo_preparo (produto_id, tipo_preparo, material, acessorios, complexidade, marca) VALUES
(6, 'Prensa Francesa', 'Vidro Borossilicato', 'Filtro de malha, Tampa, Êmbolo', 'Fácil', 'Bodum'),
(7, 'Pour Over V60', 'Cerâmica', 'Filtros V60, Jarra de vidro, Suporte', 'Intermediário', 'Hario'),
(8, 'Moagem', 'Metal e Plástico', 'Recipiente para grãos, Dosador', 'Fácil', 'Baratza'),
(9, 'Pour Over Chemex', 'Vidro Borossilicato', 'Filtros Chemex, Amarra de couro', 'Intermediário', 'Chemex'),
(10, 'Pressão + Imersão', 'Plástico BPA-Free', 'Filtros microfinos, Agitador, Copo', 'Fácil', 'Aeropress');
