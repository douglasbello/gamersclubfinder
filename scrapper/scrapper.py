import time
import requests
from bs4 import BeautifulSoup
from concurrent.futures import ThreadPoolExecutor, as_completed

link_gc = "https://gamersclub.com.br/player/{player_id}"

def fetch_player(player_id):
    response = requests.get(link_gc.format(player_id=player_id))
    if response.status_code == 200:
        soup = BeautifulSoup(response.content, 'html.parser')
        steam_link = soup.find('a', class_="Button--steam")
        if steam_link:
            return steam_link['href']
    return None

def main():
    start = time.time()
    total_encontradas = 0
    with ThreadPoolExecutor(max_workers=50) as executor:
        # Mapeando as requisições para os IDs
        futures = [executor.submit(fetch_player, n) for n in range(1, 1000)]
        
        for future in as_completed(futures):
            steam_link = future.result()
            if steam_link:
                link_save = "https://gamersclub.com.br/player/{player_id}"
                data = {
                    'steamId': steam_link,
                    "gamersclubUrl": link_save.format(player_id=player_id_here) 
                }
                print(steam_link + " " + link_save)
                total_encontradas += 1
    
    print("-------------------------------------------------------------------------------------")
    print(f"Total encontradas = {total_encontradas}")
    print("-------------------------------------------------------------------------------------")
    end = time.time()
    length = end - start;

    print("It took: ", length, " seconds")

if __name__ == "__main__":
    main()