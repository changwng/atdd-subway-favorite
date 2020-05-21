import { EVENT_TYPE, ERROR_MESSAGE } from '../../utils/constants.js'

function Login() {
  const $loginButton = document.querySelector('#login-button')
  const onLogin = event => {
    event.preventDefault()
    const emailValue = document.querySelector('#email').value
    const passwordValue = document.querySelector('#password').value
    if (!emailValue && !passwordValue) {
      Snackbar.show({ text: ERROR_MESSAGE.LOGIN_FAIL, pos: 'bottom-center', showAction: false, duration: 2000 })
      return
    }
    fetch("/login", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      }
      , body: JSON.stringify({
        email: emailValue,
        password: passwordValue
      })
    }).then(data => console.log(data.json()))

  }

  this.init = () => {
    $loginButton.addEventListener(EVENT_TYPE.CLICK, onLogin)
  }
}

const login = new Login()
login.init()
