import type { CheckDTO, PageQuery, PageResult, Result } from '@/model';
import type { AxiosPromise } from 'axios';

export interface MessageApi {
  /**
   * 查看留言列表
   * @param messageQuery 查询条件
   * @returns 留言列表
   */
  getMessageList: (messageQuery: MessageQuery) => AxiosPromise<Result<PageResult<Message[]>>>;

  /**
   * 删除留言
   * @param data 留言id集合
   */
  deleteMessage: (messageIds: number[]) => AxiosPromise<Result<null>>;

  /**
   * 审核留言
   * @param checkDTO 审核信息
   */
  updateMessageCheck: (checkDTO: CheckDTO) => AxiosPromise<Result<null>>;
}

export interface Message {
  /**
   * 留言id
   */
  id: number;
  /**
   * 昵称
   */
  nickname: string;
  /**
   * 头像
   */
  avatar: string;
  /**
   * 留言内容
   */
  messageContent: string;
  /**
   * 用户ip
   */
  ipAddress: string;
  /**
   * 用户地址
   */
  ipSource: string;
  /**
   * 是否通过 (0否 1是)
   */
  isCheck: number;
  /**
   * 留言时间
   */
  createTime: string;
}

/**
 * 留言查询参数
 */
export interface MessageQuery extends PageQuery {
  /**
   * 关键词
   */
  keywords?: string;
  /**
   * 是否通过 (0否 1是)
   */
  isCheck?: number;
}
