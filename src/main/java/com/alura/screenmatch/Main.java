package com.alura.screenmatch;

import com.google.gson.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {


        Scanner lectura = new Scanner(System.in);

        var busqueda = 0;
        double cantidad = 0;
        while (true) {

            // Interfaz de Usuario
          try {
              UIUser();

              busqueda = lectura.nextInt();

              if (busqueda == 0) {
                  break;
              }

              System.out.println("Ingrese una cantidad monetaria");
              cantidad = lectura.nextDouble();

              Map<String, Object> valores = getValores(busqueda);

              // Obtener los valores devueltos
              String input_1 = (String) valores.get("input_1");
              String input_2 = (String) valores.get("input_2");
              int flag = (int) valores.get("flag");

              String url_str = "https://v6.exchangerate-api.com/v6/f19b794933b88dd30185eb25/latest/" + input_1;

              Gson gson = new GsonBuilder()
                      .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                      .setPrettyPrinting()
                      .create();

              HttpClient client = HttpClient.newHttpClient();
              HttpRequest request = HttpRequest.newBuilder()
                      .uri(URI.create(url_str))
                      .build();
              HttpResponse<String> response = client
                      .send(request, HttpResponse.BodyHandlers.ofString());

              String json = response.body();
              // System.out.println(json);

              JsonObject jsonObject = new Gson().fromJson(response.body(), JsonObject.class);

              JsonObject ConversionRate = jsonObject.getAsJsonObject("conversion_rates");

              double usdConversionRate = ConversionRate.get("USD").getAsDouble();
              double arsConversionRate = ConversionRate.get(input_2).getAsDouble();

              // Imprimir el resultado
              imprimir(flag, cantidad, arsConversionRate, usdConversionRate, input_1, input_2);

          }catch (NullPointerException e){
              System.out.println("Selecciono una opcion incorrecta");

          }

        }

        System.out.println("Se finalizo el Sistema:: ");

    }

    private static void UIUser() {
        System.out.println("✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦");
        System.out.println("Sea Bienvenido/a al Conversor de Monedas =]");
        System.out.println(" ");

        System.out.println("1) DÓLAR ==> Peso Argentino");
        System.out.println("2) Peso Argentino ==> DÓLAR");
        System.out.println("3) DÓLAR ==> Real Brasileño");
        System.out.println("4) Real Brasileño ==> DÓLAR");
        System.out.println("5) DÓLAR ==> Boliviano boliviano");
        System.out.println("6) Boliviano boliviano ==> DÓLAR");
        System.out.println("7) DÓLAR ==> Peso Colombiano");
        System.out.println("8) Peso Colombiano ==> DÓLAR");


        System.out.println("0) Salir");
    }

    private static void imprimir(int flag, double cantidad, double arsConversionRate, double usdConversionRate, String input_1, String input_2) {
        if (flag == 1) {

            var resultado = cantidad * (arsConversionRate / usdConversionRate);

            System.out.println("El valor " + cantidad + "[" + input_1 + "] corresponde a valor final " +
                    "de === " + resultado + "[" + input_2 + "]");

        } else {

            var resultado = (cantidad / arsConversionRate);

            System.out.println("El valor " + cantidad + "[" + input_2 + "] corresponde a valor final " +
                    "de === " + resultado + "[" + input_1 + "]");
        }
    }

    public static Map<String, Object> getValores(int busqueda) {
        // Crear un Map para almacenar los valores
        Map<String, Object> valores = new HashMap<>();

        // Definir los valores según la búsqueda
        switch (busqueda) {
            case 1:
                valores.put("input_1", "USD");
                valores.put("input_2", "ARS");
                valores.put("flag", 1);
                break;
            case 2:
                valores.put("input_1", "USD");
                valores.put("input_2", "ARS");
                valores.put("flag", 2);
                break;
            case 3:
                valores.put("input_1", "USD");
                valores.put("input_2", "BRL");
                valores.put("flag", 1);
                break;
            case 4:
                valores.put("input_1", "USD");
                valores.put("input_2", "BRL");
                valores.put("flag", 2);
                break;
            case 5:
                valores.put("input_1", "USD");
                valores.put("input_2", "BOB");
                valores.put("flag", 1);
                break;
            case 6:
                valores.put("input_1", "USD");
                valores.put("input_2", "BOB");
                valores.put("flag", 2);
                break;
            case 7:
                valores.put("input_1", "USD");
                valores.put("input_2", "CLP");
                valores.put("flag", 1);
                break;
            case 8:
                valores.put("input_1", "USD");
                valores.put("input_2", "CLP");
                valores.put("flag", 2);
                break;
            default:
                System.out.println("Búsqueda no válida");
                break;
        }

        return valores;
    }


}