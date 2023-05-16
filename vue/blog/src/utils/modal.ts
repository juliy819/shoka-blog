/**
 * 模态框
 */
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
    // ElMessage.info(message);
    window.$message?.info(message);
  },

  msgError: (message) => {
    // ElMessage.error(message);
    window.$message?.error(message);
  },

  msgSuccess: (message) => {
    // ElMessage.success(message);
    window.$message?.success(message);
  },

  msgWarning: (message) => {
    window.$message?.warning(message);
  },

  notifyInfo: (message) => {

  },

  notifyError: (message) => {
    window.$notification?.error({
      title: '错误',
      content: message
    });
  },

  notifySuccess: (message) => {

  },

  notifyWarning: (message) => {

  },

  loading: (message) => {

  },

  closeLoading: () => {
  }
};