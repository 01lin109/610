import { useNavigate, Navigate } from 'react-router-dom'

export default function Home() {
  const navigate = useNavigate()
  const token = localStorage.getItem('token')
  if (token) return <Navigate to="/dashboard" replace />

  return (
    <div className="min-h-screen flex flex-col items-center justify-center px-6">
      {/* Logo & Title */}
      <div className="text-center mb-16">
        <div className="text-7xl mb-6">📖</div>
        <h1 className="text-4xl font-black text-duolingo-dark-text mb-2">
          AI 单词本
        </h1>
        <p className="text-lg text-duolingo-gray-text font-semibold">
          智能学习，轻松记单词
        </p>
      </div>

      {/* Action Buttons */}
      <div className="w-full max-w-sm flex flex-col gap-4">
        <button
          onClick={() => navigate('/register')}
          className="w-full py-4 bg-duolingo-green text-white text-lg font-black rounded-duolingo shadow-duolingo-btn hover:bg-duolingo-green-hover active:translate-y-0.5 transition-all duration-150"
        >
          开始学习
        </button>

        <button
          onClick={() => navigate('/login')}
          className="w-full py-4 bg-white text-duolingo-dark-text text-lg font-black rounded-duolingo border-2 border-duolingo-gray-border shadow-duolingo-btn-white hover:bg-gray-50 active:translate-y-0.5 transition-all duration-150"
        >
          已有账户
        </button>
      </div>

      {/* Footer */}
      <p className="mt-16 text-sm text-duolingo-gray-text">
        每天几分钟，轻松掌握新单词
      </p>
    </div>
  )
}
