import time
import aiohttp
import asyncio
from bs4 import BeautifulSoup

link_gc = "https://gamersclub.com.br/player/{player_id}"
post_url = "http://localhost:8080/players"  # URL para fazer o POST

async def fetch_player(session, player_id):
    async with session.get(link_gc.format(player_id=player_id)) as response:
        if response.status == 200:
            content = await response.text()
            soup = BeautifulSoup(content, 'html.parser')
            steam_link = soup.find('a', class_="Button--steam")
            if steam_link:
                return player_id, steam_link['href']  # Retorna o player_id e steam_link
    return player_id, None

async def post_player(session, data):
    async with session.post(post_url, json=data) as response:
        if response.status == 204:
            print(f"Sucesso: {data['steamId']} -> {data['gamersclubUrl']}")
        else:
            print(f"Erro no POST: {response.status}")

async def main():
    start = time.time()
    total_encontradas = 0
    async with aiohttp.ClientSession() as session:
        tasks = []
        for n in range(1, 100000):  # IDs dos jogadores
            tasks.append(fetch_player(session, n))

        for task in asyncio.as_completed(tasks):
            player_id, steam_link = await task
            if steam_link:
                link_save = f"https://gamersclub.com.br/player/{player_id}"
                data = {
                    'steamId': steam_link,
                    "gamersclubUrl": link_save
                }
                # Criar uma task para o POST
                await post_player(session, data)
                total_encontradas += 1

    print("-------------------------------------------------------------------------------------")
    print(f"Total encontradas = {total_encontradas}")
    print("-------------------------------------------------------------------------------------")
    end = time.time()
    length = end - start
    print("It took: ", length, " seconds")

if __name__ == "__main__":
    asyncio.run(main())