import {ERROR_MESSAGE, EVENT_TYPE, PATH_TYPE, SUCCESS_MESSAGE} from "../../utils/constants.js";
import api from "../../api/index.js";
import {searchResultTemplate} from "../../utils/templates.js";
import showSnackbar from "../../lib/snackbar/index.js";

function Search() {
  const $departureStationName = document.querySelector(
      "#departure-station-name"
  );
  const $arrivalStationName = document.querySelector("#arrival-station-name");
  const $searchButton = document.querySelector("#search-button");
  const $searchResultContainer = document.querySelector(
      "#search-result-container"
  );
  const $favoriteButton = document.querySelector("#favorite-button");
  const $searchResult = document.querySelector("#search-result");
  const $shortestDistanceTab = document.querySelector("#shortest-distance-tab");
  const $minimumTimeTab = document.querySelector("#minimum-time-tab");
  let searchTargetStations = [];

  const showSearchResult = data => {
    const isHidden = $searchResultContainer.classList.contains("hidden");
    if (isHidden) {
      $searchResultContainer.classList.remove("hidden");
    }
    $searchResult.innerHTML = searchResultTemplate(data);
  };

  const onSearchShortestDistance = event => {
    event.preventDefault();
    console.log("찍혀?");
    $shortestDistanceTab.classList.add("active-tab");
    $minimumTimeTab.classList.remove("active-tab");
    getSearchResult(PATH_TYPE.DISTANCE);
  };

  const onSearchMinimumTime = event => {
    event.preventDefault();
    $minimumTimeTab.classList.add("active-tab");
    $shortestDistanceTab.classList.remove("active-tab");
    getSearchResult(PATH_TYPE.DURATION);
  };

  const getSearchResult = async pathType => {
    try {
      const searchInput = {
        source: $departureStationName.value,
        target: $arrivalStationName.value,
        type: pathType
      };
      const data = await api.path.find(searchInput);
      searchTargetStations = data.stations;
      showSearchResult(data);
    } catch (e) {
      showSnackbar(ERROR_MESSAGE.COMMON);
    }
  };

  const onToggleFavorite = async event => {
    event.preventDefault();
    try {
      const path = {
        source: searchTargetStations[0].name,
        target: searchTargetStations[searchTargetStations.length - 1].name,
        email: localStorage.getItem("email")
      };
      await api.favorite.create(path);
      showSnackbar(SUCCESS_MESSAGE.FAVORITE);
    } catch (e) {
      showSnackbar(ERROR_MESSAGE.COMMON);
    }
  };

  const initEventListener = () => {
    console.log("init2")

    $favoriteButton.addEventListener(EVENT_TYPE.CLICK, onToggleFavorite);
    $searchButton.addEventListener(EVENT_TYPE.CLICK, onSearchShortestDistance);
    $shortestDistanceTab.addEventListener(
        EVENT_TYPE.CLICK,
        onSearchShortestDistance
    );
    $minimumTimeTab.addEventListener(EVENT_TYPE.CLICK, onSearchMinimumTime);
  };

  this.init = () => {
    console.log("init")
    initEventListener();
  };
}

const search = new Search();
search.init();
