/**
 * 更改页面主题色
 * @param theme 主题颜色 hex格式
 */
export const handleThemeStyle = (theme: string) => {
  document.documentElement.style.setProperty('--el-color-primary', theme);
  for (let i = 1; i <= 9; i++) {
    document.documentElement.style.setProperty(`--el-color-primary-light-${ i }`, `${ getLightColor(theme, i / 10) }`);
  }
  for (let i = 1; i <= 9; i++) {
    document.documentElement.style.setProperty(`--el-color-primary-dark-${ i }`, `${ getDarkColor(theme, i / 10) }`);
  }
};

/**
 * hex转rgb
 * @param hex hex格式颜色
 */
const hexToRgb = (hex: string): number[] => {
  hex = hex.replace('#', '');
  let hexs = hex.match(/../g)!;
  let rgb: number[] = [];
  for (let i = 0; i < 3; i++) {
    rgb[i] = parseInt(hexs[i], 16);
  }
  return rgb;
};

/**
 * rgb转hex
 * @param rgb rgb格式颜色数组，0-r;1-g;2-b
 */
const rgbToHex = (rgb: number[]): string => {
  let hexs = [rgb[0].toString(16), rgb[0].toString(16), rgb[0].toString(16)];
  for (let i = 0; i < 3; i++) {
    if (hexs[i].length === 1) {
      hexs[i] = `0${ hexs[i] }`;
    }
  }
  return `#${ hexs.join('') }`;
};

/**
 * 获取更亮的颜色
 * @param color 原来的颜色 hex格式
 * @param level 等级
 */
const getLightColor = (color: string, level: number): string => {
  let rgb = hexToRgb(color);
  for (let i = 0; i < 3; i++) {
    rgb[i] = Math.floor(255 - rgb[i]) * level + rgb[i];
  }
  return rgbToHex(rgb);
};

/**
 * 获取更暗的颜色
 * @param color 原来的颜色 hex格式
 * @param level 等级
 */
const getDarkColor = (color: string, level: number): string => {
  let rgb = hexToRgb(color);
  for (let i = 0; i < 3; i++) {
    rgb[i] = Math.floor(rgb[i] * (1 - level));
  }
  return rgbToHex(rgb);
};