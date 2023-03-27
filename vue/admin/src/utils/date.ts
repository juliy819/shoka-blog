import { dayjs } from 'element-plus';

export const formatDate = (date: string | Date, format = 'YYYY-MM-DD'): string => {
  if (date == null) {
    return '';
  }
  return dayjs(date).format(format);
};

export const formatDateTime = (date: string | Date, format = 'YYYY-MM-DD HH:mm:ss'): string => {
  return formatDate(date, format);
};