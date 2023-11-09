<script setup>
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
</script>

<template>
  <h1>Vue Playground</h1>
  <section id="operation">
    <div id="operation-search-form">
      <input type="text" v-model="roleId" />
      <button @click.prevent="fetch_operations">fetch operations</button>
    </div>
    <ul>
      <li v-for="op in operations">{{ op.id }} : {{ op.name }}</li>
    </ul>
  </section>
</template>

<style scoped>
#operation {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

#operation-search-form {
  display: flex;
  flex-direction: row;
  justify-content: start;
  gap: 10px;
}
</style>
