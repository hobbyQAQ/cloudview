# 智能分类云相册
### 一、界面展示
![3GGW_@3F_I_XKMGEHE_QB_G.png](https://i.loli.net/2021/06/01/1DjpEC9LhX5Ao27.png)
主界面、分类界面、搜索界面、图片详情
![6__LU_V2L7H_1C88_PDVECO.png](https://i.loli.net/2021/06/01/5K1RnvU4pyXPLFb.png)
编辑图片、拍照


图像详情界面内有图片的具体操作，图片识别也是在那完成的。

### 二、使用的框架

```
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'androidx.appcompat:appcompat:1.0.2'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test.ext:junit:1.1.0'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.1.1'
    implementation 'com.wonderkiln:camerakit:0.13.1'
    implementation 'com.camerakit:camerakit:1.0.0-beta3.11'
    implementation 'com.camerakit:jpegkit:0.1.0'


    implementation 'org.tensorflow:tensorflow-lite:0.0.0-nightly-SNAPSHOT'
    implementation 'org.tensorflow:tensorflow-lite-gpu:0.0.0-nightly-SNAPSHOT'
    implementation 'org.tensorflow:tensorflow-lite-support:0.1.0'
    implementation 'org.tensorflow:tensorflow-lite:+'

    implementation 'com.squareup.retrofit2:retrofit:2.6.3'
    implementation 'com.squareup.retrofit2:converter-gson:2.7.0'
    implementation 'com.lcodecorex:tkrefreshlayout:1.0.7'
    implementation 'androidx.recyclerview:recyclerview:1.1.0'
    implementation 'com.jakewharton:butterknife:10.2.1'
    annotationProcessor 'com.jakewharton:butterknife-compiler:10.2.1'
    implementation 'com.google.android.material:material:1.2.0-alpha03'
    implementation 'com.ashokvarma.android:bottom-navigation-bar:2.2.0'
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
    implementation "org.permissionsdispatcher:permissionsdispatcher:4.7.0"
    annotationProcessor "org.permissionsdispatcher:permissionsdispatcher-processor:4.7.0"
    implementation 'com.github.chrisbanes:PhotoView:2.0.0'
    implementation 'de.hdodenhof:circleimageview:3.1.0'
    //知乎图片选择
    implementation group: 'com.zhihu.android', name: 'matisse', version: '0.5.3-beta3'
    implementation 'com.squareup.picasso:picasso:2.3.2'
    implementation 'com.leon:lsettingviewlibrary:1.7.0'
    implementation 'com.jph.takephoto:takephoto_library:4.0.3'
    implementation 'com.yanzhenjie:permission:1.0.5'
    implementation 'com.lcodecorex:tkrefreshlayout:1.0.7'
    implementation 'com.github.HanHuoBin:BaseDialog:1.2.0'
    api project(':imageeditlibrary')
}
```

