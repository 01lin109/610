const BASE = '/api'

interface ApiResult {
  code: number
  message?: string
  error?: string
  data?: any
}

async function authFetch(path: string, options: RequestInit = {}): Promise<ApiResult> {
  const token = localStorage.getItem('token')
  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
    ...(options.headers as Record<string, string>),
  }
  if (token) {
    headers['Authorization'] = `Bearer ${token}`
  }

  const res = await fetch(`${BASE}${path}`, { ...options, headers })
  const data: ApiResult = await res.json()

  if (res.status === 401) {
    localStorage.clear()
    window.location.href = '/login'
    throw new Error(data.error || '未登录')
  }
  return data
}

export async function login(username: string, password: string): Promise<ApiResult> {
  const res = await fetch(`${BASE}/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password }),
  })
  return res.json()
}

export async function register(username: string, password: string, language: string): Promise<ApiResult> {
  const res = await fetch(`${BASE}/register`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password, language }),
  })
  return res.json()
}

export async function queryWord(word: string, provider: string): Promise<ApiResult> {
  return authFetch(`/words/query?word=${encodeURIComponent(word)}&provider=${encodeURIComponent(provider)}`)
}

export async function saveWord(word: string, definition: string, chineseDefinition: string, sentences: string[], aiProvider: string): Promise<ApiResult> {
  return authFetch('/words', {
    method: 'POST',
    body: JSON.stringify({ word, definition, chinese_definition: chineseDefinition, sentences, ai_provider: aiProvider }),
  })
}

export async function listWords(page: number, pageSize: number): Promise<ApiResult> {
  return authFetch(`/words?page=${page}&page_size=${pageSize}`)
}

export async function deleteWord(id: string | number): Promise<ApiResult> {
  return authFetch(`/words/${id}`, { method: 'DELETE' })
}
