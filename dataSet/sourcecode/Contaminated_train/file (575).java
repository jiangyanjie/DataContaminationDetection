package src.main.java.challenge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ConnectionGet {

	private static final String DOLLAR_SYMBOL = "$";
	private static String API_KEY = "52ddafbe3ee659bad97fcce7c53592916a6bfd73";
	private static String SEARCH_STRING = "shoes";
	private static String PRODUCT_COUNT = "5";

	// http://localhost:8080/RESTfulExample/json/product/get
	public static void main(String[] args) {

		try {
			// URL url = new URL("http://api.zappos.com/Search?term="
			// +SEARCH_STRING +"&key="+API_KEY);
			URL url = new URL(
					"http://api.zappos.com/Search?term=shoes&key=a73121520492f88dc3d33daf2103d7574f1a3166");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() > 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			if (conn.getResponseMessage() != null) {

			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output = null;
			String fullOutput = "";
			while ((output = br.readLine()) != null) {
				// System.out.println(output);
				fullOutput += output;
			}

			conn.disconnect();

			Mapper mapper = new Mapper();
			FinalOutputBean finalOutputBean = mapper
					.getPojoFromJsonString(fullOutput);

			BufferedReader bufferReader = new BufferedReader(
					new InputStreamReader(System.in));
			System.out.println("Please enter price:");
			String inputPrice = bufferReader.readLine();
			if (inputPrice.contains(DOLLAR_SYMBOL)) {
				inputPrice.replace(DOLLAR_SYMBOL, "");
			}
			System.out.println("Please enter # of products (Only Numbers): ");
			int productsCount = Integer.parseInt(bufferReader.readLine());

			System.out
					.println("Your preferred price limit and count of products:"
							+ inputPrice + "\n" + productsCount);
			float price = Float.parseFloat(inputPrice);
			List<Results> listOutput = mapper
					.parseValuesToCalculateGiftsCombination(productsCount,
							price, finalOutputBean);

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}
}