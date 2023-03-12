import { createApp } from "vue";
import { createPinia } from "pinia";
import SvgIcon from "@/components/SvgIcon.vue";
import "@/permission";
import "nprogress/nprogress.css";
import "virtual:svg-icons-register";
import "@/assets/styles/index.scss";

import App from "./App.vue";
import router from "./router";

const app = createApp(App);

app.use(createPinia());
app.use(router);
app.component("svg-icon", SvgIcon);
app.mount("#app");
