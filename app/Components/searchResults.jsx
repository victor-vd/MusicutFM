'use client';

import React, { useEffect, useState } from 'react';

export default function SearchResultList({ searchParameter }) {

    console.log({ searchParameter });

    if (searchParameter == "") {
        return (
            <div>

            </div>
        );
    }
    const [searchResults, setSearchResults] = useState([]);

    useEffect(() => {
        const yourFetch = async () => {
            const API_KEY = '522d7d17efba9a3dd4d6c40de961486a'; // Replace with your actual API key
            const searchData = await fetch('https://ws.audioscrobbler.com/2.0/?method=track.search&track=' + searchParameter + '&api_key=' + API_KEY + '&format=json');
            const result = await searchData.json();
            const searchResult = result.results?.trackmatches?.track || [];
            setSearchResults(searchResult);
        }
        yourFetch();
    }, [searchParameter]);

    return (
        <ul>
            {searchResults.map((post) => (
                <li key={post.mbid || post.url}><a href={post.url}>{post.name}</a></li>
            ))}
        </ul>
    );

}