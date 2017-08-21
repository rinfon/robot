# Robot

异步操作类。

等待所有异步任务完成后的结果汇总，可以根据不同的任务返回不同的结果

## 用法

* Android Studio
	
	```
	compile 'com.github.rinfon:robot:0.1'
	```
	

## 配置application


```java
public class App extends Application 
{ 
 	@Override     public void onCreate() 
    {         
        super.onCreate();
         Robot.init();
        
         //        set threads number   Robot.getInstance().setJobThreads(5);    
   
    } 

}

```
别忘了在AndroidManifest中设置。


在需要调用的地方使用

```java
final IJob job = new IJob()
{            
	@Override
	public Object job() {                 
		Log.i(TAG, "job 1 start sleep 0s");
		return "finish job 1";             
	}         
};          
Robot.getInstance().group()
					.add(job)
  					.add(new IJob() {
                 		@Override
                     	public Object job() {
						Log.i(TAG, "job 2 start sleep 2s"); 
							try { 							
								Thread.sleep(2000); 
								Log.i(TAG, "job 2 finish sleep"); 
							} catch (InterruptedException e) { 
								e.printStackTrace(); 				
							} 
							try { 
								return new JSONObject("{'name':'job 2'}"); 							} catch (JSONException e) { 
									e.printStackTrace(); 													return null; 
							} 						
						} 
						}) 
						.add(new IJob() {                     
							@Override 							
							public Object job() { 
								Log.i(TAG, "job 3 start sleep 3s");                         
								try {                             
									Thread.sleep(3000); 
									Log.i(TAG, "job 3 finish sleep"); 
								} catch (InterruptedException e) { 
									e.printStackTrace(); 
								} 
								return 3; 
							} 
						}) 
						.last(new IJob() { 
							@Override 
							public Object job() { 
								Log.i(TAG, "job 4 start sleep 4s"); 
								try { 
										Thread.sleep(4000); 
										Log.i(TAG, "job 4 finish sleep"); 
								} catch (InterruptedException e) { 
									e.printStackTrace(); 
								} 
								return "finish job 4"; 
							} 
						}) 
						.onFinish(new RobotStatus() { 
							@Override 
							public void onFinishJobs(HashMap<String, Object> resultList) { 
							Log.i(TAG, "all finish"); 
//                        result maybe null,add try catch 
							String job1result = String.valueOf(resultList.get(String.valueOf(job.hashCode()))); 
//                        you can get another result by this way 
							Log.i(TAG, "job1result" + job1result); 
						}}).start();
```





