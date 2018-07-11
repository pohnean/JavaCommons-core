package picoded.core.web;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import picoded.core.web.RequestHttp;
import picoded.core.conv.ConvertJSON;
import picoded.core.conv.GUID;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import okhttp3.Cookie;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This test cases will cover all the funcitonality of RequestHttp
 * The HTTP request methods to be tested are : GET, POST, PUT, DELETE
 */
public class RequestHttp_test {

	MockWebServer mockWebServer = null;
	RequestHttp requestHttp = null;

	@Before
	public void setup() {
		try {
			mockWebServer = new MockWebServer();

			// Start server at any available port in the system
			mockWebServer.start(0);

		} catch (IOException io) {
			throw new RuntimeException(io);
		}

		// Initialize the http client
		requestHttp = new RequestHttp();
	}

	@After
	public void destroy() {
		try {
			// Shut down the server. Instances cannot be reused.
			mockWebServer.shutdown();
		} catch (IOException io) {
			throw new RuntimeException(io);
		}
	}

	@Test
	public void initializeRequestHttp_test() {
		assertNotNull(requestHttp);
	}

	//------------------------------------------------
	//
	//  GET request test units
	//
	//------------------------------------------------
	
	/**
	 * This basic GET request test retrieves information from the server
	 */
	@Test
	public void getRequestBasic_test() {
		// Simple hello world test
		// Add the body for the response
		mockWebServer.enqueue(new MockResponse().setBody("hello, world!"));
		// Retrieve mockResponse from server and assert the results
		ResponseHttp responseHttp = requestHttp.get(mockWebServer.url("/").toString());
		assertEquals(responseHttp.statusCode(), 200);
		assertEquals(responseHttp.toString(), "hello, world!");
	}

	/**
	 * This test assert the request params sent via GET request
	 * was received correctly on the server side
	 *
	 * @throws InterruptedException
	 */
	@Test
	public void getRequestWithParams_test() throws InterruptedException {
		mockWebServer.enqueue(new MockResponse().setBody("hello, world!"));
		
		// Prepare headers
		Map<String, Object> requestParams = new HashMap<String, Object>();
		requestParams.put("first", "hello");
		requestParams.put("second", "world");
		
		// Retrieve mockResponse from server and assert the results
		ResponseHttp responseHttp = requestHttp.get(mockWebServer.url("/").toString(), requestParams);
		assertEquals(responseHttp.statusCode(), 200);
		assertEquals(responseHttp.toString(), "hello, world!");
		
		// Check sent request's params
		RecordedRequest sentRequest = mockWebServer.takeRequest();
		assertEquals("/?first=hello&second=world", sentRequest.getPath());
	}

	/**
	 * This test assert the cookies sent via GET
	 * was received correctly on the server side
	 *
	 * @throws InterruptedException
	 */
	@Test
	public void getRequestWithCookies_test() throws InterruptedException {
		mockWebServer.enqueue(new MockResponse().setBody("hello, world!"));
		
		// Prepare cookie map
		Map<String, Object> cookiesMap = new HashMap<String, Object>();
		cookiesMap.put("cookie1", "thiscookie");
		cookiesMap.put("cookie2", "myname");
		
		// Retrieve mockResponse from server and assert the results
		ResponseHttp responseHttp = requestHttp.get(mockWebServer.url("/").toString(),
			null, cookiesMap, null);
		assertEquals(responseHttp.statusCode(), 200);
		assertEquals(responseHttp.toString(), "hello, world!");
		
		// Check sent request's cookies
		RecordedRequest sentRequest = mockWebServer.takeRequest();
		Map<String, List<String>> requestHeaders = sentRequest.getHeaders().toMultimap();
		
		List<String> cookies = new ArrayList<String>();
		cookies.add("cookie1=thiscookie; cookie2=myname");
		assertEquals(cookies, requestHeaders.get("cookie"));
	}


