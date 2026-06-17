import request from './request'
export const getOrderList = params => request.get('/order/list', { params })
export const getOrder = id => request.get(`/order/${id}`)
export const deleteOrder = id => request.delete(`/order/${id}`)
export const updateOrderStatus = (id, status) => request.put(`/order/${id}/status/${status}`)
