import request from './request'
export const getSalesOverview = () => request.get('/statistics/sales/overview')
export const getOrderStats = params => request.get('/statistics/orders', { params })
export const getProductRank = limit => request.get('/statistics/products/rank', { params: { limit } })
export const getDailySales = days => request.get('/statistics/sales/daily', { params: { days } })
