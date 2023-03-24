import type { Password } from '@/api/user/types';
import request from '@/utils/request';

/**
 * 修改管理员密码
 * @param data 密码
 */
export function updateAdminPassword(data: Password) {
  return request({
    url: '/admin/password',
    method: 'put',
    data,
  });
}
