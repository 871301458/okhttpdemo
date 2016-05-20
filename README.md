# okhttpdemo
okhttp的简单介绍和简单的封装
![这里写图片描述](http://img.blog.csdn.net/20160125132235675)

使用方法：
如果是使用的first里面封装好的okhttp请求，那么就需要你自己去封装解析数据了。方法如下
  OkHttpManager.getAsync(Contants.ASYNC_URL, new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
            //在这个地方返回的是请求的数据
               // tvtext.setText(result);
            }
        });


如果是使用的second里面封装好的okhttp请求，那么这个时候就不需要在去自己解析了，
直接传入javabean，返回的结果就是已经封装好的，事例代码如下：
 mHttpHelper.get(url, new SpotsCallBack<List<T>>(this) {

            @Override
            public void onSuccess(Response response, Object o) {
                List<T> t = (List<t>) o;   //这个o就是返回的数据集合,T就是你封装好的javabean
                
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
而且第二种还有一个请求的dialog哈，是不是很简单的。
