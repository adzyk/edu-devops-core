<template>
  <div> 
    <li v-for="(line, index) in msg.tasks">
        <a v-bind:href="'/task/' + line.id" >{{ line.name }}</a>
    </li>
  </div>
</template>

<script setup> 
  import axios from 'axios'
  import { defineProps, ref, onMounted } from "vue";
  import { useRouter } from "vue-router";

  const msg = ref({})

  const props = defineProps( {
      id: {
        type: Number,
        required: true,
      }
    })

  onMounted( () => {
      axios.get('/api/theme/' + props.id)
        .then(
        function (response) {
            console.log('RESPONSE-->' + response.data + '<---')
            msg.value = response.data
        }
      )
    })
</script>