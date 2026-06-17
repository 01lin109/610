<template>
  <div class="page-container">
    <el-card>
      <template #header><div class="page-header"><span>优惠券管理</span><el-button type="primary" @click="handleAdd">新增优惠券</el-button></div></template>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="couponName" label="名称" min-width="150" />
        <el-table-column prop="couponType" label="类型" width="100"><template #default="{row}"><el-tag>{{ row.couponType===1?'满减券':row.couponType===2?'折扣券':'代金券' }}</el-tag></template></el-table-column>
        <el-table-column prop="discountValue" label="优惠值" width="100" />
        <el-table-column prop="minAmount" label="门槛" width="100"><template #default="{row}">¥{{ row.minAmount }}</template></el-table-column>
        <el-table-column prop="receivedCount" label="已领取/总量" width="120"><template #default="{row}">{{ row.receivedCount }}/{{ row.totalCount }}</template></el-table-column>
        <el-table-column prop="status" label="状态" width="80"><template #default="{row}"><el-tag :type="row.status===1?'success':'info'">{{ row.status===1?'启用':'禁用' }}</el-tag></template></el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{row}"><el-button size="small" @click="handleEdit(row)">编辑</el-button><el-popconfirm title="确定删除?" @confirm="handleDel(row.id)"><template #reference><el-button size="small" type="danger">删除</el-button></template></el-popconfirm></template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="500px" @close="resetForm">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="名称" prop="couponName"><el-input v-model="form.couponName" /></el-form-item>
        <el-form-item label="类型"><el-select v-model="form.couponType"><el-option label="满减券" :value="1" /><el-option label="折扣券" :value="2" /><el-option label="代金券" :value="3" /></el-select></el-form-item>
        <el-form-item label="优惠值" prop="discountValue"><el-input-number v-model="form.discountValue" :min="0" :precision="2" /></el-form-item>
        <el-form-item label="门槛金额"><el-input-number v-model="form.minAmount" :min="0" :precision="2" /></el-form-item>
        <el-form-item label="发放总量"><el-input-number v-model="form.totalCount" :min="0" /></el-form-item>
        <el-form-item label="有效日期"><el-date-picker v-model="dateRange" type="daterange" range-separator="至" start-placeholder="开始" end-placeholder="结束" value-format="YYYY-MM-DD HH:mm:ss" /></el-form-item>
        <el-form-item label="状态"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="submitForm">确定</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getCouponList, getCoupon, addCoupon, updateCoupon, deleteCoupon } from '@/api/promotion'
import { ElMessage } from 'element-plus'
const loading = ref(false), tableData = ref([]), dialogVisible = ref(false), dialogTitle = ref('新增优惠券'), formRef = ref(null), isEdit = ref(false), dateRange = ref([])
const form = reactive({ id: null, couponName: '', couponType: 1, discountValue: 0, minAmount: 0, totalCount: 100, startTime: '', endTime: '', status: 1 })
const rules = { couponName: [{ required: true, message: '请输入名称' }], discountValue: [{ required: true, message: '请输入优惠值' }] }
const fetchData = async () => {
  loading.value = true
  try { const res = await getCouponList(); tableData.value = res.data.records || res.data || [] } catch {}
  loading.value = false
}
const handleAdd = () => { isEdit.value = false; dialogTitle.value = '新增优惠券'; dialogVisible.value = true }
const handleEdit = async row => { isEdit.value = true; dialogTitle.value = '编辑优惠券'; Object.assign(form, row); if (row.startTime) dateRange.value = [row.startTime, row.endTime]; dialogVisible.value = true }
const handleDel = async id => { try { await deleteCoupon(id); ElMessage.success('删除成功'); fetchData() } catch (e) { ElMessage.error('删除失败') } }
const resetForm = () => { formRef.value?.resetFields(); Object.assign(form, { id: null, couponName: '', couponType: 1, discountValue: 0, minAmount: 0, totalCount: 100, startTime: '', endTime: '', status: 1 }); dateRange.value = [] }
const submitForm = async () => {
  const valid = await formRef.value.validate().catch(() => false); if (!valid) return
  if (dateRange.value?.length === 2) { form.startTime = dateRange.value[0]; form.endTime = dateRange.value[1] }
  try {
    if (isEdit.value) await updateCoupon(form); else await addCoupon(form)
    ElMessage.success(isEdit.value ? '更新成功' : '新增成功'); dialogVisible.value = false; fetchData()
  } catch (e) { ElMessage.error(isEdit.value ? '更新失败' : '新增失败') }
}
onMounted(fetchData)
</script>
<style scoped>.page-container { padding: 0; } .page-header { display: flex; justify-content: space-between; align-items: center; }</style>