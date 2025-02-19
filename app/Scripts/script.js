"use client"

import getMusicSearch from './api.js';
import { getMusicInfo } from './api.js';
import SearchResultList from '../Components/searchResults.jsx'

export async function handleSubmit(e) {
    // Prevent the browser from reloading the page
    e.preventDefault();

    // Read the form data
    const form = e.target;
    const formData = new FormData(form);

    // Or you can work with it as a plain object:
    const formJson = Object.fromEntries(formData.entries());
    console.log(formJson.searchParameter);

    return (
        <SearchResultList searchParameter={formJson.searchParameter} />
    );
}

export function test() {
    console.log("3");
}