	/**
	 * This test assert the headers sent via GET
	 * was received correctly on the server side
	 *
	 * @throws InterruptedException
	 */
	@Test
	public void getRequestWithHeaders_test() throws InterruptedException {
		mockWebServer.enqueue(new MockResponse().setBody("hello, world!"));
		
		// Prepare headers
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("first", "random-value");
		headers.put("second", "single-value");
		
		// Retrieve mockResponse from server and assert the results
		ResponseHttp responseHttp = requestHttp.get(mockWebServer.url("/").toString(),
			null, null, headers);
		assertEquals(responseHttp.statusCode(), 200);
		assertEquals(responseHttp.toString(), "hello, world!");
		
		// Check sent request's headers
		RecordedRequest sentRequest = mockWebServer.takeRequest();
		Map<String, List<String>> serverRequestHeaders = sentRequest.getHeaders().toMultimap();
		
		List<String> firstHeader = new ArrayList<String>();
		firstHeader.add("random-value");
		assertEquals(firstHeader, serverRequestHeaders.get("first"));
		
		List<String> secondHeader = new ArrayList<String>();
		secondHeader.add("single-value");
		assertEquals(secondHeader, serverRequestHeaders.get("second"));
	}


	//------------------------------------------------
	//
	//  POST request test units
	//
	//------------------------------------------------


	/**
	 * This test assert that the basic post body
	 * is correctly sent via the POST request to the server
	 */
	@Test
	public void postRequestWithRequestParams() throws InterruptedException {
		mockWebServer.enqueue(new MockResponse().setBody("hello, world!"));
		
		// Prepare post body Params
		Map<String, Object> postBodyParams = new HashMap<String, Object>();
		postBodyParams.put("first_value","single-value");
		postBodyParams.put("second_value", "double-value");
		
		// Retrieve mockResponse from server and assert the results
		ResponseHttp responseHttp = requestHttp.post(mockWebServer.url("/").toString(),
			postBodyParams);
		assertEquals(responseHttp.statusCode(), 200);
		assertEquals(responseHttp.toString(), "hello, world!");
		
		// Check sent request's body
		RecordedRequest sentRequest = mockWebServer.takeRequest();
		assertEquals("second_value=double-value&first_value=single-value", sentRequest.getUtf8Body());
	}

	/**
	 * This test assert that the cookies
	 * is correctly sent via POST method to the server
	 *
	 * @throws InterruptedException
	 */
	@Test
	public void postRequestWithCookies_test() throws InterruptedException {
		mockWebServer.enqueue(new MockResponse().setBody("hello, world!"));
		
		// Prepare cookie map
		Map<String, Object> cookiesMap = new HashMap<String, Object>();
		cookiesMap.put("cookie1", "thiscookie");
		cookiesMap.put("cookie2", "myname" );
		
		// Retrieve mockResponse from server and assert the results
		ResponseHttp responseHttp = requestHttp.post(mockWebServer.url("/").toString(),
			null, cookiesMap, null);
		assertEquals(responseHttp.statusCode(), 200);
		assertEquals(responseHttp.toString(), "hello, world!");
		
		// Check sent request's cookies
		RecordedRequest sentRequest = mockWebServer.takeRequest();
		Map<String, List<String>> requestHeaders = sentRequest.getHeaders().toMultimap();
		
		List<String> cookies = new ArrayList<String>();
		cookies.add("cookie1=thiscookie; cookie2=myname");
		assertEquals(cookies, requestHeaders.get("cookie"));
	}

	/**
	 * This test assert that the headers
	 * is correctly sent via POST to the server
	 * using httpPost()
	 *
	 * @throws InterruptedException
	 */
	@Test
	public void postRequestWithHeaders_test() throws InterruptedException {
		mockWebServer.enqueue(new MockResponse().setBody("hello, world!"));
		
		// Prepare headers
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("first", "random-value");
		headers.put("second", "single-value");
		
		// Retrieve mockResponse from server and assert the results
		ResponseHttp responseHttp = requestHttp.post(mockWebServer.url("/").toString(),
			null, null, headers);
		assertEquals(responseHttp.statusCode(), 200);
		assertEquals(responseHttp.toString(), "hello, world!");
		
		// Check sent request's headers
		RecordedRequest sentRequest = mockWebServer.takeRequest();
		Map<String, List<String>> serverRequestHeaders = sentRequest.getHeaders().toMultimap();
		
		List<String> firstHeader = new ArrayList<String>();
		firstHeader.add("random-value");
		assertEquals(firstHeader, serverRequestHeaders.get("first"));
		
		List<String> secondHeader = new ArrayList<String>();
		secondHeader.add("single-value");
		assertEquals(secondHeader, serverRequestHeaders.get("second"));
	}

