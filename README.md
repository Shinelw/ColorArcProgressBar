# ColorArcProgressBar
[中文版](https://github.com/Shinelw/ColorArcProgressBar/blob/master/README_CHINESE.md)

This is a customizable circular progressbar.It can achieve the effect of the QQ health's arc progress with XML. What's more, we can use it by only a few codes to do the beautiful and colorful dashboard to show current data.

## Preview
 ![](https://raw.githubusercontent.com/Shinelw/ColorArcProgressBar/master/Demo.gif)
 
#Usage
##1、Add Dependency in gradle
```
dependencies {
    ...
    compile 'com.github.shinelw.colorarcprogressbar:library:1.0.3'
}
```
##2、XML
```
<com.shinelw.library.ColorArcProgressBar
        android:layout_width="300dp"
        android:layout_height="300dp"
        android:layout_gravity="center_horizontal"
        android:id="@+id/bar1"
        app:is_need_content="true"
        app:front_color1="@color/colorAccent"
        app:max_value="100"
        app:back_width="10dp"
        app:front_width="10dp"
        app:total_engle="360"
        app:is_need_unit="true"
        app:string_unit="百分比%"
        app:back_color="@android:color/darker_gray"
        android:layout_marginBottom="150dp"
        />
```
##3、Code
```
progressbar.setCurrentValues(100);
```

##4、Customize
###1）set arc total engle
```
 app:total_engle="270" 
```
###2）set color gradient
```
app:front_color1="#00ff00"
app:front_color2="#ffff00"
app:front_color3="#ff0000"
```
###3)set two arc width
```
app:back_width="2dp"
app:front_width="10dp"
```
###4)set text（title，content，unit） in arc center
```
app:is_need_unit="true"
app:string_unit="步"
app:is_need_title="true"
app:string_title="截止当前已走"
```
preview like QQ health：

![](https://raw.githubusercontent.com/Shinelw/ColorArcProgressBar/master/demo_qq.gif)

###5）set dial 
```
  app:is_need_dial="true"
```

preview like dashboard：

![](https://raw.githubusercontent.com/Shinelw/ColorArcProgressBar/master/demo_dashboard.gif)



