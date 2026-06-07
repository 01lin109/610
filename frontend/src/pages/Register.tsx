import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import ukFlag from '../../static/icons8-英国循环-96.png'

interface Language {
  id: string
  name: string
  native: string
  flag: string
  isImage?: boolean
}

const LANGUAGES: Language[] = [
  {
    id: 'en',
    name: '英语',
    native: 'English',
    flag: ukFlag,
    isImage: true,
  },
  {
    id: 'ja',
    name: '日语',
    native: '日本語',
    flag: '🇯🇵',
  },
]

export default function Register() {
  const navigate = useNavigate()
  const [step, setStep] = useState<'language' | 'form'>('language')
  const [selectedLang, setSelectedLang] = useState<string | null>(null)
  const [form, setForm] = useState({ username: '', password: '', confirmPassword: '' })
  const [error, setError] = useState('')

  const handleLanguageSelect = (langId: string) => {
    setSelectedLang(langId)
    setStep('form')
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError('')

    if (!form.username.trim()) {
      setError('请输入用户名')
      return
    }
    if (form.password.length < 6) {
      setError('密码至少6位')
      return
    }
    if (form.password !== form.confirmPassword) {
      setError('两次密码不一致')
      return
    }

    const res = await fetch('/api/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        username: form.username,
        password: form.password,
        language: selectedLang,
      }),
    })
    if (!res.ok) {
      const data = await res.json()
      setError(data.error || '注册失败')
      return
    }
    navigate('/login')
  }

  return (
    <div className="min-h-screen flex flex-col items-center px-6 py-12">
      {/* Back Button */}
      <button
        onClick={() => step === 'form' ? setStep('language') : navigate('/')}
        className="self-start text-duolingo-gray-text hover:text-duolingo-dark-text transition-colors mb-8"
      >
        <svg className="w-8 h-8" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
          <path strokeLinecap="round" strokeLinejoin="round" d="M15 19l-7-7 7-7" />
        </svg>
      </button>

      {/* Step Indicator */}
      <div className="flex items-center gap-2 mb-8">
        <div className={`w-3 h-3 rounded-full ${step === 'language' ? 'bg-duolingo-green' : 'bg-duolingo-green'}`} />
        <div className="w-12 h-0.5 bg-duolingo-gray-border rounded" />
        <div className={`w-3 h-3 rounded-full ${step === 'form' ? 'bg-duolingo-green' : 'bg-duolingo-gray-border'}`} />
      </div>

      {/* Step 1: Language Selection */}
      {step === 'language' && (
        <div className="w-full max-w-md">
          <h2 className="text-3xl font-black text-duolingo-dark-text text-center mb-2">
            你想学习什么语言？
          </h2>
          <p className="text-center text-duolingo-gray-text font-semibold mb-8">
            选择一门语言开始学习
          </p>

          <div className="flex flex-col gap-4">
            {LANGUAGES.map((lang) => (
              <button
                key={lang.id}
                onClick={() => handleLanguageSelect(lang.id)}
                className={`w-full p-5 bg-white rounded-duolingo border-2 border-duolingo-gray-border
                  hover:border-duolingo-green hover:shadow-duolingo-sm
                  active:translate-y-0.5 transition-all duration-150
                  ${selectedLang === lang.id ? 'border-duolingo-green shadow-duolingo-sm' : ''}`}
              >
                <div className="flex items-center gap-4">
                  {lang.isImage ? (
                    <img src={lang.flag} alt="" className="w-12 h-12" />
                  ) : (
                    <span className="text-5xl">{lang.flag}</span>
                  )}
                  <div className="text-left">
                    <p className="text-lg font-bold text-duolingo-dark-text">{lang.name}</p>
                    <p className="text-sm text-duolingo-gray-text">{lang.native}</p>
                  </div>
                </div>
              </button>
            ))}
          </div>
        </div>
      )}

      {/* Step 2: Registration Form */}
      {step === 'form' && (
        <div className="w-full max-w-md">
          <div className="bg-white rounded-duolingo shadow-duolingo-sm p-8">
            <h2 className="text-2xl font-black text-duolingo-dark-text text-center mb-6">
              创建账户
            </h2>

            {/* Selected Language Badge */}
            {(() => {
              const lang = LANGUAGES.find(l => l.id === selectedLang)
              if (!lang) return null
              return (
                <div className="flex justify-center mb-6">
                  <div className="inline-flex items-center gap-2 px-4 py-2 bg-gray-50 rounded-full border border-duolingo-gray-border">
                    {lang.isImage ? (
                      <img src={lang.flag} alt="" className="w-6 h-6" />
                    ) : (
                      <span className="text-xl">{lang.flag}</span>
                    )}
                    <span className="font-bold text-duolingo-dark-text">{lang.name}</span>
                  </div>
                </div>
              )
            })()}

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
                  placeholder="至少6位密码"
                  className="w-full px-4 py-3 border-2 border-duolingo-gray-border rounded-xl
                    focus:border-duolingo-green focus:outline-none transition-colors
                    text-base font-semibold text-duolingo-dark-text"
                />
              </div>

              <div>
                <label className="block text-sm font-bold text-duolingo-gray-text mb-1">
                  确认密码
                </label>
                <input
                  type="password"
                  value={form.confirmPassword}
                  onChange={(e) => setForm({ ...form, confirmPassword: e.target.value })}
                  placeholder="再次输入密码"
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
                className="w-full py-4 mt-2 bg-duolingo-green text-white text-lg font-black
                  rounded-duolingo shadow-duolingo-btn hover:bg-duolingo-green-hover
                  active:translate-y-0.5 transition-all duration-150"
              >
                注册
              </button>
            </form>
          </div>
        </div>
      )}
    </div>
  )
}
