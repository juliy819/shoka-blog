import type { PageQuery, PageResult, Result } from '@/model';
import type { AxiosPromise } from 'axios';

export interface LogApi {
  /**
   * 获取操作日志列表
   * @param logQuery 查询条件
   * @return 操作日志列表
   */
  getOperationLogList(logQuery?: LogQuery): AxiosPromise<Result<PageResult<OperationLog[]>>>;

  /**
   * 删除操作日志
   * @param ids 日志id集合
   */
  deleteOperation(ids: number[]): AxiosPromise<null>;

  /**
   * 获取异常日志列表
   * @param logQuery 查询条件
   * @return 异常日志列表
   */
  getExceptionLogList(logQuery?: LogQuery): AxiosPromise<Result<PageResult<ExceptionLog[]>>>;

  /**
   * 删除异常日志
   * @param ids 日志id集合
   */
  deleteException(ids: number[]): AxiosPromise<null>;

  /**
   * 获取访问日志列表
   * @param visitQuery 查询条件
   * @return 访问日志列表
   */
  getVisitLogList(visitQuery?: VisitQuery): AxiosPromise<Result<PageResult<VisitLog[]>>>;

  /**
   * 删除访问日志
   * @param ids 日志id集合
   */
  deleteVisit(ids: number[]): AxiosPromise<null>;
}

/**
 * 日志查询参数
 */
export interface LogQuery extends PageQuery {
  /**
   * 关键词
   */
  keywords?: string;
  /**
   * 操作模块
   */
  optModule?: string;
}

/**
 * 访问日志查询参数
 */
export interface VisitQuery extends PageQuery {
  /**
   * 关键词
   */
  keywords?: string;
}

/**
 * 操作日志
 */
export interface OperationLog {
  /**
   * id
   */
  id: number;
  /**
   * 操作模块
   */
  module: string;
  /**
   * 操作uri
   */
  uri: string;
  /**
   * 操作类型
   */
  type: string;
  /**
   * 操作方法
   */
  name: string;
  /**
   * 操作描述
   */
  description: string;
  /**
   * 请求方式
   */
  method: string;
  /**
   * 请求参数
   */
  params: string;
  /**
   * 返回数据
   */
  data: string;
  /**
   * 用户昵称
   */
  nickname: string;
  /**
   * 操作ip
   */
  ipAddress: string;
  /**
   * 操作地址
   */
  ipSource: string;
  /**
   * 操作耗时 (毫秒)
   */
  times: number;
  /**
   * 创建时间
   */
  createTime: string;
}

/**
 * 异常日志
 */
export interface ExceptionLog {
  /**
   * 异常id
   */
  id: number;
  /**
   * 异常模块
   */
  module: string;
  /**
   * 异常uri
   */
  uri: string;
  /**
   * 操作类型
   */
  type: string;
  /**
   * 异常名称
   */
  name: string;
  /**
   * 操作描述
   */
  description: string;
  /**
   * 异常方法
   */
  errorMethod: string;
  /**
   * 异常信息
   */
  message: string;
  /**
   * 请求参数
   */
  params: string;
  /**
   * 请求方式
   */
  requestMethod: string;
  /**
   * 操作ip
   */
  ipAddress: string;
  /**
   * 操作地址
   */
  ipSource: string;
  /**
   * 操作系统
   */
  os: string;
  /**
   * 浏览器
   */
  browser: string;
  /**
   * 创建时间
   */
  createTime: string;
}

/**
 * 访问日志
 */
export interface VisitLog {
  /**
   * 访问id
   */
  id: number;
  /**
   * 访问页面
   */
  page: string;
  /**
   * 访问ip
   */
  ipAddress: string;
  /**
   * ip来源
   */
  ipSource: string;
  /**
   * 浏览器
   */
  browser: string;
  /**
   * 操作系统
   */
  os: string;
  /**
   * 访问时间
   */
  createTime: string;
}