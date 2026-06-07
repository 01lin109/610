import { useState, useEffect, useCallback } from 'react'
import { useNavigate } from 'react-router-dom'
import { queryWord, saveWord, listWords, deleteWord } from '../lib/api.ts'

interface WordItem {
  id: string
  word: string
  definition: string
  chinese_definition: string
  sentences: string[]
  ai_provider: string
  created_at: string
}

interface QueryResult {
  word: string
  definition: string
  chinese_definition: string
  sentences: string[]
  ai_provider: string
  from_cache: boolean
}

const PROVIDERS = [
  { value: 'deepseek', label: 'DeepSeek' },
  { value: 'qwen', label: '通义千问' },
]

export default function Dashboard() {
  const navigate = useNavigate()
  const username = localStorage.getItem('username') || ''

  // Search state
  const [searchWord, setSearchWord] = useState('')
  const [provider, setProvider] = useState('deepseek')
  const [searching, setSearching] = useState(false)
  const [queryResult, setQueryResult] = useState<QueryResult | null>(null)
  const [searchError, setSearchError] = useState('')

  // Save state
  const [saving, setSaving] = useState(false)
  const [saveMsg, setSaveMsg] = useState('')

  // List state
  const [words, setWords] = useState<WordItem[]>([])
  const [total, setTotal] = useState(0)
  const [page, setPage] = useState(1)
  const [pageSize] = useState(10)
  const [loadingList, setLoadingList] = useState(false)
  const [deletingId, setDeletingId] = useState<string | null>(null)
  const [listError, setListError] = useState('')

  // Detail modal state
  const [detailWord, setDetailWord] = useState<WordItem | null>(null)

  // Delete confirmation state
  const [confirmDelete, setConfirmDelete] = useState<WordItem | null>(null)

  const totalPages = Math.max(1, Math.ceil(total / pageSize))

  const fetchWords = useCallback(async (p: number) => {
    setLoadingList(true)
    setListError('')
    try {
      const res = await listWords(p, pageSize)
      if (res.code === 0 && res.data) {
        setWords(res.data.items || [])
        setTotal(res.data.total || 0)
      } else {
        setListError(res.error || '加载失败')
      }
    } catch (e: any) {
      setListError(e.message || '网络错误')
    } finally {
      setLoadingList(false)
    }
  }, [pageSize])

  useEffect(() => {
    fetchWords(page)
  }, [page, fetchWords])

  const handleSearch = async () => {
    const trimmed = searchWord.trim()
    if (!trimmed) return

    setSearching(true)
    setSearchError('')
    setQueryResult(null)
    setSaveMsg('')

    try {
      const res = await queryWord(trimmed, provider)
      if (res.code === 0 && res.data) {
        setQueryResult(res.data as QueryResult)
      } else {
        setSearchError(res.error || '查询失败')
      }
    } catch (e: any) {
      setSearchError(e.message || '网络错误')
    } finally {
      setSearching(false)
    }
  }

  const handleSave = async () => {
    if (!queryResult) return

    setSaving(true)
    setSaveMsg('')
    try {
      const res = await saveWord(
        queryResult.word,
        queryResult.definition,
        queryResult.chinese_definition,
        queryResult.sentences,
        queryResult.ai_provider,
      )
      if (res.code === 0) {
        setSaveMsg('saved')
        setQueryResult(null)
        setSearchWord('')
        // Refresh list to show new word
        fetchWords(1)
        setPage(1)
      } else if (res.error?.includes('已存在')) {
        setSaveMsg('exists')
      } else {
        setSaveMsg('error')
      }
    } catch {
      setSaveMsg('error')
    } finally {
      setSaving(false)
    }
  }

  const handleDelete = async (id: string) => {
    setDeletingId(id)
    try {
      await deleteWord(id)
      fetchWords(page)
    } catch {
      // error handled by api.ts
    } finally {
      setDeletingId(null)
      setConfirmDelete(null)
    }
  }

  const handleLogout = () => {
    localStorage.clear()
    navigate('/login')
  }

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter') handleSearch()
  }

  return (
    <div className="min-h-screen bg-duolingo-gray-bg">
      {/* ===== Header ===== */}
      <header className="bg-white border-b-2 border-duolingo-gray-border sticky top-0 z-30">
        <div className="max-w-4xl mx-auto px-4 h-16 flex items-center justify-between">
          <div className="flex items-center gap-2">
            <span className="text-2xl">🔤</span>
            <h1 className="text-xl font-black text-duolingo-dark-text">AI 单词本</h1>
          </div>
          <div className="flex items-center gap-4">
            <span className="text-sm font-bold text-duolingo-gray-text">{username}</span>
            <button
              onClick={handleLogout}
              className="px-4 py-2 text-sm font-bold text-duolingo-red hover:bg-red-50 rounded-xl transition-colors"
            >
              退出
            </button>
          </div>
        </div>
      </header>

      <main className="max-w-4xl mx-auto px-4 py-6 pb-24 flex flex-col gap-6">
        {/* ===== Search Section ===== */}
        <section className="bg-white rounded-duolingo shadow-duolingo-sm p-6">
          <h2 className="text-lg font-black text-duolingo-dark-text mb-4 flex items-center gap-2">
            <span>🔍</span> 查询单词
          </h2>

          <div className="flex flex-col sm:flex-row gap-3">
            <div className="flex-1">
              <input
                type="text"
                value={searchWord}
                onChange={(e) => setSearchWord(e.target.value)}
                onKeyDown={handleKeyDown}
                placeholder="输入要查询的单词..."
                className="w-full px-4 py-3 border-2 border-duolingo-gray-border rounded-xl
                  focus:border-duolingo-green focus:outline-none transition-colors
                  text-base font-bold text-duolingo-dark-text placeholder:text-duolingo-gray-text"
              />
            </div>
            <select
              value={provider}
              onChange={(e) => setProvider(e.target.value)}
              className="px-4 py-3 border-2 border-duolingo-gray-border rounded-xl
                focus:border-duolingo-green focus:outline-none transition-colors
                text-base font-bold text-duolingo-dark-text bg-white cursor-pointer"
            >
              {PROVIDERS.map((p) => (
                <option key={p.value} value={p.value}>{p.label}</option>
              ))}
            </select>
            <button
              onClick={handleSearch}
              disabled={searching || !searchWord.trim()}
              className="px-8 py-3 bg-duolingo-green text-white text-base font-black
                rounded-duolingo shadow-duolingo-btn hover:bg-duolingo-green-hover
                active:translate-y-0.5 transition-all duration-150
                disabled:opacity-50 disabled:cursor-not-allowed disabled:active:translate-y-0
                cursor-pointer"
            >
              {searching ? (
                <span className="flex items-center gap-2">
                  <Spinner /> 查询中...
                </span>
              ) : (
                '🔍 查询'
              )}
            </button>
          </div>

          {searchError && (
            <p className="mt-3 text-sm font-bold text-duolingo-red">{searchError}</p>
          )}
        </section>

        {/* ===== Result Card ===== */}
        {queryResult && (
          <section className="bg-white rounded-duolingo shadow-duolingo-sm p-6 border-l-4 border-duolingo-green">
            <div className="flex items-start justify-between mb-3">
              <div>
                <h3 className="text-2xl font-black text-duolingo-dark-text">
                  {queryResult.word}
                </h3>
                <p className="text-base font-semibold text-duolingo-dark-text mt-2">
                  {queryResult.definition}
                </p>
                {queryResult.chinese_definition && (
                  <p className="text-base font-bold text-duolingo-gray-text mt-1">
                    {queryResult.chinese_definition}
                  </p>
                )}
              </div>
              <div className="flex gap-2 flex-shrink-0">
                <span className="px-3 py-1 text-xs font-bold bg-duolingo-green/10 text-duolingo-green-dark rounded-full">
                  {queryResult.ai_provider}
                </span>
                {queryResult.from_cache && (
                  <span className="px-3 py-1 text-xs font-bold bg-duolingo-blue/10 text-duolingo-blue rounded-full">
                    来自缓存
                  </span>
                )}
              </div>
            </div>

            {/* Sentences */}
            <div className="mb-4">
              <p className="text-sm font-bold text-duolingo-gray-text mb-2">💬 例句</p>
              <ul className="space-y-1.5">
                {queryResult.sentences.map((s, i) => (
                  <li key={i} className="flex items-start gap-2 text-base font-semibold text-duolingo-dark-text">
                    <span className="text-duolingo-green mt-1 flex-shrink-0">•</span>
                    <span>{s}</span>
                  </li>
                ))}
              </ul>
            </div>

            {/* Save button */}
            {!queryResult.from_cache && (
              <button
                onClick={handleSave}
                disabled={saving}
                className="w-full sm:w-auto px-8 py-3 bg-duolingo-green text-white text-base font-black
                  rounded-duolingo shadow-duolingo-btn hover:bg-duolingo-green-hover
                  active:translate-y-0.5 transition-all duration-150
                  disabled:opacity-50 disabled:cursor-not-allowed disabled:active:translate-y-0
                  cursor-pointer"
              >
                {saving ? (
                  <span className="flex items-center gap-2"><Spinner /> 保存中...</span>
                ) : (
                  '💾 保存到单词本'
                )}
              </button>
            )}

            {queryResult.from_cache && (
              <div className="inline-block px-4 py-2 bg-duolingo-blue/10 text-duolingo-blue text-sm font-bold rounded-xl">
                ✅ 该单词已在单词本中
              </div>
            )}

            {saveMsg === 'saved' && (
              <p className="mt-2 text-sm font-bold text-duolingo-green">✅ 已保存到单词本</p>
            )}
            {saveMsg === 'exists' && (
              <p className="mt-2 text-sm font-bold text-duolingo-orange">⚠️ 该单词已存在</p>
            )}
            {saveMsg === 'error' && (
              <p className="mt-2 text-sm font-bold text-duolingo-red">❌ 保存失败，请重试</p>
            )}
          </section>
        )}

        {/* ===== Word List ===== */}
        <section className="bg-white rounded-duolingo shadow-duolingo-sm p-6">
          <h2 className="text-lg font-black text-duolingo-dark-text mb-4 flex items-center gap-2">
            <span>📚</span> 我的单词本
            {total > 0 && (
              <span className="text-sm font-bold text-duolingo-gray-text">({total})</span>
            )}
          </h2>

          {loadingList && words.length === 0 ? (
            <div className="flex justify-center py-12">
              <Spinner />
            </div>
          ) : listError ? (
            <div className="text-center py-8">
              <p className="text-sm font-bold text-duolingo-red">{listError}</p>
              <button
                onClick={() => fetchWords(page)}
                className="mt-3 px-6 py-2 bg-duolingo-green text-white text-sm font-bold
                  rounded-duolingo shadow-duolingo-btn hover:bg-duolingo-green-hover
                  active:translate-y-0.5 transition-all duration-150 cursor-pointer"
              >
                重试
              </button>
            </div>
          ) : words.length === 0 ? (
            <div className="text-center py-12">
              <p className="text-4xl mb-3">📭</p>
              <p className="text-base font-bold text-duolingo-gray-text">还没有保存单词</p>
              <p className="text-sm font-semibold text-duolingo-gray-text mt-1">
                在上方搜索并保存你的第一个单词吧
              </p>
            </div>
          ) : (
            <>
              <div className="space-y-2">
                {words.map((w) => (
                  <div
                    key={w.id}
                    onClick={() => setDetailWord(w)}
                    className="flex items-center justify-between p-4 rounded-xl border-2 border-duolingo-gray-border
                      hover:border-duolingo-green/50 hover:shadow-duolingo-sm transition-all duration-150 cursor-pointer"
                  >
                    <div className="min-w-0 flex-1">
                      <div className="flex items-center gap-2 flex-wrap">
                        <span className="text-lg font-black text-duolingo-dark-text">{w.word}</span>
                        <span className="px-2 py-0.5 text-xs font-bold bg-duolingo-green/10 text-duolingo-green-dark rounded-full">
                          {w.ai_provider}
                        </span>
                      </div>
                      <p className="text-sm font-semibold text-duolingo-dark-text mt-0.5 truncate">
                        {w.definition}
                      </p>
                      {w.chinese_definition && (
                        <p className="text-sm font-bold text-duolingo-gray-text truncate">
                          {w.chinese_definition}
                        </p>
                      )}
                    </div>
                    <button
                      onClick={(e) => { e.stopPropagation(); setConfirmDelete(w) }}
                      disabled={deletingId === w.id}
                      className="ml-3 p-2 text-duolingo-gray-text hover:text-duolingo-red hover:bg-red-50
                        rounded-xl transition-all duration-150 cursor-pointer flex-shrink-0"
                      title="删除"
                    >
                      {deletingId === w.id ? (
                        <Spinner />
                      ) : (
                        <svg className="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                          <path strokeLinecap="round" strokeLinejoin="round"
                            d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                        </svg>
                      )}
                    </button>
                  </div>
                ))}
              </div>

              {/* Pagination */}
              {totalPages > 1 && (
                <div className="flex items-center justify-center gap-2 mt-6">
                  <button
                    onClick={() => setPage((p) => Math.max(1, p - 1))}
                    disabled={page <= 1}
                    className="w-10 h-10 flex items-center justify-center rounded-xl border-2 border-duolingo-gray-border
                      font-bold text-duolingo-dark-text hover:border-duolingo-green transition-colors
                      disabled:opacity-30 disabled:cursor-not-allowed cursor-pointer"
                  >
                    ◀
                  </button>

                  {Array.from({ length: Math.min(totalPages, 5) }, (_, i) => {
                    const start = Math.max(1, Math.min(page - 2, totalPages - 4))
                    const p = start + i
                    if (p > totalPages) return null
                    return (
                      <button
                        key={p}
                        onClick={() => setPage(p)}
                        className={`w-10 h-10 rounded-xl font-bold transition-all duration-150 cursor-pointer ${
                          p === page
                            ? 'bg-duolingo-green text-white shadow-duolingo-btn active:translate-y-0.5'
                            : 'border-2 border-duolingo-gray-border text-duolingo-dark-text hover:border-duolingo-green'
                        }`}
                      >
                        {p}
                      </button>
                    )
                  })}

                  <button
                    onClick={() => setPage((p) => Math.min(totalPages, p + 1))}
                    disabled={page >= totalPages}
                    className="w-10 h-10 flex items-center justify-center rounded-xl border-2 border-duolingo-gray-border
                      font-bold text-duolingo-dark-text hover:border-duolingo-green transition-colors
                      disabled:opacity-30 disabled:cursor-not-allowed cursor-pointer"
                  >
                    ▶
                  </button>
                </div>
              )}
            </>
          )}
        </section>
      </main>

      {/* ===== Word Detail Modal ===== */}
      {detailWord && (
        <div
          className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/40"
          onClick={() => setDetailWord(null)}
        >
          <div
            className="bg-white rounded-duolingo shadow-duolingo p-6 w-full max-w-lg max-h-[90vh] overflow-y-auto"
            onClick={(e) => e.stopPropagation()}
          >
            {/* Header */}
            <div className="flex items-start justify-between mb-4">
              <div className="flex-1 min-w-0">
                <h3 className="text-2xl font-black text-duolingo-dark-text">
                  {detailWord.word}
                </h3>
                <p className="text-base font-semibold text-duolingo-dark-text mt-2">
                  {detailWord.definition}
                </p>
                {detailWord.chinese_definition && (
                  <p className="text-base font-bold text-duolingo-gray-text mt-1">
                    {detailWord.chinese_definition}
                  </p>
                )}
              </div>
              <button
                onClick={() => setDetailWord(null)}
                className="ml-4 p-1 text-duolingo-gray-text hover:text-duolingo-dark-text transition-colors cursor-pointer flex-shrink-0"
              >
                <svg className="w-6 h-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                  <path strokeLinecap="round" strokeLinejoin="round" d="M6 18L18 6M6 6l12 12" />
                </svg>
              </button>
            </div>

            {/* Badge */}
            <div className="mb-4">
              <span className="inline-block px-3 py-1 text-xs font-bold bg-duolingo-green/10 text-duolingo-green-dark rounded-full">
                {detailWord.ai_provider}
              </span>
              <span className="ml-2 text-xs font-semibold text-duolingo-gray-text">
                保存于 {new Date(detailWord.created_at).toLocaleDateString('zh-CN')}
              </span>
            </div>

            {/* Sentences */}
            {detailWord.sentences && detailWord.sentences.length > 0 && (
              <div className="mb-4">
                <p className="text-sm font-bold text-duolingo-gray-text mb-2">💬 例句</p>
                <ul className="space-y-1.5">
                  {detailWord.sentences.map((s, i) => (
                    <li key={i} className="flex items-start gap-2 text-base font-semibold text-duolingo-dark-text">
                      <span className="text-duolingo-green mt-1 flex-shrink-0">•</span>
                      <span>{s}</span>
                    </li>
                  ))}
                </ul>
              </div>
            )}

            {/* Close button */}
            <button
              onClick={() => setDetailWord(null)}
              className="w-full py-3 mt-2 bg-duolingo-green text-white text-base font-black
                rounded-duolingo shadow-duolingo-btn hover:bg-duolingo-green-hover
                active:translate-y-0.5 transition-all duration-150 cursor-pointer"
            >
              关闭
            </button>
          </div>
        </div>
      )}

      {/* ===== Delete Confirmation Modal ===== */}
      {confirmDelete && (
        <div
          className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/40"
          onClick={() => setConfirmDelete(null)}
        >
          <div
            className="bg-white rounded-duolingo shadow-duolingo p-6 w-full max-w-sm mx-4"
            onClick={(e) => e.stopPropagation()}
          >
            <div className="text-center mb-6">
              <div className="text-4xl mb-3">🗑️</div>
              <h3 className="text-lg font-black text-duolingo-dark-text mb-2">
                确认删除
              </h3>
              <p className="text-base font-semibold text-duolingo-gray-text">
                确定要删除「<span className="text-duolingo-dark-text">{confirmDelete.word}</span>」吗？
              </p>
              <p className="text-sm font-bold text-duolingo-gray-text mt-1">
                删除后不可恢复
              </p>
            </div>
            <div className="flex gap-3">
              <button
                onClick={() => setConfirmDelete(null)}
                className="flex-1 py-3 border-2 border-duolingo-gray-border text-duolingo-dark-text
                  text-base font-black rounded-duolingo hover:bg-gray-50
                  active:translate-y-0.5 transition-all duration-150 cursor-pointer"
              >
                取消
              </button>
              <button
                onClick={() => handleDelete(confirmDelete.id)}
                disabled={deletingId === confirmDelete.id}
                className="flex-1 py-3 bg-duolingo-red text-white text-base font-black
                  rounded-duolingo shadow-duolingo-btn hover:bg-red-600
                  active:translate-y-0.5 transition-all duration-150
                  disabled:opacity-50 disabled:cursor-not-allowed disabled:active:translate-y-0
                  cursor-pointer"
              >
                {deletingId === confirmDelete.id ? (
                  <span className="flex items-center justify-center gap-2"><Spinner /> 删除中...</span>
                ) : (
                  '确认删除'
                )}
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}

function Spinner() {
  return (
    <svg className="animate-spin h-4 w-4" viewBox="0 0 24 24" fill="none">
      <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" />
      <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z" />
    </svg>
  )
}
