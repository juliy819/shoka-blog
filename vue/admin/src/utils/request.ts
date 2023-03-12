import type {
  AxiosError,
  AxiosResponse,
  InternalAxiosRequestConfig
} from "axios";
import axios from "axios";
import useStore from "@/stores";
import { ElMessage, ElNotification } from "element-plus";
import { messageConfirm } from "./modal";
import { getToken, token_prefix } from "./token";

// 是否显示重新登录
export const isReLogin = { show: false };

const requests = axios.create({
  baseURL: "/api",
  timeout: 10000,
  headers: {
    "Content-Type": "application/json;charset=UTF-8"
  }
});

// 请求拦截器
requests.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // 请求带token
    if (getToken()) {
      config.headers["Authorization"] = token_prefix + getToken();
    }
    return config;
  },
  (error: AxiosError) => {
    return Promise.reject(error);
  }
);

// 配置响应拦截器
requests.interceptors.response.use(
  (response: AxiosResponse) => {
    const { user } = useStore();
    switch (response.data.code) {
      case 400:
        ElNotification({
          title: "失败",
          message: response.data.msg,
          type: "error"
        });
        break;
      case 402:
        if (!isReLogin.show) {
          isReLogin.show = true;
          messageConfirm("登录状态已过期，您可以继续留在该页面，或者重新登录")
            .then(() => {
              isReLogin.show = false;
              user.LogOut().then(() => {
                location.href = "/login";
              });
            })
            .catch(() => {
              isReLogin.show = false;
            });
        }
        break;
      case 500:
        ElNotification({
          title: "失败",
          message: response.data.msg,
          type: "error"
        });
        break;
    }
    return response;
  },
  (error: AxiosError) => {
    let { message } = error;
    if (message == "Network Error") {
      message = "后端接口连接异常";
    } else if (message.includes("timeout")) {
      message = "系统接口请求超时";
    } else if (message.includes("Request failed with status code")) {
      message = "系统接口" + message.substring(message.length - 3) + "异常";
    }
    ElMessage({
      message: message,
      type: "error",
      duration: 5 * 1000
    });
    return Promise.reject(error);
  }
);

export default requests;
