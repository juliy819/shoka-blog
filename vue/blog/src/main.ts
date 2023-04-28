import { createApp } from 'vue';
import { createPinia } from 'pinia';
import piniaPersist from 'pinia-plugin-persist';
import SvgIcon from '@/components/SvgIcon.vue';
import 'nprogress/nprogress.css';
import 'virtual:svg-icons-register';
import '@/assets/styles/index.scss';
import '@/assets/fonts/font.css';
import App from './App.vue';
import router from './router';
import '@/router/guard';
import 'swiper/css';
import 'swiper/css/autoplay';
import 'swiper/css/mousewheel';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';
import createEmojiPlugin from '@kangc/v-md-editor/lib/plugins/emoji/index';
import '@kangc/v-md-editor/lib/plugins/emoji/emoji.css';
import createTodoListPlugin from '@kangc/v-md-editor/lib/plugins/todo-list/index';
import '@kangc/v-md-editor/lib/plugins/todo-list/todo-list.css';
import Prism from 'prismjs';
import { animate } from '@/directive/animate';
import VueViewer from 'v-viewer';
import 'viewerjs/dist/viewer.css';

const app = createApp(App);
const pinia = createPinia();

app.directive('animate', animate);

VMdPreview.use(vuepressTheme, { Prism })
  .use(createTodoListPlugin())
  .use(createEmojiPlugin());

pinia.use(piniaPersist);
app.use(pinia);
app.use(router);
app.use(VMdPreview);
app.use(VueViewer);

app.component('svg-icon', SvgIcon);
app.mount('#app');
