
## CoordinatorLayout

### `app:layout_scrollFlags`

- `scroll`  
   
控件可滚动


- `enterAlways`

当前控件和可滚动控件的先后顺序，添加此属性的控件将优于滚动控件先滚动

- `enterAlwaysCollapsed`
   
   
`enterAlways`的附加值
   
```xml 
...
android:layout_height="@dimen/dp_200"
android:minHeight="@dimen/dp_56"
...
app:layout_scrollFlags="scroll|enterAlways|enterAlwaysCollapsed"
...
```

设置该属性之后，childView先滚动到最小高度，然后滚动scrollView，滚动完scrollView之后继续滚动childView

    

- `snap`

吸附效果，当childView滚动没有显示完全时，松开要么全部消失，要么全部显示，不存在停留到中间的时刻

- `exitUntilCollapsed`

childView不会完全消失，滚到到指定minHeight时，不再消失


