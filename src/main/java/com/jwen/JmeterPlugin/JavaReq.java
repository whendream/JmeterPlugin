package com.jwen.JmeterPlugin;

import java.lang.reflect.Type;
import java.util.Map;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import SDKClient;

public class JavaReq implements JavaSamplerClient {

	private Logger logger = LoggingManager.getLoggerForClass();
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private SDKClient client;


	/**
	 * 设置jmeter界面上的入参的默认值
	 */
	public Arguments getDefaultParameters() {

		Arguments args = new Arguments();
		args.addArgument("host", "127.0.0.1");
		args.addArgument("port", "1212");
		args.addArgument("model", "{\"name\":\"jun.wen\",\"age\":27}");
		args.addArgument("map", "{\"name\":\"jun.wen\",\"age\":27}");
		return args;

	}

	/**
	 * 方法执行，相当于触发jmeter的run
	 */
	public SampleResult runTest(JavaSamplerContext arg0) {

		SampleResult sampleResult = new SampleResult();
		sampleResult.setSuccessful(false);
		sampleResult.sampleStart();

		try {
			// 从getDefaultParameters中获取参数
			String host = arg0.getParameter("host");
			int port = arg0.getIntParameter("port");
			String model = arg0.getParameter("model");
			String map = arg0.getParameter("map");

			// 将string转为想要的model或者说类
			Type requireParaModeType = new TypeToken<Model>() {
			}.getType();
			Model requireParamsModel = gson.fromJson(model, requireParaModeType);

			// 将string转为map
			Type mapType = new TypeToken<Map<String, String>>() {
			}.getType();
			Map<String, String> options = gson.fromJson(map, mapType);

			// 调用SDK中的方法，获取返回
			String result = client.method(host, port, requireParamsModel, options);

			sampleResult.setResponseData(result + "", "UTF-8");
			sampleResult.setSuccessful(true);
			sampleResult.setResponseCodeOK();
		} catch (Exception e) {
			logger.error("Sample Error: ", e);
			sampleResult.setResponseCode("999");
			sampleResult.setResponseMessage(e.getMessage());
		} finally {
			return sampleResult;

		}

	}

	/**
	 * 初始化方法，实际运行时每个线程仅执行一次，在测试方法运行前执行
	 */
	public void setupTest(JavaSamplerContext arg0) {

		client = new SDKClient();

	}

	/**
	 * 结束方法，实际运行时每个线程仅执行一次，在测试方法运行结束后执行
	 */
	public void teardownTest(JavaSamplerContext arg0) {
		// TODO Auto-generated method stub

	}

}
