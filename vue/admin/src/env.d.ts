/// <reference types="vite/client" />
interface ImportMetaEnv {
  readonly VITE_APP_TITLE: string,
  readonly VITE_APP_ENV: string,
  readonly VITE_APP_BASE_API: string,
  readonly VITE_HOME_URL: string,
  readonly VITE_GITHUB_URL: string,
  readonly VITE_GITEE_URL: string,
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}