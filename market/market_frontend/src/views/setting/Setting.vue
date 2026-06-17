<template>
  <div class="page-container">
    <el-tabs v-model="activeTab" type="border-card">
      <el-tab-pane label="基本设置" name="basic">
        <el-form :model="basicForm" label-width="120px">
          <el-form-item label="站点名称"><el-input v-model="basicForm.site_name" /></el-form-item>
          <el-form-item label="版权信息"><el-input v-model="basicForm.copyright" /></el-form-item>
          <el-form-item label="订单超时(分钟)"><el-input-number v-model="basicForm.order_timeout" :min="1" /></el-form-item>
          <el-form-item><el-button type="primary" @click="saveBasic">保存设置</el-button></el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="物流设置" name="logistics">
        <el-form :model="logisticsForm" label-width="120px">
          <el-form-item label="默认运费"><el-input-number v-model="logisticsForm.default_freight" :min="0" :precision="2" /></el-form-item>
          <el-form-item label="包邮门槛"><el-input-number v-model="logisticsForm.free_freight_amount" :min="0" :precision="2" /></el-form-item>
          <el-form-item><el-button type="primary" @click="saveLogistics">保存设置</el-button></el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="支付设置" name="pay">
        <el-form :model="payForm" label-width="120px">
          <el-form-item label="微信支付"><el-switch v-model="payForm.wechat_enabled" /></el-form-item>
          <el-form-item label="支付宝"><el-switch v-model="payForm.ali_enabled" /></el-form-item>
          <el-form-item><el-button type="primary" @click="savePay">保存设置</el-button></el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="短信设置" name="sms">
        <el-form :model="smsForm" label-width="120px">
          <el-form-item label="服务商"><el-select v-model="smsForm.sms_provider"><el-option label="阿里云" value="aliyun" /><el-option label="腾讯云" value="tencent" /></el-select></el-form-item>
          <el-form-item label="短信签名"><el-input v-model="smsForm.sms_sign" /></el-form-item>
          <el-form-item><el-button type="primary" @click="saveSms">保存设置</el-button></el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getConfigByType, updateConfigBatch } from '@/api/setting'
import { ElMessage } from 'element-plus'
const activeTab = ref('basic')
const basicForm = reactive({ site_name: '', copyright: '', order_timeout: 30 })
const logisticsForm = reactive({ default_freight: 10, free_freight_amount: 99 })
const payForm = reactive({ wechat_enabled: true, ali_enabled: true })
const smsForm = reactive({ sms_provider: 'aliyun', sms_sign: '' })

const loadConfig = async type => {
  try {
    const res = await getConfigByType(type)
    const list = res.data || []
    list.forEach(c => {
      if (type === 'basic' && basicForm.hasOwnProperty(c.configKey)) basicForm[c.configKey] = c.configValue
      if (type === 'logistics' && logisticsForm.hasOwnProperty(c.configKey)) logisticsForm[c.configKey] = parseFloat(c.configValue) || c.configValue
      if (type === 'pay') payForm.wechat_enabled = list.find(i => i.configKey === 'wechat_pay')?.configValue?.includes('true') || true
      if (type === 'sms' && smsForm.hasOwnProperty(c.configKey)) smsForm[c.configKey] = c.configValue
    })
  } catch {}
}
const buildBatch = (type, data) => Object.keys(data).map(k => ({ configKey: k, configValue: String(data[k]), configType: type }))
const saveBasic = async () => { try { await updateConfigBatch(buildBatch('basic', basicForm)); ElMessage.success('保存成功') } catch (e) { ElMessage.error('保存失败') } }
const saveLogistics = async () => { try { await updateConfigBatch(buildBatch('logistics', logisticsForm)); ElMessage.success('保存成功') } catch (e) { ElMessage.error('保存失败') } }
const savePay = async () => { try { await updateConfigBatch([{ configKey: 'wechat_pay', configValue: JSON.stringify({ enabled: payForm.wechat_enabled }), configType: 'pay' }]); ElMessage.success('保存成功') } catch (e) { ElMessage.error('保存失败') } }
const saveSms = async () => { try { await updateConfigBatch(buildBatch('sms', smsForm)); ElMessage.success('保存成功') } catch (e) { ElMessage.error('保存失败') } }

onMounted(() => { loadConfig('basic'); loadConfig('logistics'); loadConfig('pay'); loadConfig('sms') })
</script>
<style scoped>.page-container { padding: 0; }</style>