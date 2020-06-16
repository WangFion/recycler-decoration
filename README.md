# Recycler-Decoration
A RecyclerView item decoration that supports custom width and color.


# Getting start
**First, Add the following information to build.gradle under the project root directory.**
```
allprojects {
    repositories {
        ......
        maven { url 'https://jitpack.io' }
    }
}
```

**Second, Add one of the platforms below to build.gradle under the module directory. <br>
The latest release version is [![](https://jitpack.io/v/WangFion/recycler-decoration.svg)](https://jitpack.io/#WangFion/recycler-decoration)**
```
dependencies {
    implementation 'com.github.WangFion:recycler-decoration:1.0.1'
}
```

**Third, Add item decoration to recyclerview.**
```kotlin
//1.横竖相同
recyclerView.addItemDecoration(SimpleRecyclerDecoration(Color.parseColor("#0000ff"),20))
//2.横竖单独设置
recyclerView.addItemDecoration(SimpleRecyclerDecoration(Color.parseColor("#ff0000"), Color.parseColor("#000000"),20, 40))
``` 

# e.g.
<img src="https://github.com/WangFion/recycler-decoration/blob/master/image/device-1.png" width="200"/>    <img src="https://github.com/WangFion/recycler-decoration/blob/master/image/device-2.png" width="200"/>    <img src="https://github.com/WangFion/recycler-decoration/blob/master/image/device-3.png" width="200"/>    <img src="https://github.com/WangFion/recycler-decoration/blob/master/image/device-4.png" width="200"/>
