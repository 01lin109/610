import { useState } from 'react'
import { useNavigate } from 'react-router-dom'

export default function Login() {
  const navigate = useNavigate()
  const [form, setForm] = useState({ username: '', password: '' })
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError('')

    if (!form.username.trim()) {
      setError('请输入用户名')
      return
    }
    if (!form.password) {
      setError('请输入密码')
      return
    }

    setLoading(true)
    try {
      const res = await fetch('/api/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          username: form.username,
          password: form.password,
        }),
      })
      const data = await res.json()
      if (data.code !== 0) {
        setError(data.error || '登录失败')
        return
      }
      // 保存 token 和用户信息
      localStorage.setItem('token', data.data.token)
      localStorage.setItem('username', data.data.username)
      localStorage.setItem('language', data.data.language)
      navigate('/dashboard')
    } catch {
      setError('网络错误')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen flex flex-col items-center px-6 py-12">
      {/* Back Button */}
      <button
        onClick={() => navigate('/')}
        className="self-start text-duolingo-gray-text hover:text-duolingo-dark-text transition-colors mb-8"
      >
        <svg className="w-8 h-8" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
          <path strokeLinecap="round" strokeLinejoin="round" d="M15 19l-7-7 7-7" />
        </svg>
      </button>

      {/* Header */}
      <div className="text-center mb-10">
        <div className="text-6xl mb-4">📖</div>
        <h1 className="text-3xl font-black text-duolingo-dark-text mb-2">
          欢迎回来
        </h1>
        <p className="text-duolingo-gray-text font-semibold">
          登录继续你的学习
        </p>
      </div>

      {/* Login Card */}
      <div className="w-full max-w-md bg-white rounded-duolingo shadow-duolingo-sm p-8">
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <div>
            <label className="block text-sm font-bold text-duolingo-gray-text mb-1">
              用户名
            </label>
            <input
              type="text"
              value={form.username}
              onChange={(e) => setForm({ ...form, username: e.target.value })}
              placeholder="请输入用户名"
              className="w-full px-4 py-3 border-2 border-duolingo-gray-border rounded-xl
                focus:border-duolingo-green focus:outline-none transition-colors
                text-base font-semibold text-duolingo-dark-text"
            />
          </div>

          <div>
            <label className="block text-sm font-bold text-duolingo-gray-text mb-1">
              密码
            </label>
            <input
              type="password"
              value={form.password}
              onChange={(e) => setForm({ ...form, password: e.target.value })}
              placeholder="请输入密码"
              className="w-full px-4 py-3 border-2 border-duolingo-gray-border rounded-xl
                focus:border-duolingo-green focus:outline-none transition-colors
                text-base font-semibold text-duolingo-dark-text"
            />
          </div>

          {error && (
            <p className="text-duolingo-red text-sm font-bold text-center">{error}</p>
          )}

          <button
            type="submit"
            disabled={loading}
            className="w-full py-4 mt-2 bg-duolingo-green text-white text-lg font-black
              rounded-duolingo shadow-duolingo-btn hover:bg-duolingo-green-hover
              active:translate-y-0.5 transition-all duration-150
              disabled:opacity-50 disabled:cursor-not-allowed disabled:active:translate-y-0"
          >
            {loading ? '登录中...' : '登录'}
          </button>
        </form>

        <div className="mt-6 text-center">
          <p className="text-duolingo-gray-text font-semibold">
            还没有账户？{' '}
            <button
              onClick={() => navigate('/register')}
              className="text-duolingo-green font-black hover:underline"
            >
              注册
            </button>
          </p>
        </div>
      </div>
    </div>
  )
}
