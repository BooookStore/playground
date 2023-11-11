<script setup>
import Login from "./components/Login.vue";
import { ref } from "vue";

const orderList = ref([]);
async function fetchCreatedOrders() {
  const response = await fetch("http://localhost:8080/order", {
    credentials: "include",
  });

  if (!response.ok) {
    return;
  }

  orderList.value = await response.json();
}

const orderId = ref("");
const order = ref(null);
async function searchOrder() {
  try {
    const response = await fetch(
      `http://localhost:8080/order/${orderId.value}`,
      {
        headers: {
          Accept: "application/json",
        },
        credentials: "include",
      }
    );
    if (!response.ok) {
      order.value = null;
      return;
    }
    const json = await response.json();
    order.value = json;
  } catch (err) {
    order.value = null;
  }
}
</script>

<template>
  <h1>Vue Playground</h1>
  <Login id="login" />
  <div id="content">
    <div>
      <button @click.prevent="fetchCreatedOrders">fetch created order</button>
    </div>
    <div v-for="order in orderList">
      <p>{{ order.name }}</p>
      <ul>
        <li>CreatedAt: {{ order.createdDate }}</li>
        <li>Status: {{ order.currentStatus }}</li>
      </ul>
    </div>
  </div>
  <div id="content">
    <div id="content-search-form">
      <input type="text" v-model="orderId" />
      <button @click.prevent="searchOrder">search order</button>
    </div>
    <div v-if="order">
      <ul>
        <li>OrderId: {{ order.orderId }}</li>
        <li>OrderName: {{ order.name }}</li>
        <li>Status: {{ order.statusHistory[0].status }}</li>
      </ul>
    </div>
  </div>
</template>

<style scoped>
#login {
  margin-bottom: 20px;
}

#content {
  display: flex;
  flex-direction: column;
  gap: 5px;
  margin-bottom: 20px;
}

#content-search-form {
  display: flex;
  flex-direction: row;
  justify-content: start;
  gap: 10px;
}
</style>
