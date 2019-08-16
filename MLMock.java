package mock;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;

import java.io.FileReader;
import java.util.concurrent.TimeUnit;

import static org.mockserver.model.HttpResponse.response;

import static org.mockserver.model.HttpRequest.request;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;

public class MLMock {


    static MockServerClient mockServerClient = startClientAndServer(8082);

    public static void getSites(String method, String path, int statusCode, String content, String body, long delay) {

        mockServerClient.when(

                request()
                        .withMethod(method)
                        .withPath(path)
        ).respond(

                response()
                        .withStatusCode(statusCode)
                        .withHeader(new Header("Content-Type", content))
                        .withBody(body)
                        .withDelay(new Delay(TimeUnit.MILLISECONDS, delay))
        );
    }

    public static void getCategories(String method, String path, int statusCode, String content, String body, long delay) {

        mockServerClient.when(

                request()
                        .withMethod(method)
                        .withPath(path)
        ).respond(

                response()
                        .withStatusCode(statusCode)
                        .withHeader(new Header("Content-Type", content))
                        .withBody(body)
                        .withDelay(new Delay(TimeUnit.MILLISECONDS, delay))
        );
    }


    public static void main(String[] args) {

        Gson gson = new Gson();

        JsonParser parser = new JsonParser();
        JsonElement jsonSites = null;
        try {
            Object obj = parser.parse(new FileReader("/Users/prisma/IdeaProjects/MockSites/src/main/java/mock/sites.json"));

            jsonSites = gson.toJsonTree(obj);

        } catch (Exception ex) {
            System.err.println("Error: " + ex.toString());
        }

        JsonParser parserCat = new JsonParser();
        JsonElement jsonCategories = null;
        try {
            Object obj = parserCat.parse(new FileReader("/Users/prisma/IdeaProjects/MockSites/src/main/java/mock/categoriesMLA.json"));

            jsonCategories = gson.toJsonTree(obj);

        } catch (Exception ex) {
            System.err.println("Error: " + ex.toString());
        }


        getSites("GET", "/sites", 200, "application/json", gson.toJson(jsonSites), 5000);

        getCategories("GET", "/sites/MLA/categories", 200, "application/json", gson.toJson(jsonCategories), 5000);


    }

}