	/**
	 * This test assert that the params
	 * is correctly sent via POST to the server
	 * using httpPostMultipart()
	 *
	 * @throws InterruptedException
	 */
	@Test
	public void postRequestWithMultipart_test() throws InterruptedException {
		mockWebServer.enqueue(new MockResponse().setBody("hello, world!"));
		
		Map<String, Object> params = new HashMap<String, Object>();
		String first = GUID.base64();
		String second = GUID.base64();
		String third = GUID.base64();
		params.put("first", first);
		params.put("second", second);
		
		// Retrieve mockResponse from server and assert the results
		ResponseHttp responseHttp = requestHttp.postMultipart(mockWebServer.url("/")
			.toString(), params);
		assertEquals(responseHttp.statusCode(), 200);
		assertEquals(responseHttp.toString(), "hello, world!");
		
		// Check sent request's body
		RecordedRequest sentRequest = mockWebServer.takeRequest();
		String body = sentRequest.getUtf8Body();
		assertTrue(body.indexOf(first) >= 0);
		assertTrue(body.indexOf(second) >= 0);
	}
	
	/**
	 * This test assert that the filesMap
	 * is correctly sent via POST to the server
	 * using httpPostMultipart()
	 *
	 * @throws InterruptedException
	 */
	@Test
	public void basic_post_request_multipart_files_only() throws IOException, InterruptedException {
		mockWebServer.enqueue(new MockResponse().setBody("hello, world!"));
		
		// Generating random files with random content
		Map<String, File[]> filesMap = new HashMap<String, File[]>();
		int number = 3;
		File[] fileArray = new File[number];
		for (int i = 0; i < number; i++) {
			File temp = File.createTempFile(GUID.base64(), ".tmp");
			String randomString = GUID.base64();
			FileOutputStream outputStream = new FileOutputStream(temp);
			byte[] strToBytes = randomString.getBytes();
			outputStream.write(strToBytes);
			outputStream.close();
			fileArray[i] = temp;
		}
		filesMap.put("files", fileArray);
		
		// Retrieve mockResponse from server and assert the results
		ResponseHttp responseHttp = requestHttp.postMultipart(mockWebServer.url("/")
			.toString(), null, filesMap);
		assertEquals(responseHttp.statusCode(), 200);
		assertEquals(responseHttp.toString(), "hello, world!");
		
		// Check sent request's body
		RecordedRequest sentRequest = mockWebServer.takeRequest();
		String body = sentRequest.getUtf8Body();
		
		for (File file : fileArray) {
			// Assert that file name exists
			assertTrue(body.indexOf(file.getName()) >= 0);
			
			// Assert that the content of file exists
			String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
			assertTrue(body.indexOf(content) >= 0);
		}
		
	}
	
