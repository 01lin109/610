import request from './request'
export const getTransactionList = params => request.get('/finance/transactions', { params })
export const getTransaction = id => request.get(`/finance/transactions/${id}`)
export const getRefundList = params => request.get('/finance/refunds', { params })
export const getRefund = id => request.get(`/finance/refunds/${id}`)
export const approveRefund = (id, data) => request.put(`/finance/refunds/${id}/approve`, data)
export const rejectRefund = (id, data) => request.put(`/finance/refunds/${id}/reject`, data)
export const getPendingRefundCount = () => request.get('/finance/refunds/pending/count')
