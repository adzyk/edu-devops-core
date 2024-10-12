<template>
  <div>
    <h2>ID: {{ user.id }}</h2>
    <h2>{{ user.name }} {{ user.family}}</h2>
    <h2>email: {{ user.email }}</h2>
  </div>
  <div id="chart"></div>
</template>

<script setup>

import axios from 'axios'
import { ref, onMounted } from 'vue'
import { BarChart } from 'chartist';

const user = ref({})

onMounted(() => {
  axios.get('/api/user/current')
    .then(response => {
      user.value = response.data
      console.log(response.data)

      console.log("data: ", response.data.userStatistic['labels'])

      new BarChart('#chart', {
        labels: response.data.userStatistic['labels'],
        series: response.data.userStatistic['series']
      }, {
        high: 10,
        low: 0,
        axisX: {
          labelInterpolationFnc: (value, index) => (index % 2 === 0 ? value : null)
        }
      });
    })
})
</script>

<style>
@import 'chartist/dist/index.css';
</style>