import request from './request'
export const getConfigList = () => request.get('/setting/configs')
export const getConfigByType = type => request.get(`/setting/configs/type/${type}`)
export const addConfig = data => request.post('/setting/configs', data)
export const updateConfig = data => request.put('/setting/configs', data)
export const deleteConfig = id => request.delete(`/setting/configs/${id}`)
export const updateConfigBatch = data => request.put('/setting/configs/batch', data)
