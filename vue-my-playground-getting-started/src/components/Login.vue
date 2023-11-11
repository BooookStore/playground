<template>
  <div v-if="!me">
    <label for="login-username">username</label>
    <input id="login-username" type="text" v-model="username" />
    <label for="login-password">password</label>
    <input id="login-password" type="text" v-model="password" />
    <button @click.prevent="login">Login</button>
  </div>
  <div>
    <div v-if="!me">you are not login.</div>
    <div v-else>
      <p>login success. hello {{ me.mailAddress }}</p>
      <button @click.prevent="logout">Logout</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const username = ref("");
const password = ref("");
const me = ref(null);

async function login() {
  await fetch("http://localhost:8080/login", {
    method: "post",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      username: username.value,
      password: password.value,
    }),
    credentials: "include",
  })
    .then((response) => {
      console.log(response.status);
    })
    .catch((reason) => {
      console.log(reason);
    });

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
</script>
