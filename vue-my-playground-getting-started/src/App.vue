<script setup>
import Login from "./components/Login.vue"
import { ref } from "vue";

const operations = ref([]);
const roleId = ref("");

async function fetch_operations() {
  const response = await fetch(
    `http://localhost:8080/operation?roleId=${roleId.value}`
  );
  const json = await response.json();
  operations.value = json;
}

const orderId = ref("");
const order = ref(null);
async function fetchOrders() {
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
  <Login/>
  <div id="content">
    <div id="content-search-form">
      <input type="text" v-model="orderId" />
      <button @click.prevent="fetchOrders">fetch orders</button>
    </div>
    <div v-if="order">
      <ul>
        <li>OrderId: {{ order.orderId }}</li>
        <li>OrderName: {{ order.name }}</li>
        <li>Status: {{ order.statusHistory[0].status }}</li>
      </ul>
    </div>
  </div>
  <div id="content">
    <div id="content-search-form">
      <input type="text" v-model="roleId" />
      <button @click.prevent="fetch_operations">fetch operations</button>
    </div>
    <ul>
      <li v-for="op in operations">{{ op.id }} : {{ op.name }}</li>
    </ul>
  </div>
</template>

<style scoped>
#content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

#content-search-form {
  display: flex;
  flex-direction: row;
  justify-content: start;
  gap: 10px;
}
</style>
