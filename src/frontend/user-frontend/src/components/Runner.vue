<template>
  <div> 
    <div class="description">
      {{ data.description }}
    </div>
    <div class="main">
      <textarea class="main_text" v-model="main">{{ main }}
      </textarea>
      <div>
        <texterea class="result" v-model="result" v-html="result">
        </texterea >
      </div>
      <input id="submit" type="submit" @click="onRun" value=">"/>
      <label id="status" >{{ resultStatus }}</label>
    </div>
  </div>
</template>

<script setup> 
  import axios from 'axios'
  import { defineProps, ref, onMounted } from "vue";
  import { useRouter } from "vue-router";

  const msg = ref({})
  const data = ref({})

  const main = defineModel('main')
  const result = defineModel('result')
  const resultStatus = defineModel('resultStatus')
  let stop = false


  const props = defineProps( {
      id: {
        type: Number,
        required: true,
      }
    })

  function onRun(event) {
    // TODO получать данные кейса
    console.log("Run Task: " + data.value.task_id)
    console.log("Request main: " + main.value)

    const delay = ms => new Promise(res => setTimeout(res, ms));

    axios.put('/api/run', {
      "user_id": 1,
      "task_id": 1,
      "engine_type": data.value.engine_type,
      "request_data": {
        "main": main.value
      }
    }).then(
        async function (response) {
          const job_id = JSON.stringify(response.data)
          console.log("jbo_id: " + job_id)

          while(true) {
            axios.get('/api/run/status/' + job_id).then(
                function (res) {
                  if (res.data) {
                    console.log("/status response: " + res.data)
                    const text = res.data.log.filter((str) => str !=='').join("<br />")
                    console.log("text: " + text)

                    result.value = text

                    console.log("res.data: " + JSON.stringify(res.data))
                    if (res.data.status === true) {
                      resultStatus.value = "[ ✓ ]"
                    } else {
                      resultStatus.value = "[ X ]"
                    }
                    stop = true
                  }
                }
            );
            console.log("wait result job_id: " + job_id)
            if (stop === true) {
              break
            }
            await delay(1000)
          }

        }
    )
  }

  onMounted( () => {
      axios.get('/api/task/' + props.id)
        .then(
        function (response) {
            msg.value = JSON.parse(JSON.stringify(response.data))
            console.log("get data: " + msg.value.data)
            data.value = JSON.parse(msg.value.data)
            main.value = data.value.request_data.main
            resultStatus.value = "[ - ]"
            console.log("description: " + data.value.description)
            console.log("main: " + data.value.main)
        }
      )
    })
</script>

<style scoped>

.description {
  position: absolute;
  top: 100px;
  left: 200px;
  width: 400px;
}

.main {
  position: absolute;
  top: 100px;
  left: 700px;
  width: 400px;
  height: 400px;
  border: 1px #dbdcdb;
}

.main #submit {
  position: absolute;
  left: 580px;
  top: 420px;
  width: 30px;
  height: 30px;
}

.main #status {
  position: absolute;
  left: 580px;
  top: 480px;
  width: 30px;
  height: 30px;
  border: 1px #dbdcdb;
}

.result {
  position: absolute;
  top: 420px;
  left: 0px;
  width: 570px;
  height: 400px;
  border: solid #dbdcdb;
}

.main_text {
  width: 600px;
  height: 400px;
}


</style>