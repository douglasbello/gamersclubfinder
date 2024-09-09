CREATE TABLE tb_players(
	id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
	steam_id VARCHAR(70),
	gamersclub_url VARCHAR(70),
	created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT NULL
)

CREATE OR REPLACE FUNCTION update_modified_column()
	RETURNS TRIGGER AS $$
	BEGIN NEW.updated_at = clock_timestamp();
	RETURN NEW;
	END;
$$ language 'plpgsql';

CREATE TRIGGER update_modified_time BEFORE UPDATE ON tb_players FOR EACH ROW EXECUTE PROCEDURE update_modified_column();

SELECT * FROM tb_players;