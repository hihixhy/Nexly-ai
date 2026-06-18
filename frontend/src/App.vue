<template>
  <div style="padding: 40px;">
    <h1>Nexly AI</h1>

    <textarea
      v-model="prompt"
      placeholder="请输入你的需求，比如：写一个Vue登录页"
      style="width: 400px; height: 120px;"
    />

    <br /><br />

    <button @click="generateCode">生成代码</button>

    <div v-for="(msg, index) in messages" :key="index">
      <div v-if="msg.role === 'user'" style="color: blue;">
        👤 {{ msg.content }}
      </div>

      <div v-else class="markdown-body">
        🤖 <div v-html="md.render(msg.content)"></div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import axios from "axios"
import hljs from "highlight.js"
import "highlight.js/styles/github.css"
import MarkdownIt from "markdown-it"
import { ref } from "vue"

const prompt = ref<string>("")
const messages = ref<Message[]>([])
interface Message {
  role: "user" | "assistant"
  content: string
}

interface Result<T> {
  code: number
  message: string
  data: T
}

const md = new MarkdownIt({
  highlight: function (str, lang) {
    if (lang && hljs.getLanguage(lang)) {
      return hljs.highlight(str, { language: lang }).value
    }
    return ""
  }
})

const generateCode = async () => {
  messages.value.push({
    role: "user",
    content: prompt.value
  })

  try {
    const res = await axios.post<Result<string>>(
      "http://localhost:8080/api/generate",
      {
        prompt: prompt.value
      }
    )

    messages.value.push({
      role: "assistant",
      content: res.data.data
    })

    prompt.value = ""
  } catch (err) {
    messages.value.push({
      role: "assistant",
      content: "请求失败，请检查后端" + err
    })
  }
}
</script>

<style scoped>
.markdown-body {
  padding: 20px;
  background: #fff;
  border-radius: 8px;
}
</style>