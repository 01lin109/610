<template>
  <div class="page-container">
    <el-card>
      <template #header><div class="page-header"><span>用户管理</span><el-button type="primary" @click="handleAdd">新增用户</el-button></div></template>
      <el-form :inline="true" :model="query" class="search-form">
        <el-form-item label="用户名"><el-input v-model="query.keyword" placeholder="用户名" clearable /></el-form-item>
        <el-form-item><el-button type="primary" @click="fetchData">搜索</el-button></el-form-item>
      </el-form>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="username" label="用户名" width="120" />
        <el-table-column prop="nickname" label="昵称" width="120" />
        <el-table-column prop="phone" label="手机号" width="130" />
        <el-table-column prop="email" label="邮箱" min-width="150" />
        <el-table-column prop="status" label="状态" width="80"><template #default="{row}"><el-tag :type="row.status===1?'success':'danger'">{{ row.status===1?'启用':'禁用' }}</el-tag></template></el-table-column>
        <el-table-column label="操作" width="200"><template #default="{row}"><el-button size="small" @click="handleEdit(row)">编辑</el-button><el-button size="small" :type="row.status===1?'danger':'success'" @click="toggleStatus(row)">{{ row.status===1?'禁用':'启用' }}</el-button><el-popconfirm title="确定删除?" @confirm="handleDel(row.id)"><template #reference><el-button size="small" type="danger">删除</el-button></template></el-popconfirm></template></el-table-column>
      </el-table>
    </el-card>
    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="500px" @close="resetForm">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="用户名" prop="username"><el-input v-model="form.username" :disabled="isEdit" /></el-form-item>
        <el-form-item label="密码" v-if="!isEdit" prop="password"><el-input v-model="form.password" type="password" /></el-form-item>
        <el-form-item label="昵称"><el-input v-model="form.nickname" /></el-form-item>
        <el-form-item label="手机号"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="邮箱"><el-input v-model="form.email" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="submitForm">确定</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getUserList, getUser, addUser, updateUser, deleteUser, updateUserStatus } from '@/api/permission'
import { ElMessage } from 'element-plus'
const loading = ref(false), tableData = ref([]), dialogVisible = ref(false), dialogTitle = ref('新增用户'), formRef = ref(null), isEdit = ref(false)
const query = reactive({ keyword: '' })
const form = reactive({ id: null, username: '', password: '', nickname: '', phone: '', email: '' })
const rules = { username: [{ required: true }], password: [{ required: true, message: '请输入密码', trigger: 'blur' }] }
const fetchData = async () => { loading.value = true; try { const res = await getUserList(query); tableData.value = res.data.records || res.data || [] } catch {} loading.value = false }
const handleAdd = () => { isEdit.value = false; dialogTitle.value = '新增用户'; dialogVisible.value = true }
const handleEdit = async row => { isEdit.value = true; dialogTitle.value = '编辑用户'; Object.assign(form, row); dialogVisible.value = true }
const handleDel = async id => { try { await deleteUser(id); ElMessage.success('删除成功'); fetchData() } catch (e) { ElMessage.error('删除失败') } }
const toggleStatus = async row => { try { const ns = row.status === 1 ? 0 : 1; await updateUserStatus(row.id, ns); ElMessage.success('状态已更新'); fetchData() } catch (e) { ElMessage.error('操作失败') } }
const resetForm = () => { formRef.value?.resetFields(); Object.assign(form, { id: null, username: '', password: '', nickname: '', phone: '', email: '' }) }
const submitForm = async () => {
  const valid = await formRef.value.validate().catch(() => false); if (!valid) return
  try {
    isEdit.value ? await updateUser(form) : await addUser(form)
    ElMessage.success(isEdit.value ? '更新成功' : '新增成功'); dialogVisible.value = false; fetchData()
  } catch (e) { ElMessage.error(isEdit.value ? '更新失败' : '新增失败') }
}
onMounted(fetchData)
</script>
<style scoped>.page-container { padding: 0; } .page-header { display: flex; justify-content: space-between; align-items: center; } .search-form { margin-bottom: 16px; }</style>