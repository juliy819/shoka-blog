import { createApp } from 'vue';
import { createPinia } from 'pinia';
import piniaPersist from 'pinia-plugin-persist';
import SvgIcon from '@/components/SvgIcon/index.vue';
import '@/permission';
import 'nprogress/nprogress.css';
import 'virtual:svg-icons-register';
import '@/assets/styles/index.scss';

import App from './App.vue';
import router from './router';

const app = createApp(App);
const pinia = createPinia();

pinia.use(piniaPersist);
app.use(pinia);
app.use(router);
app.component('svg-icon', SvgIcon);
app.mount('#app');
