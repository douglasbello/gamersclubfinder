import time
import requests
from bs4 import BeautifulSoup
from concurrent.futures import ThreadPoolExecutor, as_completed

link_gc = "https://gamersclub.com.br/player/{player_id}"
post_url = "http://localhost:8080/players"  # URL para fazer o POST

def fetch_player(player_id):
    response = requests.get(link_gc.format(player_id=player_id))
    if response.status_code == 200:
        soup = BeautifulSoup(response.content, 'html.parser')
        steam_link = soup.find('a', class_="Button--steam")
        if steam_link:
            return player_id, steam_link['href']  # Retorna o player_id e steam_link
    return player_id, None

def main():
    start = time.time()
    total_encontradas = 0
    with ThreadPoolExecutor(max_workers=50) as executor:
        # Mapeando as requisições para os IDs
        futures = [executor.submit(fetch_player, n) for n in range(1, 1000)]
        
        for future in as_completed(futures):
            player_id, steam_link = future.result()
            if steam_link:
                link_save = "https://gamersclub.com.br/player/{player_id}"
                data = {
                    'steamId': steam_link,
                    "gamersclubUrl": link_save.format(player_id=player_id)  # Passando o player_id correto
                }

                # Fazendo o POST request para o servidor local
                response = requests.post(post_url, json=data)

                if response.status_code == 204:
                    print(f"Sucesso: {steam_link} -> {link_save.format(player_id=player_id)}")
                else:
                    print(f"Erro no POST: {response.status_code}")

                total_encontradas += 1
    
    print("-------------------------------------------------------------------------------------")
    print(f"Total encontradas = {total_encontradas}")
    print("-------------------------------------------------------------------------------------")
    end = time.time()
    length = end - start
    print("It took: ", length, " seconds")

if __name__ == "__main__":
    main()