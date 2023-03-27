import { ElLoading, ElMessage, ElMessageBox, ElNotification } from 'element-plus';
import type { LoadingInstance } from 'element-plus/lib/components/loading/src/loading';

let loadingInstance: LoadingInstance;

/**
 * 提示消息
 */
export function msgInfo(content: string) {
  ElMessage.info(content);
}

/**
 * 错误消息
 */
export function msgError(content: string) {
  ElMessage.error(content);
}

/**
 * 成功消息
 */
export function msgSuccess(content: string) {
  ElMessage.success(content);
}

/**
 * 警告消息
 */
export function msgWarning(content: string) {
  ElMessage.warning(content);
}

/**
 * 错误提示
 */
export function notifyError(message: string) {
  ElNotification({
    title: '错误',
    message,
    type: 'error'
  });
}

/**
 * 成功提示
 */
export function notifySuccess(message: string) {
  ElNotification({
    title: '成功',
    message,
    type: 'success'
  });
}

/**
 * 确认消息
 */
export function messageConfirm(content: string) {
  return ElMessageBox.confirm(content, '系统提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    center: true,
    type: 'warning'
  });
}

/**
 * 打开遮罩层
 */
export function loading(content: string) {
  loadingInstance = ElLoading.service({
    lock: true,
    text: content,
    background: 'rgba(0, 0, 0, 0.7)'
  });
}

/**
 * 关闭遮罩层
 */
export function closeLoading() {
  loadingInstance.close();
}
