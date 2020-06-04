import {SNACK_BAR} from '../utils/constants.js';

const METHOD = {
    PUT() {
        return {
            method: 'PUT'
        }
    },
    DELETE() {
        return {
            method: 'DELETE'
        }
    },
    POST(data) {
        return {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                ...data
            })
        }
    }
}

const api = (() => {
    const request = (uri, config) => fetch(uri, config)
    const requestWithJsonData = (uri, config) => fetch(uri, config).then(response => {
        if (!response.ok) {
            response.json().then(error => {
                SNACK_BAR(error.errorMessage)
            });
            return;
        }
        return response.json();
    })
    const accessToken = localStorage.getItem("accessToken")
    const tokenType = localStorage.getItem("tokenType")

    function getToken() {
        return tokenType + " " + accessToken;
    }

    const line = {
        getAll() {
            return request(`/lines/detail`)
        },
        getAllDetail() {
            return requestWithJsonData(`/lines/detail`)
        }
    }

    const path = {
        find(params) {
            return requestWithJsonData(`/paths?source=${params.source}&target=${params.target}&type=${params.type}`)
        }
    }

    const member = {
        create(data) {
            return request(`/members/signup`, METHOD.POST(data));
        },
        login(data) {
            return request(`/oauth/token`, METHOD.POST(data));
        },
        find() {
            return fetch(`/members`, {
                headers: {
                    'Authorization': getToken(),
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                if (!response.ok) {
                    SNACK_BAR("로그인을 다시 해주세요 :)");
                    window.location.href = "/";
                }
                return response.json();
            })
        },
        update(memberId, data) {
            return fetch(`/members/${memberId}`, {
                method: 'PUT',
                headers: {
                    'Authorization': getToken(),
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
        },
        signOut(memberId) {
            return fetch(`/members/${memberId}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': getToken()
                }
            })
        }
    };

    const favorite = {
        create(memberId, data) {
            return fetch(`/members/${memberId}/favorites`, {
                method: 'POST',
                headers: {
                    'Authorization': getToken(),
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    ...data
                })
            })
        },
        find(memberId) {
            return fetch(`/members/${memberId}/favorites`, {
                headers: {
                    'Authorization': getToken(),
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                if (!response.ok) {
                    SNACK_BAR("로그인을 다시 해주세요 :)");
                    window.location.href = "/";
                }
                return response.json();
            })
        },
        delete(memberId, data) {
            return fetch(`/members/${memberId}/favorites/${data.favoriteId}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': getToken(),
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    ...data
                })
            })
        }
    };

    return {
        line,
        path,
        member,
        favorite
    }
})()

export default api
