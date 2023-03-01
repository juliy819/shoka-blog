import {fileURLToPath, URL} from 'node:url'

import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import {ElementPlusResolver} from 'unplugin-vue-components/resolvers'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        AutoImport({
            include: [/\.vue$/, /\.vue\?vue/, /\.md$/],
            resolvers: [ElementPlusResolver()],
            imports: [
                'vue',
                'vue-router',
                'pinia'
            ],
            dts: "src/types/auto-imports.d.ts",
        }),
        Components({
            dirs: ["src/components"],
            include: [/\.vue$/, /\.vue\?vue/, /\.md$/],
            resolvers: [ElementPlusResolver()],
            dts: "src/types/components.d.ts",
        }),
    ],
    resolve: {
        alias: {
            //设置路径别名
            "~": fileURLToPath(new URL("./", import.meta.url)),
            "@": fileURLToPath(new URL("./src", import.meta.url)),
        },
        extensions: [".mjs", ".js", ".ts", ".jsx", ".tsx", ".json", ".vue"],
    },
    server: {
        //端口
        port: 8082,
        //若端口被占用，则自动尝试下一个可用的端口
        strictPort: false,
        //服务启动后自动在浏览器打开
        open: true,
        //代理
        //http://localhost:8082/api/bar -> http://localhost:8080/bar
        proxy: {
            "/api": {
                target: "http://localhost:8080",
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, ""),
            },
        },
    }
})
