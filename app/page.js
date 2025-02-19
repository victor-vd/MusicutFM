import * as script from './Scripts/script.js'
import SearchResultList from './Components/searchResults.jsx'

export default async function Page() {
  return (
    <div className='search-container'>
      <p>paste link here</p>
      <form method="post" onSubmit={script.handleSubmit}>
        <input id="musicLink" type="text" name='searchParameter' />
        <button type="submit" id="sendButton">send</button>
      </form>
      <SearchResultList searchParameter={''} />
    </div>
  )
}