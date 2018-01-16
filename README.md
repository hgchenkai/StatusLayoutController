**StatusLayoutController**  管理不同数据状态时视图

**使用**
　　
　　创建自定义的Options（如果不设置，则使用默认的）
```java 
   Options option=new Builder()
                .loadingResId(R.layout.layout_loading_def)
                .errorResId(R.layout.layout_error_def)
                .emptyResId(R.layout.layout_empty_def)
                .errorReloadId(R.id.tv_error_reload)
                .emptyRefreshId(R.id.tv_empty_refresh)
                .callback(callback)
                .build();
```
　　创建StatusListController实例：
```java
 StatusListController statusListController = new StatusListController.Builder(rv)
                .apply(option)
                .callback(new ICallback() {
                    @Override
                    public void refreshCallback() {
                        post(0);
                    }

                    @Override
                    public void reloadCallback() {
                        post(0);
                    }
                }).build();
```
　　使用
```java
statusListController.showLoadingView();
statusListController.showContentView();
statusListController.showEmptyView();
statusListController.showErrorView();
```
