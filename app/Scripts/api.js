const API_KEY = "522d7d17efba9a3dd4d6c40de961486a";

export default async function getMusicSearch(searchParameter) {
    const searchData = await fetch('https://ws.audioscrobbler.com/2.0/?method=track.search&track=' + searchParameter + '&api_key=' + API_KEY + '&format=json');
    const result = await searchData.json();
    const searchResult = result.results.trackmatches.track;
    return searchResult;
}

export async function getMusicInfo(music) {
    let musicName = music.name;
    let artistName = music.artist;

    const infoData = await fetch('https://ws.audioscrobbler.com/2.0/?method=track.getInfo&api_key=' + API_KEY + '&artist=' + artistName + '&track=' + musicName + '&format=json');
    const musicInfo = await infoData.json();

    return musicInfo;
}