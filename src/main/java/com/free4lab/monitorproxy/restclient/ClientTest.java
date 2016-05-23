package com.free4lab.monitorproxy.restclient;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import com.free4lab.monitorproxy.hbasetemp.test;

public class ClientTest {
	
	public ArrayList<String> it = new  ArrayList<String>();
	
	public static void main(String[] args){
		ClientTest clientTest = new ClientTest();
		clientTest.getLog();
	}
	
	public void getLog(){
		String BEGINTIME = "1456300139978";
		String ENDTIME = "1456904939978";
		ThreadPool threadPool  = new ThreadPool();
//		it.add("30");it.add("33");it.add("34");it.add("35");it.add("37");it.add("38");
		it.add("30");
		ArrayList<FutureTask<String>> FutureTaskList = new ArrayList<FutureTask<String>>();
		for(String id : it){
			
				FutureTask<String> futureTaskCpu = new FutureTask<String>(new clientTask(id, BEGINTIME, ENDTIME, "cpu"));
		        ThreadPool.submitThread(futureTaskCpu);
		        FutureTaskList.add(futureTaskCpu);
		        
		        FutureTask<String> futureTaskMem = new FutureTask<String>(new clientTask(id, BEGINTIME, ENDTIME, "mem"));
		        ThreadPool.submitThread(futureTaskMem);
		        FutureTaskList.add(futureTaskMem);
		        
		        FutureTask<String> futureTaskIo = new FutureTask<String>(new clientTask(id, BEGINTIME, ENDTIME, "io"));
		        ThreadPool.submitThread(futureTaskIo);
		        FutureTaskList.add(futureTaskIo);
		        
		        FutureTask<String> futureTaskTpcc = new FutureTask<String>(new clientTask(id, BEGINTIME, ENDTIME, "tpcc"));
		        ThreadPool.submitThread(futureTaskTpcc);
		        FutureTaskList.add(futureTaskTpcc);
		        
		        FutureTask<String> futureTaskPing = new FutureTask<String>(new clientTask(id, BEGINTIME, ENDTIME, "ping"));
		        ThreadPool.submitThread(futureTaskPing);
		        FutureTaskList.add(futureTaskPing);
		        
		}
			
		for (FutureTask<String> futureTask : FutureTaskList ){
			try {
				try {
					futureTask.get();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
		
			
	
	
	
	class clientTask implements Callable<String>{
		private String result = "";
		private String id;
		private String Start;
		private String end;
		private String topic;
		
		public clientTask(String id, String Start, String end, String topic){
			 this.id = id;
			 this.Start = Start;
			 this.end = end;
			 this.topic = topic;
		}
		
	    @Override
	    public String call() throws Exception {
	    	switch(topic){
			case "cpu":System.out.println(test.getCpu(id, Start, end));
				break;
			case "mem":System.out.println(test.getMem(id, Start, end));
				break;
			case "io":System.out.println(test.getIozone(id, Start, end));
				break;
			case "tpcc":System.out.println(test.getTpcc(id, Start, end));
				break;
			case "ping":System.out.println(test.getPing(id, Start, end));
				break;
			}
	    	return result;
	    }
	    
	}

}
