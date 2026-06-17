<template>
  <div class="page-container">
    <el-card>
      <template #header><div class="page-header"><span>部门管理</span><el-button type="primary" @click="handleAdd">新增部门</el-button></div></template>
      <el-table :data="tableData" border stripe v-loading="loading" row-key="id" default-expand-all>
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="deptName" label="部门名称" min-width="150" />
        <el-table-column prop="leader" label="负责人" width="100" />
        <el-table-column prop="phone" label="联系电话" width="130" />
        <el-table-column prop="email" label="邮箱" min-width="150" />
        <el-table-column prop="status" label="状态" width="80"><template #default="{row}"><el-tag :type="row.status===1?'success':'danger'">{{ row.status===1?'启用':'禁用' }}</el-tag></template></el-table-column>
        <el-table-column label="操作" width="200"><template #default="{row}"><el-button size="small" @click="handleEdit(row)">编辑</el-button><el-popconfirm title="确定删除?" @confirm="handleDel(row.id)"><template #reference><el-button size="small" type="danger">删除</el-button></template></el-popconfirm></template></el-table-column>
      </el-table>
    </el-card>
    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="500px" @close="resetForm">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="部门名称" prop="deptName"><el-input v-model="form.deptName" /></el-form-item>
        <el-form-item label="上级部门"><el-select v-model="form.parentId" placeholder="无（一级部门）" clearable><el-option v-for="c in tableData" :key="c.id" :label="c.deptName" :value="c.id" /></el-select></el-form-item>
        <el-form-item label="负责人"><el-input v-model="form.leader" /></el-form-item>
        <el-form-item label="电话"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="邮箱"><el-input v-model="form.email" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="form.orderNum" :min="0" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="submitForm">确定</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getDeptList, getDept, addDept, updateDept, deleteDept } from '@/api/permission'
import { ElMessage } from 'element-plus'
const loading = ref(false), tableData = ref([]), dialogVisible = ref(false), dialogTitle = ref('新增部门'), formRef = ref(null), isEdit = ref(false)
const form = reactive({ id: null, deptName: '', parentId: null, leader: '', phone: '', email: '', orderNum: 0, status: 1 })
const rules = { deptName: [{ required: true }] }
const fetchData = async () => { loading.value = true; try { const res = await getDeptList(); tableData.value = res.data.records || res.data || [] } catch {} loading.value = false }
const handleAdd = () => { isEdit.value = false; dialogTitle.value = '新增部门'; dialogVisible.value = true }
const handleEdit = async row => { isEdit.value = true; dialogTitle.value = '编辑部门'; Object.assign(form, row); dialogVisible.value = true }
const handleDel = async id => { try { await deleteDept(id); ElMessage.success('删除成功'); fetchData() } catch (e) { ElMessage.error('删除失败') } }
const resetForm = () => { formRef.value?.resetFields(); Object.assign(form, { id: null, deptName: '', parentId: null, leader: '', phone: '', email: '', orderNum: 0, status: 1 }) }
const submitForm = async () => {
  const valid = await formRef.value.validate().catch(() => false); if (!valid) return
  try {
    isEdit.value ? await updateDept(form) : await addDept(form)
    ElMessage.success(isEdit.value ? '更新成功' : '新增成功'); dialogVisible.value = false; fetchData()
  } catch (e) { ElMessage.error(isEdit.value ? '更新失败' : '新增失败') }
}
onMounted(fetchData)
</script>
<style scoped>.page-container { padding: 0; } .page-header { display: flex; justify-content: space-between; align-items: center; }</style>