<template>
  <div class="page-container">
    <el-card>
      <template #header><div class="page-header"><span>菜单管理</span><el-button type="primary" @click="handleAdd">新增菜单</el-button></div></template>
      <el-table :data="tableData" border stripe v-loading="loading" row-key="id" default-expand-all>
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="menuName" label="名称" min-width="150" />
        <el-table-column prop="path" label="路由" width="180" />
        <el-table-column prop="menuType" label="类型" width="80"><template #default="{row}"><el-tag>{{ row.menuType==='M'?'目录':row.menuType==='C'?'菜单':'按钮' }}</el-tag></template></el-table-column>
        <el-table-column prop="orderNum" label="排序" width="60" />
        <el-table-column prop="status" label="状态" width="80"><template #default="{row}"><el-tag :type="row.status===1?'success':'danger'">{{ row.status===1?'启用':'禁用' }}</el-tag></template></el-table-column>
        <el-table-column label="操作" width="200"><template #default="{row}"><el-button size="small" @click="handleEdit(row)">编辑</el-button><el-popconfirm title="确定删除?" @confirm="handleDel(row.id)"><template #reference><el-button size="small" type="danger">删除</el-button></template></el-popconfirm></template></el-table-column>
      </el-table>
    </el-card>
    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="500px" @close="resetForm">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="名称" prop="menuName"><el-input v-model="form.menuName" /></el-form-item>
        <el-form-item label="上级"><el-select v-model="form.parentId" placeholder="无（一级菜单）" clearable><el-option v-for="c in tableData" :key="c.id" :label="c.menuName" :value="c.id" /></el-select></el-form-item>
        <el-form-item label="类型"><el-select v-model="form.menuType"><el-option label="目录" value="M" /><el-option label="菜单" value="C" /><el-option label="按钮" value="F" /></el-select></el-form-item>
        <el-form-item label="路由"><el-input v-model="form.path" /></el-form-item>
        <el-form-item label="组件"><el-input v-model="form.component" /></el-form-item>
        <el-form-item label="图标"><el-input v-model="form.icon" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="form.orderNum" :min="0" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="submitForm">确定</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getMenuList, getMenu, addMenu, updateMenu, deleteMenu } from '@/api/permission'
import { ElMessage } from 'element-plus'
const loading = ref(false), tableData = ref([]), dialogVisible = ref(false), dialogTitle = ref('新增菜单'), formRef = ref(null), isEdit = ref(false)
const form = reactive({ id: null, menuName: '', parentId: null, orderNum: 0, path: '', component: '', menuType: 'C', icon: '', status: 1 })
const rules = { menuName: [{ required: true }], path: [{ required: true }] }
const fetchData = async () => { loading.value = true; try { const res = await getMenuList(); tableData.value = res.data.records || res.data || [] } catch {} loading.value = false }
const handleAdd = () => { isEdit.value = false; dialogTitle.value = '新增菜单'; dialogVisible.value = true }
const handleEdit = async row => { isEdit.value = true; dialogTitle.value = '编辑菜单'; Object.assign(form, row); dialogVisible.value = true }
const handleDel = async id => { try { await deleteMenu(id); ElMessage.success('删除成功'); fetchData() } catch (e) { ElMessage.error('删除失败') } }
const resetForm = () => { formRef.value?.resetFields(); Object.assign(form, { id: null, menuName: '', parentId: null, orderNum: 0, path: '', component: '', menuType: 'C', icon: '', status: 1 }) }
const submitForm = async () => {
  const valid = await formRef.value.validate().catch(() => false); if (!valid) return
  try {
    isEdit.value ? await updateMenu(form) : await addMenu(form)
    ElMessage.success(isEdit.value ? '更新成功' : '新增成功'); dialogVisible.value = false; fetchData()
  } catch (e) { ElMessage.error(isEdit.value ? '更新失败' : '新增失败') }
}
onMounted(fetchData)
</script>
<style scoped>.page-container { padding: 0; } .page-header { display: flex; justify-content: space-between; align-items: center; }</style>