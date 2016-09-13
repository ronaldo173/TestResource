package ua.nure.efimov.summarytask4.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

/**
 * Class for captcha verification. Uses reCAPTCHA Google service.
 * 
 * @author Alexandr Efimov
 *
 */
public class VerifyRecaptcha {

	public static final String URL = "https://www.google.com/recaptcha/api/siteverify";
	private static final String SECRET = "6Ld34CgTAAAAAHioS5mefUnpvE2HiwhHWCBYOsnU";
	private final static String USER_AGENT = "Mozilla/5.0";

	/**
	 * Check captcha.
	 * 
	 * @param gRecaptchaResponse
	 *            is response from captcha check
	 * @return true if passed
	 * @throws IOException
	 *             if smth happened
	 */
	public static boolean verify(String gRecaptchaResponse) throws IOException {
		if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
			return false;
		}
		try {
			URL obj = new URL(URL);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			// add request header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String postParams = "secret=" + SECRET + "&response=" + gRecaptchaResponse;

			// Send post request
			con.setDoOutput(true);
			try (DataOutputStream wr = new DataOutputStream(con.getOutputStream());) {
				wr.writeBytes(postParams);

			}

			StringBuffer response;
			try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
				String inputLine;
				response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
			}

			// parse JSON response and return 'success' value
			JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
			JsonObject jsonObject = jsonReader.readObject();
			jsonReader.close();

			return jsonObject.getBoolean("success");
		} catch (Exception e) {
			return false;
		}
	}
}