import request from './request'
export const getMemberList = params => request.get('/member/list', { params })
export const getMember = id => request.get(`/member/${id}`)
export const updateMember = data => request.put('/member', data)
export const updateMemberStatus = (id, status) => request.put(`/member/${id}/status/${status}`)
