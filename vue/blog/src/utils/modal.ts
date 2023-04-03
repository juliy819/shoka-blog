import type { MessageBoxData } from 'element-plus';
import { ElLoading, ElMessage, ElMessageBox, ElNotification } from 'element-plus';
import type { LoadingInstance } from 'element-plus/lib/components/loading/src/loading';

let loadingInstance: LoadingInstance;

interface Modal {
  /**
   * 提示消息
   */
  msgInfo(message: string): void;

  /**
   * 错误消息
   */
  msgError(message: string): void;

  /**
   * 成功消息
   */
  msgSuccess(message: string): void;

  /**
   * 警告消息
   */
  msgWarning(message: string): void;

  /**
   * 信息提示
   */
  notifyInfo(message: string): void;

  /**
   * 错误提示
   */
  notifyError(message: string): void;

  /**
   * 成功提示
   */
  notifySuccess(message: string): void;

  /**
   * 警告提示
   */
  notifyWarning(message: string): void;

  /**
   * 确认消息
   */
  messageConfirm(message: string): Promise<MessageBoxData>;

  /**
   * 打开遮罩层
   */
  loading(message: string): void;

  /**
   * 关闭遮罩层
   */
  closeLoading(): void;
}

export const modal: Modal = {

  msgInfo: (message) => {
    ElMessage.info(message);
  },

  msgError: (message) => {
    ElMessage.error(message);
  },

  msgSuccess: (message) => {
    ElMessage.success(message);
  },

  msgWarning: (message) => {
    ElMessage.warning(message);
  },

  notifyInfo: (message) => {
    ElNotification({
      title: '提示',
      type: 'info',
      message
    });
  },

  notifyError: (message) => {
    ElNotification({
      title: '错误',
      type: 'error',
      message
    });
  },

  notifySuccess: (message) => {
    ElNotification({
      title: '成功',
      type: 'success',
      message
    });
  },

  notifyWarning: (message) => {
    ElNotification({
      title: '警告',
      type: 'warning',
      message
    });
  },

  messageConfirm: (message) => {
    return ElMessageBox.confirm(message, '系统提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      center: true,
      type: 'warning'
    });
  },

  loading: (message) => {
    loadingInstance = ElLoading.service({
      lock: true,
      text: message,
      background: 'rgba(0, 0, 0, 0.7)'
    });
  },

  closeLoading: () => {
    loadingInstance.close();
  }
};