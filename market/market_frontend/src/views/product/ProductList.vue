<template>
  <div class="page-container">
    <el-card>
      <template #header><div class="page-header"><span>商品列表</span><el-button type="primary" @click="handleAdd">新增商品</el-button></div></template>
      <el-form :inline="true" :model="query" class="search-form">
        <el-form-item label="商品名称"><el-input v-model="query.keyword" placeholder="输入商品名称" clearable /></el-form-item>
        <el-form-item label="分类"><el-select v-model="query.categoryId" placeholder="全部分类" clearable><el-option v-for="c in categories" :key="c.id" :label="c.categoryName" :value="c.id" /></el-select></el-form-item>
        <el-form-item label="状态"><el-select v-model="query.status" placeholder="全部" clearable><el-option label="上架" :value="1" /><el-option label="下架" :value="0" /></el-select></el-form-item>
        <el-form-item><el-button type="primary" @click="search">搜索</el-button><el-button @click="reset">重置</el-button></el-form-item>
      </el-form>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="productName" label="商品名称" min-width="160" />
        <el-table-column prop="categoryName" label="分类" width="100" />
        <el-table-column prop="price" label="价格" width="100"><template #default="{row}">¥{{ row.price }}</template></el-table-column>
        <el-table-column prop="stock" label="库存" width="80" />
        <el-table-column prop="sales" label="销量" width="80" />
        <el-table-column prop="status" label="状态" width="80"><template #default="{row}"><el-tag :type="row.status===1?'success':'info'">{{ row.status===1?'上架':'下架' }}</el-tag></template></el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{row}">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" :type="row.status===1?'warning':'success'" @click="toggleStatus(row)">{{ row.status===1?'下架':'上架' }}</el-button>
            <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)"><template #reference><el-button size="small" type="danger">删除</el-button></template></el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top:20px; justify-content:flex-end" background layout="total, prev, pager, next" :total="total" v-model:current-page="query.page" @current-change="fetchData" />
    </el-card>
    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="600px" @close="resetForm">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="商品名称" prop="productName"><el-input v-model="form.productName" /></el-form-item>
        <el-form-item label="商品编码" prop="productCode"><el-input v-model="form.productCode" /></el-form-item>
        <el-form-item label="分类" prop="categoryId"><el-select v-model="form.categoryId" placeholder="选择分类"><el-option v-for="c in categories" :key="c.id" :label="c.categoryName" :value="c.id" /></el-select></el-form-item>
        <el-form-item label="品牌"><el-input v-model="form.brand" /></el-form-item>
        <el-form-item label="价格" prop="price"><el-input-number v-model="form.price" :min="0" :precision="2" /></el-form-item>
        <el-form-item label="成本价"><el-input-number v-model="form.costPrice" :min="0" :precision="2" /></el-form-item>
        <el-form-item label="库存" prop="stock"><el-input-number v-model="form.stock" :min="0" /></el-form-item>
        <el-form-item label="单位"><el-input v-model="form.unit" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="submitForm">确定</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getProductList, getProduct, addProduct, updateProduct, deleteProduct, updateProductStatus, getCategoryList } from '@/api/product'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const categories = ref([])
const query = reactive({ page: 1, keyword: '', categoryId: null, status: null })
const dialogVisible = ref(false)
const dialogTitle = ref('新增商品')
const formRef = ref(null)
const isEdit = ref(false)
const form = reactive({ id: null, productName: '', productCode: '', categoryId: null, brand: '', price: 0, costPrice: 0, stock: 0, unit: '件', description: '' })
const rules = { productName: [{ required: true, message: '请输入商品名称' }], categoryId: [{ required: true, message: '请选择分类' }] }

const fetchData = async () => {
  loading.value = true
  try { const res = await getProductList(query); tableData.value = res.data.list || res.data.records || res.data || []; total.value = res.data.total || tableData.value.length } catch {}
  loading.value = false
}
const search = () => { query.page = 1; fetchData() }
const reset = () => { query.keyword = ''; query.categoryId = null; query.status = null; search() }
const handleAdd = () => { isEdit.value = false; dialogTitle.value = '新增商品'; dialogVisible.value = true }
const handleEdit = async row => {
  isEdit.value = true; dialogTitle.value = '编辑商品'
  try {
    const res = await getProduct(row.id)
    Object.assign(form, res.data || row)
    dialogVisible.value = true
  } catch (e) {
    ElMessage.error('获取商品信息失败')
  }
}
const toggleStatus = async row => {
  try {
    const newStatus = row.status === 1 ? 0 : 1
    await updateProductStatus(row.id, newStatus)
    ElMessage.success('状态已更新'); fetchData()
  } catch (e) {
    ElMessage.error('操作失败')
  }
}
const handleDelete = async id => {
  try {
    await deleteProduct(id)
    ElMessage.success('删除成功'); fetchData()
  } catch (e) {
    ElMessage.error('删除失败')
  }
}
const resetForm = () => { formRef.value?.resetFields(); Object.assign(form, { id: null, productName: '', productCode: '', categoryId: null, brand: '', price: 0, costPrice: 0, stock: 0, unit: '件', description: '' }) }
const submitForm = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  const data = { ...form, categoryName: categories.value.find(c => c.id === form.categoryId)?.categoryName || '' }
  try {
    if (isEdit.value) await updateProduct(data)
    else await addProduct(data)
    ElMessage.success(isEdit.value ? '更新成功' : '新增成功')
    dialogVisible.value = false
    fetchData()
  } catch (e) {
    ElMessage.error(isEdit.value ? '更新失败' : '新增失败')
  }
}
onMounted(async () => {
  fetchData()
  try { const res = await getCategoryList(); categories.value = res.data || [] } catch {}
})
</script>

<style scoped>
.page-container { padding: 0; }
.page-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 16px; }
</style>