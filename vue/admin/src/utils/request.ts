import type { AxiosError, AxiosResponse, InternalAxiosRequestConfig } from 'axios';
import axios from 'axios';
import useStore from '@/stores';
import { modal } from './modal';
import { getToken, token_prefix } from './token';
import { errorCode } from '@/utils/errorCode';

// 是否显示重新登录
export const isReLogin = { show: false };

// 创建axios实例
const requests = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: import.meta.env.VITE_APP_BASE_API,
  // 超时
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json;charset=UTF-8'
  }
});

// 请求拦截器
requests.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // 是否需要设置token
    const isToken = (config.headers || {}).isToken === false;
    if (getToken() && !isToken) {
      config.headers['Authorization'] = token_prefix + getToken();
    }
    return config;
  },
  (error: AxiosError) => {
    console.error(error);
    return Promise.reject(error);
  }
);

// 响应拦截器
requests.interceptors.response.use(
  (response: AxiosResponse) => {
    // 未设置状态码则默认成功状态
    const code = response.data.code || 200;
    // 获取错误信息
    const message = response.data.msg || errorCode[code] || errorCode['default'];

    const { userStore } = useStore();

    if (code === 401) {
      if (!isReLogin.show) {
        isReLogin.show = true;
        modal.messageConfirm('登录状态已过期，您可以继续留在该页面，或者重新登录')
          .then(() => {
            isReLogin.show = false;
            userStore.logout().then(() => {
              location.href = '/login';
            });
          })
          .catch(() => {
            isReLogin.show = false;
          });
      }
      return Promise.reject('无效的会话，或者会话已过期，请重新登录。');
    } else if (code === 601) {
      modal.notifyError(message);
      return Promise.reject(new Error(message));
    } else if (code != 200) {
      modal.notifyError(message);
      return Promise.reject(message);
    } else {
      return Promise.resolve(response);
    }
  },
  (error: AxiosError) => {
    let { message } = error;
    if (message === 'Network Error') {
      message = '后端接口连接异常';
    } else if (message.includes('timeout')) {
      message = '系统接口请求超时';
    } else if (message.includes('Request failed with status code')) {
      message = '系统接口' + message.substring(message.length - 3) + '异常';
    }
    modal.msgError(message);
    return Promise.reject(error);
  }
);

export default requests;
