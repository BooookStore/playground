<template>
  <div v-bind="$attrs">
    <template v-if="!me">
      <div>
        <label for="login-username">username</label>
        <input id="login-username" type="text" v-model="username" />
        <label for="login-password">password</label>
        <input id="login-password" type="text" v-model="password" />
        <button @click.prevent="login">Login</button>
      </div>
      <div>you are not login.</div>
      <div v-if="loginFailedMessage">incorrect username or password</div>
    </template>

    <template v-else>
      <div>
        <button @click.prevent="logout">Logout</button>
      </div>
      <div>
        <p>login success. hello {{ me.mailAddress }}</p>
        <ul>
          <li>role: {{ me.roleNames }}</li>
          <li>permission: {{ me.permissionNames }}</li>
          <li>operation: {{ me.operationNames }}</li>
        </ul>
      </div>
    </template>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";

const username = ref("");
const password = ref("");
const loginFailedMessage = ref("");
const me = ref(null);

async function login() {
  const response = await fetch("http://localhost:8080/login", {
    method: "post",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      username: username.value,
      password: password.value,
    }),
    credentials: "include",
  });

  if (!response.ok) {
    console.log(`authorization failed ${response.status}`);
    if (response.status === 401) {
      loginFailedMessage.value = "incorrect username or password";
    } else {
      loginFailedMessage.value = "error";
    }
    return;
  }
  loginFailedMessage.value = "";

  await updateMe();
}

async function updateMe() {
  await fetch("http://localhost:8080/me", {
    credentials: "include",
  }).then(async (response) => {
    me.value = await response.json();
  });
}

async function logout() {
  fetch("http://localhost:8080/logout", {
    credentials: "include",
  }).then((response) => {
    me.value = null;
  });
}

onMounted(() => {
  updateMe();
});
</script>
