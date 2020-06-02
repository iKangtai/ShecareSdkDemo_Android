# ShecareSdkDemo_Android

## 引入方式
    1.第一种方式,复制SDK aar文件到工程的app/libs/目录下，然后gradle配置
        android {
            repositories {
                flatDir {
                dirs 'libs'
                }
            }
        }

        dependencies {
            implementation(name: 'shecaresdk-release-v0.2', ext: 'aar')
        }


    2.第二种方式，复制Demo的shecaresdk模块配置到工程，然后gradle dependencies添加implementation project(':shecaresdk')
## 在自定义的 Application 中初始化
    public class App extends Application {
    
        @Override
        public void onCreate() {
            super.onCreate();
    
            ShecareSdk.init(this, "123456", "1");
        }
    }

