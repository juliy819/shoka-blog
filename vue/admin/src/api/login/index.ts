import type {Result} from "@/model";
import request from "@/utils/request";
import type {AxiosPromise} from "axios";
import type {LoginForm} from "./types";

/**
 * 用户登录
 * @param data 登录信息
 * @returns Token
 */
export function login(data: LoginForm): AxiosPromise<Result<string>> {
    return request({
        url: "/login",
        method: "post",
        data,
    });
}

/**
 * 用户退出
 */
export function logout(): AxiosPromise<Result<null>> {
    return request({
        url: "/logout",
        method: "get",
    });
}
