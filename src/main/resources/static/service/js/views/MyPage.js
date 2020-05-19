import {
  EVENT_TYPE,
  ERROR_MESSAGE,
  SUCCESS_MESSAGE
} from "../../utils/constants.js";
import api from "../../api/index.js";
import showSnackbar from "../../lib/snackbar/index.js";

function MyInfo() {
  const $email = document.querySelector("#email");
  const $name = document.querySelector("#name");
  const $password = document.querySelector("#password");
  const $signOutButton = document.querySelector("#sign-out-button");
  const $updateButton = document.querySelector("#update-button");

  const onSignOutHandler = event => {
    event.preventDefault();
    if (confirm("정말 탈퇴하시겠습니까?")) {
      api.loginMember
        .delete()
        .then(() => {
          localStorage.setItem("jwt", "");
          location.href = "/";
        })
        .catch(() => showSnackbar(ERROR_MESSAGE.COMMON));
    }
  };

  const onUpdateHandler = event => {
    event.preventDefault();
    const updatedInfo = {
      name: $name.value,
      email: $email.value,
      password: $password.value
    };
    api.loginMember
      .update(updatedInfo)
      .then(() => {
        showSnackbar(SUCCESS_MESSAGE.SAVE);
      })
      .catch(() => showSnackbar(ERROR_MESSAGE.COMMON));
  };

  const initMyInfo = () => {
    api.loginMember
      .get()
      .then(member => {
        $email.value = member.email;
        $name.value = member.name;
        $password.value = member.password;
      })
      .catch(() => showSnackbar(ERROR_MESSAGE.COMMON));
  };

  this.init = () => {
    initMyInfo();
    $signOutButton.addEventListener(EVENT_TYPE.CLICK, onSignOutHandler);
    $updateButton.addEventListener(EVENT_TYPE.CLICK, onUpdateHandler);
  };
}

const myInfo = new MyInfo();
myInfo.init();