	/**
	 * This test assert that the params and fileMap
	 * is correctly sent via POST to the server
	 * using httpPostMultipart()
	 *
	 * @throws InterruptedException
	 */
	@Test
	public void postRequestWithParamsAndFiles_test() throws IOException,
		InterruptedException {
		mockWebServer.enqueue(new MockResponse().setBody("hello, world!"));
		
		// Prepare params
		Map<String, Object> params = new HashMap<String, Object>();
		String first = GUID.base64();
		String second = GUID.base64();
		String third = GUID.base64();
		params.put("first", first);
		params.put("second", second);
		params.put("third", third);
		
		// Generating random files with random content
		Map<String, File[]> filesMap = new HashMap<String, File[]>();
		int number = 3;
		File[] fileArray = new File[number];
		for (int i = 0; i < number; i++) {
			File temp = File.createTempFile(GUID.base64(), ".tmp");
			String randomString = GUID.base64();
			FileOutputStream outputStream = new FileOutputStream(temp);
			byte[] strToBytes = randomString.getBytes();
			outputStream.write(strToBytes);
			outputStream.close();
			fileArray[i] = temp;
		}
		filesMap.put("files", fileArray);
		
		// Retrieve mockResponse from server and assert the results
		ResponseHttp responseHttp = requestHttp.postMultipart(mockWebServer.url("/")
			.toString(), params, filesMap);
		assertEquals(responseHttp.statusCode(), 200);
		assertEquals(responseHttp.toString(), "hello, world!");
		
		// Check sent request's body
		RecordedRequest sentRequest = mockWebServer.takeRequest();
		String body = sentRequest.getUtf8Body();
		
		assertTrue(body.indexOf(first) >= 0);
		assertTrue(body.indexOf(second) >= 0);
		assertTrue(body.indexOf(third) >= 0);
		
		for (File file : fileArray) {
			// Assert that file name exists
			assertTrue(body.indexOf(file.getName()) >= 0);
			
			// Assert that the content of file exists
			String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
			assertTrue(body.indexOf(content) >= 0);
		}
	}

		/**
	 * This test assert that the headers
	 * is correctly sent via POST to the server
	 * using httpPostMultipart()
	 *
	 * @throws InterruptedException
	 */
	@Test
	public void postReq() throws InterruptedException, IOException {
		mockWebServer.enqueue(new MockResponse().setBody("hello, world!"));
		
		// Prepare params
		Map<String, Object> params = new HashMap<String, Object>();
		String first = GUID.base64();
		String second = GUID.base64();
		String third = GUID.base64();
		params.put("first", first);
		params.put("second", second);
		params.put("third", third);

		// Generating random files with random content
		Map<String, File[]> filesMap = new HashMap<String, File[]>();
		int number = 3;
		File[] fileArray = new File[number];
		for (int i = 0; i < number; i++) {
			File temp = File.createTempFile(GUID.base64(), ".tmp");
			String randomString = GUID.base64();
			FileOutputStream outputStream = new FileOutputStream(temp);
			byte[] strToBytes = randomString.getBytes();
			outputStream.write(strToBytes);
			outputStream.close();
			fileArray[i] = temp;
		}
		filesMap.put("files", fileArray);

		// Prepare cookie map
		Map<String, Object> cookiesMap = new HashMap<String, Object>();
		cookiesMap.put("cookie1", "thiscookie");
		cookiesMap.put("cookie2", "myname");

		// Prepare headers
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("first", "random-value");
		headers.put("second", "single-value");
		
		// Retrieve mockResponse from server and assert the results
		ResponseHttp responseHttp = requestHttp.postMultipart(mockWebServer.url("/")
			.toString(), params, filesMap, cookiesMap, headers);
		assertEquals(responseHttp.statusCode(), 200);
		assertEquals(responseHttp.toString(), "hello, world!");
		
		// Check sent request's headers
		RecordedRequest sentRequest = mockWebServer.takeRequest();

		// Assert post params body
		//assertEquals("first="+first+"&second="+second+"&third="+third, sentRequest.getUtf8Body());

		// Assert files
		String body = sentRequest.getUtf8Body();
		for (File file : fileArray) {
			// Assert that file name exists
			assertTrue(body.indexOf(file.getName()) >= 0);
			
			// Assert that the content of file exists
			String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
			assertTrue(body.indexOf(content) >= 0);
		}

		// Assert headers
		Map<String, List<String>> serverRequestHeaders = sentRequest.getHeaders().toMultimap();
		List<String> firstHeader = new ArrayList<String>();
		firstHeader.add("random-value");

		assertEquals(firstHeader, serverRequestHeaders.get("first"));
		
		List<String> secondHeader = new ArrayList<String>();
		secondHeader.add("single-value");
		assertEquals(secondHeader, serverRequestHeaders.get("second"));

		// Assert cookies
		List<String> cookies = new ArrayList<String>();
		cookies.add("cookie1=thiscookie; cookie2=myname");
		assertEquals(cookies, serverRequestHeaders.get("cookie"));

	}
}
