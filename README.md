# ShecareSdkDemo_Android

## 在自定义的 Application 中初始化
    public class App extends Application {
    
        @Override
        public void onCreate() {
            super.onCreate();
    
            ShecareSdk.init(this, "123456", "1");
        }
    }

