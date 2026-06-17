<template>
  <div class="page-container">
    <el-card>
      <template #header><div class="page-header"><span>商品分类</span><el-button type="primary" @click="handleAdd">新增分类</el-button></div></template>
      <el-table :data="tableData" border stripe v-loading="loading" row-key="id" default-expand-all>
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="categoryName" label="分类名称" min-width="150" />
        <el-table-column prop="level" label="层级" width="60" />
        <el-table-column prop="sort" label="排序" width="60" />
        <el-table-column prop="status" label="状态" width="80"><template #default="{row}"><el-tag :type="row.status===1?'success':'info'">{{ row.status===1?'启用':'禁用' }}</el-tag></template></el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{row}">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)"><template #reference><el-button size="small" type="danger">删除</el-button></template></el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="500px" @close="resetForm">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="分类名称" prop="categoryName"><el-input v-model="form.categoryName" /></el-form-item>
        <el-form-item label="上级分类"><el-select v-model="form.parentId" placeholder="无（一级分类）" clearable><el-option v-for="c in tableData" :key="c.id" :label="c.categoryName" :value="c.id" /></el-select></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="form.sort" :min="0" /></el-form-item>
        <el-form-item label="状态"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="submitForm">确定</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getCategoryList, getCategory, addCategory, updateCategory, deleteCategory } from '@/api/product'
import { ElMessage } from 'element-plus'

const loading = ref(false), tableData = ref([]), dialogVisible = ref(false), dialogTitle = ref('新增分类'), formRef = ref(null), isEdit = ref(false)
const form = reactive({ id: null, categoryName: '', parentId: null, sort: 0, status: 1 })
const rules = { categoryName: [{ required: true, message: '请输入分类名称' }] }

const fetchData = async () => {
  loading.value = true
  try { const res = await getCategoryList(); tableData.value = res.data || [] } catch {}
  loading.value = false
}
const handleAdd = () => { isEdit.value = false; dialogTitle.value = '新增分类'; dialogVisible.value = true }
const handleEdit = async row => { isEdit.value = true; dialogTitle.value = '编辑分类'; Object.assign(form, row); dialogVisible.value = true }
const handleDelete = async id => { try { await deleteCategory(id); ElMessage.success('删除成功'); fetchData() } catch (e) { ElMessage.error('删除失败') } }
const resetForm = () => { formRef.value?.resetFields(); Object.assign(form, { id: null, categoryName: '', parentId: null, sort: 0, status: 1 }) }
const submitForm = async () => {
  const valid = await formRef.value.validate().catch(() => false); if (!valid) return
  try {
    if (isEdit.value) await updateCategory(form); else await addCategory(form)
    ElMessage.success(isEdit.value ? '更新成功' : '新增成功'); dialogVisible.value = false; fetchData()
  } catch (e) { ElMessage.error(isEdit.value ? '更新失败' : '新增失败') }
}
onMounted(fetchData)
</script>
<style scoped>.page-container { padding: 0; } .page-header { display: flex; justify-content: space-between; align-items: center; }</style>