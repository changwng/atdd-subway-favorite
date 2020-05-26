import {EVENT_TYPE} from '../../utils/constants.js'
import api from '../../api/index.js'
import {searchResultTemplate} from '../../utils/templates.js'
import {PATH_TYPE, ERROR_MESSAGE} from '../../utils/constants.js'

function Search() {
  const $departureStationName = document.querySelector('#departure-station-name')
  const $arrivalStationName = document.querySelector('#arrival-station-name')
  const $searchButton = document.querySelector('#search-button')
  const $searchResultContainer = document.querySelector('#search-result-container')
  const $favoriteButton = document.querySelector('#favorite-button')
  const $searchResult = document.querySelector('#search-result')
  const $shortestDistanceTab = document.querySelector('#shortest-distance-tab')
  const $minimumTimeTab = document.querySelector('#minimum-time-tab')

  const showSearchResult = data => {
    const isHidden = $searchResultContainer.classList.contains('hidden')
    if (isHidden) {
      $searchResultContainer.classList.remove('hidden')
    }
    $searchResult.innerHTML = searchResultTemplate(data)
  }

  const onSearchShortestDistance = event => {
    event.preventDefault()
    $shortestDistanceTab.classList.add('active-tab')
    $minimumTimeTab.classList.remove('active-tab')
    getSearchResult(PATH_TYPE.DISTANCE)
    setFavoriteButton();
  }

  function setFavoriteButton() {
    //TODO
  }

  const onSearchMinimumTime = event => {
    event.preventDefault()
    $minimumTimeTab.classList.add('active-tab')
    $shortestDistanceTab.classList.remove('active-tab')
    getSearchResult(PATH_TYPE.DURATION)
  }

  const getSearchResult = pathType => {
    const searchInput = {
      source: $departureStationName.value,
      target: $arrivalStationName.value,
      type: pathType
    }
    api.path
        .find(searchInput)
        .then(data => showSearchResult(data))
        .catch(error => alert(ERROR_MESSAGE.COMMON))
  }

  const onToggleFavorite = event => {
    event.preventDefault()
    const isFavorite = $favoriteButton.classList.contains('mdi-star')
    if (isFavorite) {
      //TODO
    } else {
      const favoriteInfo = {
        sourceStationName: $departureStationName.value,
        targetStationName: $arrivalStationName.value
      }
      api.memberWithToken.addFavorite(favoriteInfo)
          .then((data) => {
            if (!data.ok) {
              throw new Error(data.status)
            }
            toggleFavoriteButton(isFavorite)
          })
          .catch(error => {
            alert(ERROR_MESSAGE.FAVORITE_ADD_FAIL)
          })
    }
  }

  function toggleFavoriteButton(isFavorite) {
    const classList = $favoriteButton.classList
    if (isFavorite) {
      classList.add('mdi-star-outline')
      classList.add('text-gray-600')
      classList.add('bg-yellow-500')
      classList.remove('mdi-star')
      classList.remove('text-yellow-500')
    } else {
      classList.remove('mdi-star-outline')
      classList.remove('text-gray-600')
      classList.remove('bg-yellow-500')
      classList.add('mdi-star')
      classList.add('text-yellow-500')
    }
  }

  const initEventListener = () => {
    $favoriteButton.addEventListener(EVENT_TYPE.CLICK, onToggleFavorite)
    $searchButton.addEventListener(EVENT_TYPE.CLICK, onSearchShortestDistance)
    $shortestDistanceTab.addEventListener(EVENT_TYPE.CLICK, onSearchShortestDistance)
    $minimumTimeTab.addEventListener(EVENT_TYPE.CLICK, onSearchMinimumTime)
  }

  this.init = () => {
    if (localStorage.getItem("tokenType") === null || localStorage.getItem("accessToken") === null) {
      alert(ERROR_MESSAGE.LOGIN_FIRST)
      location.href = "/login"
    }
    initEventListener()
  }
}

const search = new Search()
search.init()
