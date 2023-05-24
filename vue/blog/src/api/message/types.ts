import type { Result } from '@/model';
import type { AxiosPromise } from 'axios';

export interface MessageApi {
  /**
   * 获取留言列表
   * @returns 文章分类
   */
  getMessageList(): AxiosPromise<Result<Message[]>>;

  /**
   * 添加留言
   * @param messageForm 留言
   */
  addMessage(messageForm: MessageForm): AxiosPromise<Result<null>>;

}

/**
 * 留言
 */
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
}

/**
 * 留言表单
 */
export interface MessageForm {

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
}
