# CartButton
CartButton是一款购物车增加减少控件，方便增加减少产品

![展示](https://github.com/DaitoudageXie/CartButton/raw/master/pic/1.png)
## 1.集成方法
 Step 1. Add the JitPack repository to your build file
 ```
 {
  	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 ```
 Step 2. Add the dependency
 ```
 	dependencies {
	        compile 'com.github.DaitoudageXie:CartButton:1.0.0'
	}

 ```
 
## 2.使用说明
|属性      |说明   |
|---------|-------:|
|app:addDisableBgColor=""|增加按钮不可用背景|
|app:addDisableFgColor=""|增加按钮不可用前景色|
|app:addEnableBgColor=""|增加按钮可用背景|
|app:addEnableFgColor=""|增加按钮可用前景色|
|app:delDisableBgColor=""|减少按钮不可用背景|
| app:delDisableFgColor=""|减少按钮不可用前景|
| app:delEnableBgColor=""|减少按钮可用背景|
|app:delEnableFgColor=""|减少按钮可用前景|
|app:count=""|数量设置|
| app:maxCount=""|最大数量|
|app:radius=""|圆圈半径|
| app:circleStokeWidth=""|圆圈宽度|
| app:lineWidth=""|加减号线的宽度|
 
 示例代码
 ```
   cartNumView= (CartNumView) findViewById(R.id.cartNumView);

        cartNumView.setClickListener(new CartNumClickListener() {
            @Override
            public void onAdd(int count) {
                Toast.makeText(MainActivity.this,"增加"+count,Toast.LENGTH_SHORT).show();
                cartNumView.increaseCount();

            }
            @Override
            public void onDel(int count) {

                cartNumView.reduceCount();
                //
                Toast.makeText(MainActivity.this,"减少"+count,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEmety() {
                Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_SHORT).show();

            }
        });
 ```
 
 **注意事项：onAdd(int count)回调方法返回的count 并没有增加 方便对购物车数据操作成功后（存取数据库或网络请求）再调用increaseCount方法更改，onDel方法同理**
