<script setup>
import { ref } from "vue";

const orders = ref([
  { id: "1", name: "order-1" },
  { id: "2", name: "order-2" },
]);

function updateOrders() {
  console.log("fetch orders");
}
</script>

<template>
  <h1>Vue Playground</h1>
  <button @click.prevent="updateOrders">update orders</button>
  <div v-for="order in orders" :id="order.id">
    {{ order.name }}
  </div>
</template>

<style scoped></style>
