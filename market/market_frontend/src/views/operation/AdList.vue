<template>
  <div class="page-container">
    <el-card>
      <template #header><div class="page-header"><span>广告管理</span><el-button type="primary" @click="handleAdd">新增广告</el-button></div></template>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="adName" label="名称" min-width="150" />
        <el-table-column prop="adType" label="类型" width="80"><template #default="{row}"><el-tag>{{ row.adType===1?'图片':row.adType===2?'链接':'视频' }}</el-tag></template></el-table-column>
        <el-table-column prop="adPosition" label="位置" width="80"><template #default="{row}">{{ row.adPosition===1?'首页':row.adPosition===2?'商品页':'详情页' }}</template></el-table-column>
        <el-table-column prop="clickCount" label="点击量" width="80" />
        <el-table-column prop="status" label="状态" width="80"><template #default="{row}"><el-tag :type="row.status===1?'success':'info'">{{ row.status===1?'启用':'禁用' }}</el-tag></template></el-table-column>
        <el-table-column label="操作" width="200"><template #default="{row}"><el-button size="small" @click="handleEdit(row)">编辑</el-button><el-popconfirm title="确定删除?" @confirm="handleDel(row.id)"><template #reference><el-button size="small" type="danger">删除</el-button></template></el-popconfirm></template></el-table-column>
      </el-table>
    </el-card>
    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="500px" @close="resetForm">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="名称" prop="adName"><el-input v-model="form.adName" /></el-form-item>
        <el-form-item label="类型"><el-select v-model="form.adType"><el-option label="图片" :value="1" /><el-option label="链接" :value="2" /><el-option label="视频" :value="3" /></el-select></el-form-item>
        <el-form-item label="位置"><el-select v-model="form.adPosition"><el-option label="首页" :value="1" /><el-option label="商品页" :value="2" /><el-option label="详情页" :value="3" /></el-select></el-form-item>
        <el-form-item label="图片URL"><el-input v-model="form.imageUrl" /></el-form-item>
        <el-form-item label="链接URL"><el-input v-model="form.linkUrl" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="form.sort" :min="0" /></el-form-item>
        <el-form-item label="状态"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="submitForm">确定</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getAdList, getAd, addAd, updateAd, deleteAd } from '@/api/operation'
import { ElMessage } from 'element-plus'
const loading = ref(false), tableData = ref([]), dialogVisible = ref(false), dialogTitle = ref('新增广告'), formRef = ref(null), isEdit = ref(false)
const form = reactive({ id: null, adName: '', adType: 1, adPosition: 1, imageUrl: '', linkUrl: '', sort: 0, status: 1 })
const rules = { adName: [{ required: true }] }
const fetchData = async () => { loading.value = true; try { const res = await getAdList(); tableData.value = res.data.records || res.data || [] } catch {} loading.value = false }
const handleAdd = () => { isEdit.value = false; dialogTitle.value = '新增广告'; dialogVisible.value = true }
const handleEdit = async row => { isEdit.value = true; dialogTitle.value = '编辑广告'; Object.assign(form, row); dialogVisible.value = true }
const handleDel = async id => { try { await deleteAd(id); ElMessage.success('删除成功'); fetchData() } catch (e) { ElMessage.error('删除失败') } }
const resetForm = () => { formRef.value?.resetFields(); Object.assign(form, { id: null, adName: '', adType: 1, adPosition: 1, imageUrl: '', linkUrl: '', sort: 0, status: 1 }) }
const submitForm = async () => {
  const valid = await formRef.value.validate().catch(() => false); if (!valid) return
  try {
    isEdit.value ? await updateAd(form) : await addAd(form)
    ElMessage.success(isEdit.value ? '更新成功' : '新增成功'); dialogVisible.value = false; fetchData()
  } catch (e) { ElMessage.error(isEdit.value ? '更新失败' : '新增失败') }
}
onMounted(fetchData)
</script>
<style scoped>.page-container { padding: 0; } .page-header { display: flex; justify-content: space-between; align-items: center; }</style>