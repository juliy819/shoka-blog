import { ElLoading, ElMessageBox, ElNotification } from 'element-plus';
import type {
  LoadingInstance
} from 'element-plus/lib/components/loading/src/loading';

let loadingInstance: LoadingInstance;

export function notifySuccess(message: string) {
  ElNotification({
    title: '成功',
    message,
    type: 'success'
  });
}

export function messageConfirm(content: string) {
  return ElMessageBox.confirm(content, '系统提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    center: true,
    type: 'warning'
  });
}

// 打开遮罩层
export function loading(content: string) {
  loadingInstance = ElLoading.service({
    lock: true,
    text: content,
    background: 'rgba(0, 0, 0, 0.7)'
  });
}

// 关闭遮罩层
export function closeLoading() {
  loadingInstance.close();
}
