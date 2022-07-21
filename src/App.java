import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        // buscar os dados do imdb através de uma requisição http do tipo get
        //---------------------------------------------------------------------

        // fazer a conexão HTTP para buscar os top 250 filmes
        // todo: formatar valor da chave
        String url = "https://api.mocki.io/v2/549a5d8b/MostPopularMovies"; 
        URI uri = URI.create( url );
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder( uri ).GET().build();
        
        // separar no corpo da requisição para extrair somente os dados importantes
        // { titulo, poster, classificacao }
        HttpResponse<String> response = client.send( request, BodyHandlers.ofString() );
        String body = response.body();

        JsonParser jsonParser = new JsonParser();
        List<Map<String, String>> arrFilmes = jsonParser.parse( body );

        //exibir e manipular os dados na aplicação
        for (Map<String,String> umFilme : arrFilmes ) {
            InputStream inputStream = new URL( umFilme.get("image" ) ).openStream();

            CreateSticker newSticker = new CreateSticker();
            newSticker.create(inputStream, String.format("figurinha%s.png", umFilme.get( "rank") ) );

            System.out.printf( "Titulo: %s\nNota no IMDB: %s\n\n ", 
                                        umFilme.get( "title" ), 
                                        umFilme.get( "imDbRating" ) );
        }
    }
}
