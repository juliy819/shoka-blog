/**
 * 结果返回接口
 */
export interface Result<T> {

  /** 返回状态 */
  flag: boolean;

  /** 状态码 */
  code: number;

  /** 返回信息 */
  msg: string;

  /** 返回数据 */
  data: T;
}