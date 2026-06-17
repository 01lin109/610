<template>
  <div class="page-container">
    <el-card>
      <template #header><div class="page-header"><span>文章管理</span><el-button type="primary" @click="handleAdd">新增文章</el-button></div></template>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="title" label="标题" min-width="200" />
        <el-table-column prop="categoryName" label="分类" width="100" />
        <el-table-column prop="author" label="作者" width="100" />
        <el-table-column prop="viewCount" label="浏览量" width="80" />
        <el-table-column prop="isTop" label="置顶" width="80"><template #default="{row}"><el-tag :type="row.isTop===1?'danger':''">{{ row.isTop===1?'置顶':'否' }}</el-tag></template></el-table-column>
        <el-table-column prop="status" label="状态" width="80"><template #default="{row}"><el-tag :type="row.status===1?'success':'info'">{{ row.status===1?'已发布':'草稿' }}</el-tag></template></el-table-column>
        <el-table-column label="操作" width="200"><template #default="{row}"><el-button size="small" @click="handleEdit(row)">编辑</el-button><el-popconfirm title="确定删除?" @confirm="handleDel(row.id)"><template #reference><el-button size="small" type="danger">删除</el-button></template></el-popconfirm></template></el-table-column>
      </el-table>
    </el-card>
    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="700px" @close="resetForm">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="标题" prop="title"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="分类"><el-input v-model="form.categoryName" /></el-form-item>
        <el-form-item label="摘要"><el-input v-model="form.summary" type="textarea" :rows="2" /></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" :rows="8" /></el-form-item>
        <el-form-item label="作者"><el-input v-model="form.author" /></el-form-item>
        <el-form-item label="置顶"><el-switch v-model="form.isTop" :active-value="1" :inactive-value="0" /></el-form-item>
        <el-form-item label="状态"><el-switch v-model="form.status" active-text="发布" inactive-text="草稿" :active-value="1" :inactive-value="0" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="submitForm">确定</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getArticleList, getArticle, addArticle, updateArticle, deleteArticle } from '@/api/content'
import { ElMessage } from 'element-plus'
const loading = ref(false), tableData = ref([]), dialogVisible = ref(false), dialogTitle = ref('新增文章'), formRef = ref(null), isEdit = ref(false)
const form = reactive({ id: null, title: '', categoryName: '', summary: '', content: '', author: '', isTop: 0, status: 1 })
const rules = { title: [{ required: true }] }
const fetchData = async () => { loading.value = true; try { const res = await getArticleList(); tableData.value = res.data.records || res.data || [] } catch {} loading.value = false }
const handleAdd = () => { isEdit.value = false; dialogTitle.value = '新增文章'; dialogVisible.value = true }
const handleEdit = async row => { isEdit.value = true; dialogTitle.value = '编辑文章'; Object.assign(form, row); dialogVisible.value = true }
const handleDel = async id => { try { await deleteArticle(id); ElMessage.success('删除成功'); fetchData() } catch (e) { ElMessage.error('删除失败') } }
const resetForm = () => { formRef.value?.resetFields(); Object.assign(form, { id: null, title: '', categoryName: '', summary: '', content: '', author: '', isTop: 0, status: 1 }) }
const submitForm = async () => {
  const valid = await formRef.value.validate().catch(() => false); if (!valid) return
  try {
    isEdit.value ? await updateArticle(form) : await addArticle(form)
    ElMessage.success(isEdit.value ? '更新成功' : '新增成功'); dialogVisible.value = false; fetchData()
  } catch (e) { ElMessage.error(isEdit.value ? '更新失败' : '新增失败') }
}
onMounted(fetchData)
</script>
<style scoped>.page-container { padding: 0; } .page-header { display: flex; justify-content: space-between; align-items: center; }</style>