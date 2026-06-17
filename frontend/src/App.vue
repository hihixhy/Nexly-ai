<template>
  <div style="padding: 40px;">
    <h1>CodeMaster AI</h1>

    <textarea
      v-model="prompt"
      placeholder="请输入你的需求，比如：写一个Vue登录页"
      style="width: 400px; height: 120px;"
    />

    <br /><br />

    <button @click="generateCode">生成代码</button>

    <pre v-if="code" style="margin-top: 20px;">{{ code }}</pre>
  </div>
</template>

<script setup lang="ts">
import axios from "axios"
import { ref } from "vue"

const prompt = ref<string>("")
const code = ref<string>("")

interface AIResponse {
  code: string
}

const generateCode = async () => {
  try {
    const res = await axios.post<AIResponse>(
      "http://localhost:8080/api/generate",
      {
        prompt: prompt.value
      }
    )

    code.value = res.data.code
  } catch (err) {
    console.error("请求失败", err)
    code.value = "请求失败，请检查后端"
  }
}
</script>