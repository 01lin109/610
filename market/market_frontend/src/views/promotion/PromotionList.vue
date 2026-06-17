<template>
  <div class="page-container">
    <el-card>
      <template #header><div class="page-header"><span>促销活动</span><el-button type="primary" @click="handleAdd">新增活动</el-button></div></template>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="promotionName" label="活动名称" min-width="150" />
        <el-table-column prop="promotionType" label="类型" width="100"><template #default="{row}"><el-tag>{{ row.promotionType===1?'满减':row.promotionType===2?'限时折扣':row.promotionType===3?'秒杀':'拼团' }}</el-tag></template></el-table-column>
        <el-table-column prop="discountValue" label="折扣值" width="80" />
        <el-table-column prop="startTime" label="开始时间" width="160" />
        <el-table-column prop="endTime" label="结束时间" width="160" />
        <el-table-column prop="status" label="状态" width="80"><template #default="{row}"><el-tag :type="row.status===1?'success':row.status===2?'info':'warning'">{{ row.status===0?'未开始':row.status===1?'进行中':'已结束' }}</el-tag></template></el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{row}"><el-button size="small" @click="handleEdit(row)">编辑</el-button><el-popconfirm title="确定删除?" @confirm="handleDel(row.id)"><template #reference><el-button size="small" type="danger">删除</el-button></template></el-popconfirm></template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="500px" @close="resetForm">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="名称" prop="promotionName"><el-input v-model="form.promotionName" /></el-form-item>
        <el-form-item label="类型"><el-select v-model="form.promotionType"><el-option label="满减" :value="1" /><el-option label="限时折扣" :value="2" /><el-option label="秒杀" :value="3" /><el-option label="拼团" :value="4" /></el-select></el-form-item>
        <el-form-item label="折扣值"><el-input-number v-model="form.discountValue" :min="0" :precision="2" /></el-form-item>
        <el-form-item label="活动时间"><el-date-picker v-model="dateRange" type="daterange" range-separator="至" start-placeholder="开始" end-placeholder="结束" value-format="YYYY-MM-DD HH:mm:ss" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.remark" type="textarea" :rows="2" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="submitForm">确定</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getPromotionList, getPromotion, addPromotion, updatePromotion, deletePromotion } from '@/api/promotion'
import { ElMessage } from 'element-plus'
const loading = ref(false), tableData = ref([]), dialogVisible = ref(false), dialogTitle = ref('新增活动'), formRef = ref(null), isEdit = ref(false), dateRange = ref([])
const form = reactive({ id: null, promotionName: '', promotionType: 2, discountValue: 0, startTime: '', endTime: '', remark: '' })
const rules = { promotionName: [{ required: true, message: '请输入名称' }] }
const fetchData = async () => {
  loading.value = true
  try { const res = await getPromotionList(); tableData.value = res.data.records || res.data || [] } catch {}
  loading.value = false
}
const handleAdd = () => { isEdit.value = false; dialogTitle.value = '新增活动'; dialogVisible.value = true }
const handleEdit = async row => { isEdit.value = true; dialogTitle.value = '编辑活动'; Object.assign(form, row); if (row.startTime) dateRange.value = [row.startTime, row.endTime]; dialogVisible.value = true }
const handleDel = async id => { try { await deletePromotion(id); ElMessage.success('删除成功'); fetchData() } catch (e) { ElMessage.error('删除失败') } }
const resetForm = () => { formRef.value?.resetFields(); Object.assign(form, { id: null, promotionName: '', promotionType: 2, discountValue: 0, startTime: '', endTime: '', remark: '' }); dateRange.value = [] }
const submitForm = async () => {
  const valid = await formRef.value.validate().catch(() => false); if (!valid) return
  if (dateRange.value?.length === 2) { form.startTime = dateRange.value[0]; form.endTime = dateRange.value[1] }
  try {
    if (isEdit.value) await updatePromotion(form); else await addPromotion(form)
    ElMessage.success(isEdit.value ? '更新成功' : '新增成功'); dialogVisible.value = false; fetchData()
  } catch (e) { ElMessage.error(isEdit.value ? '更新失败' : '新增失败') }
}
onMounted(fetchData)
</script>
<style scoped>.page-container { padding: 0; } .page-header { display: flex; justify-content: space-between; align-items: center; }</style>