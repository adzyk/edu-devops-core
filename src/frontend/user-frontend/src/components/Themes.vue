<template>
  <div class="listThemes">
    <li v-for="(line, index) in msg">
        <a v-bind:href="'/theme/' + line.id" >#{{ index+1 }} - {{ line.name }}</a>
    </li>
    <br><br><br>
    <div class='add-theme' v-if="getRole() === 'CURATOR'">
      <input v-model="nameTheme" placeholder="новое имя темы" />
      <label>Активная:</label>
      <input v-model="isActiveNewTheme" type="checkbox" id="checkbox"/>
      <button @click="postTheme()">add</button>
    </div>
  </div>
</template>


<script setup>
import axios from 'axios'
import { ref, onMounted } from 'vue'
import { getRole } from '@/plugins/keycloak'
const msg = ref({})
const role = ref({})
const nameTheme = defineModel('nameTheme')
const isActiveNewTheme = defineModel('isActiveNewTheme')

onMounted(() => {
  axios.get('/api/themes')
    .then(
        function (response) {
            msg.value = response.data
        }
    )
  console.log("YOUR ROLE: " + getRole())
  role.value = getRole()
})

function postTheme(event) {
  axios.post('/api/theme', {
    'name': nameTheme.value,
    'active': isActiveNewTheme.value,
  }).then(
      function (response) {
        console.log('result save: ' + response.data)
      }

  )
}
</script>

<style>

.add-theme {
  margin: 10px;
  padding: 10px;
}

.add-theme input {
  margin: 10px;
}

</style>