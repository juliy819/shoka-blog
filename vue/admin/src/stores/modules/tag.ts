import { defineStore } from 'pinia';
import type { TagsView, TagViewState } from '@/stores/interface';

const useTagStore = defineStore('useTagStore', {
  state: (): TagViewState => ({
    visitedViews: []
  }),
  actions: {
    /**
     * 添加标签页
     * @param view 页面
     */
    addView(view: TagsView) {
      // 若标签页已存在
      if (this.visitedViews.some(v => v.path === view.path)) {
        return;
      }
      if (view.meta?.affix) {
        // 若是固定标签页，则添加到开头
        this.visitedViews.unshift(
          // 设置标签名，若没有则设为'no-name'
          Object.assign({}, view, {
            title: view.meta?.title || 'no-name'
          })
        );
      } else {
        // 若不是固定标签页，则添加到末尾
        this.visitedViews.push(
          Object.assign({}, view, {
            title: view.meta?.title || 'no-name'
          })
        );
      }
    },

    /**
     * 删除标签页
     * @param view 页面
     */
    delView(view: TagsView): Promise<TagsView[]> {
      return new Promise(resolve => {
        const index = this.visitedViews.findIndex(v => v.path === view.path);
        if (index !== -1) {
          this.visitedViews.splice(index, 1);
        }
        resolve([...this.visitedViews]);
      });
    },

    /**
     * 删除其它标签页
     * @param view 页面
     */
    delOtherViews(view: TagsView): Promise<TagsView[]> {
      return new Promise(resolve => {
        this.visitedViews = this.visitedViews.filter(
          v => v.meta?.affix || v.path === view.path
        );
        resolve([...this.visitedViews]);
      });
    },

    /**
     * 删除左侧标签页
     * @param view 页面
     */
    delLeftViews(view: TagsView): Promise<TagsView[]> {
      return new Promise(resolve => {
        const currentIndex = this.visitedViews.findIndex(
          v => v.path === view.path
        );
        if (currentIndex === -1) {
          return;
        }
        this.visitedViews = this.visitedViews.filter(
          (v, index) => !!(index >= currentIndex || v.meta?.affix)
        );
        resolve([...this.visitedViews]);
      });
    },

    /**
     * 删除右侧标签页
     * @param view 页面
     */
    delRightViews(view: TagsView): Promise<TagsView[]> {
      return new Promise(resolve => {
        const currentIndex = this.visitedViews.findIndex(
          v => v.path === view.path
        );
        if (currentIndex === -1) {
          return;
        }
        this.visitedViews = this.visitedViews.filter(
          (v, index) => !!(index <= currentIndex || v.meta?.affix)
        );
        resolve([...this.visitedViews]);
      });
    },

    /**
     * 删除所有标签页
     */
    delAllViews(): Promise<TagsView[]> {
      return new Promise(resolve => {
        this.visitedViews = this.visitedViews.filter(v => v.meta?.affix);
        resolve([...this.visitedViews]);
      });
    },

    /**
     *更新标签页
     * @param view 页面
     */
    updateView(view: TagsView) {
      for (let v of this.visitedViews) {
        if (v.path === view.path) {
          v = Object.assign(v, view);
          break;
        }
      }
    }
  },
  getters: {}
});

export default useTagStore;
