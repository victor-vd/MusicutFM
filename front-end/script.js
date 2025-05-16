function lastfmSearchResult(data, resultView) {
    let conteudo = "";

    for (let i = 0; i < data.results.trackmatches.track.length; i++) {
        const track = data.results.trackmatches.track[i];
        conteudo += `
            <div>
                <input type="radio" name="trackSelect" id="${track.mbid}" value="${track.url}">
                <label for="track${track.mbid}">${track.url}</label>
            </div>`;
    }
    resultView.innerHTML = conteudo;
}

function ytSearchResult(data, resultView) {
    let conteudo = "";

    for (let i = 0; i < data.items.length; i++) {
        const item = data.items[i];
        const videoUrl = `https://www.youtube.com/watch?v=${item.id.videoId}`;
        conteudo += `
            <div>
                <input type="radio" name="ytTrackSelect" id="${item.id.videoId}" value="${videoUrl}">
                <label for="ytTrack${item.id.videoId}">${item.snippet.title}</label>
            </div>`;
    }
    resultView.innerHTML = conteudo;

    resultView.addEventListener('change', function (e) {
        if (e.target.name === "ytTrackSelect") {
            const selectedUrl = e.target.value;
            console.log("Selected YouTube video URL:", selectedUrl);
            // You can use selectedUrl as needed
        }
    });
}

function getLastFMResponse(trackParam, artistParam, lastfmResult) {
    let artist = artistParam.value.replace(" ", "+");
    let track = trackParam.value.replace(" ", "+");

    fetch(`http://localhost:8080/home/lastfmSearch?track=${track}&artist=${artist}`, {
        method: 'GET'
    })
        .then(response => response.json()) // JSONArray
        .then(data => {
            console.log(data);
            console.log(data.results.trackmatches.track[1].url);
            lastfmSearchResult(data, lastfmResult);
        })
        .catch();
}


function getYTResponse(searchParam, youtubeResult) {
    let search = searchParam.value.replace(" ", "+");

    fetch(`http://localhost:8080/api/youtube/YTsearch?query=${search}`, {
        method: 'GET'
    })
        .then(response => response.json()) // JSONArray
        .then(data => {
            console.log(data);
            console.log(data.items[0]);

            ytSearchResult(data, youtubeResult);
        })
        .catch();
}

function createVideo() {
    let lastfmResult = document.getElementById('lastfmResult').childNodes;
    let youtubeResults = document.getElementById('youtubeResult').childNodes; // Remove if not used

    // Example: Find the checked radio button in lastfmResult
    let selectedTrackID = null;
    for (let i = 0; i < lastfmResult.length; i++) {
        const node = lastfmResult[i];
        if (node.nodeType === 1 && node.querySelector && node.querySelector('input[type="radio"]') && node.querySelector('input[type="radio"]').checked) {
            selectedTrackID = node.querySelector('input[type="radio"]').id;
            break;
        }
    }

    let selectedVideoID = null;
    for (let i = 0; i < youtubeResults.length; i++) {
        const node = youtubeResults[i];
        if (node.nodeType === 1 && node.querySelector && node.querySelector('input[type="radio"]') && node.querySelector('input[type="radio"]').checked) {
            selectedVideoID = node.querySelector('input[type="radio"]').id;
            break;
        }
    }

    if (selectedVideoID != "" && selectedTrackID != "") {

        fetch(`http://localhost:8080/home/musicutfm/create?trackMbid=${selectedTrackID}&videoID=${selectedVideoID}`, {
            method: 'GET'
        })
            .then(response => response.json())
            .then(data => { })
            .catch();

        fetch(`http://localhost:8080/media//download/video/musicutfm-${selectedVideoID}.mp4`, {
            method: 'GET'
        })
            .then(response => response.json())
            .then(data => { })
            .catch();
    }
}