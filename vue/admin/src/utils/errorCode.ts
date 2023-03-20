interface ErrorCode {
  [key: string]: string;
}

export const errorCode: ErrorCode = {
  '-1': '系统异常',
  '400': '参数错误',
  '401': '认证失败',
  '404': '访问资源不存在',
  '500': '操作失败',
  'default': '系统未知错误，请反馈给管理员'
};