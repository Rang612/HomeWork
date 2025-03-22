public class APICall {
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.opensea.io/api/v2/collections"))
            .header("accept", "application/json")
            .header("x-api-key", "4b37af434b354c33affcdeab3b0ff98a")
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();
    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
System.out.println(response.body());
}
