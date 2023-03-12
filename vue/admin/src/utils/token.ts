import Cookies from "js-cookie";

const TokenKey: string = "Token";

// token前缀
export const token_prefix = "juliy ";

export function getToken() {
  return Cookies.get(TokenKey);
}

export function setToken(token: string) {
  return Cookies.set(TokenKey, token);
}

export function removeToken() {
  return Cookies.remove(TokenKey);
}
