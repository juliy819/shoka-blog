import type { ArchivesApi } from '@/api/archives/types';
import request from '@/utils/request';

const archivesApi: ArchivesApi = {
  getArchivesList(pageQuery) {
    return request({
      url: '/article/archives/list',
      method: 'get',
      params: pageQuery
    });
  }
};

export default archivesApi;