import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class OpenSea1DScraper implements Scraper {

    public Map<String, JSONObject> scrape() {
        Map<String, JSONObject> sex = new LinkedHashMap<>();
        String url = "https://opensea.io/rankings/trending?sortBy=one_day_volume";

        try {
            final String json = Jsoup.connect(url).userAgent("Jsoup client").ignoreContentType(true).execute().body();
            Document doc = Jsoup.parse(json);
            Element element = doc.select("script#__NEXT_DATA__").first();

            if (element == null) {
                throw new IllegalStateException("Không tìm thấy dữ liệu JSON trong HTML!");
            }

            String data = element.data();
            JSONObject dataJson = new JSONObject(data);
            JSONObject props = dataJson.getJSONObject("props");
            JSONObject pageProps = props.getJSONObject("pageProps");
            JSONObject initialRecords = pageProps.getJSONObject("initialRecords");

            Map<String, String> outputRows = new LinkedHashMap<>();

            for (String s : initialRecords.keySet()) {
                JSONObject record = initialRecords.getJSONObject(s);
                if (record.has("name")) {
                    String id = record.getString("__id");
                    String name = record.getString("name");
                    String image = record.optString("logo", "N/A");

                    String properties = "\"name\": \"" + name + "\", \"url\": \"" + image + "\"";
                    outputRows.put(id, outputRows.getOrDefault(id, "\"id\": \"" + id + "\"") + ", " + properties);
                } else if (record.has("symbol")) {
                    String client = record.getString("__id");
                    String id = client.split(":")[1];
                    String type = client.split(":")[4];
                    String properties = "";

                    if (Objects.equals(type, "volume")) {
                        properties = "\"volume\": \"" + record.getString("unit") + "\"";
                    } else if (Objects.equals(type, "floorPrice")) {
                        properties = "\"floorPrice\": \"" + record.getString("unit") + "\"";
                    }

                    outputRows.put(id, outputRows.getOrDefault(id, "\"id\": \"" + id + "\"") + ", " + properties);
                } else if (record.has("floorPrice")) {
                    String id = record.getString("__id").split(":")[1];
                    String properties =
                            "\"numOfSales\": \"" + record.get("numOfSales") + "\", " +
                                    "\"numOwners\": \"" + record.get("numOwners") + "\", " +
                                    "\"volumeChange\": \"" + record.get("volumeChange") + "\", " +
                                    "\"totalSupply\": \"" + record.get("totalSupply") + "\"";

                    outputRows.put(id, outputRows.getOrDefault(id, "\"id\": \"" + id + "\"") + ", " + properties);
                }
            }

            for (Map.Entry<String, String> row : outputRows.entrySet()) {
                String valueString = '{' + row.getValue() + '}';
                sex.put(row.getKey(), new JSONObject(valueString));
            }

            // Ghi dữ liệu vào file JSON
            saveToJsonFile(sex, "opensea_data.json");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return sex;
    }

    private void saveToJsonFile(Map<String, JSONObject> data, String filename) {
        try (FileWriter file = new FileWriter(filename)) {
            JSONObject jsonObject = new JSONObject(data);
            file.write(jsonObject.toString(4));  // Ghi file với format đẹp (indent=4)
            System.out.println("Dữ liệu đã được ghi vào " + filename);
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        OpenSea1DScraper os = new OpenSea1DScraper();
        os.scrape();
    }
}
