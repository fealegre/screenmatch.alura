package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;
import com.fasterxml.jackson.core.JsonToken;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=7f2ed73c";
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraMenu(){
        System.out.println("Ingrese el nombre de la serie a buscar: ");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE+nombreSerie.replace(" ","+")+API_KEY);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);

        //Busca los datos de todas las temporadas

        List<DatosTemporadas> temporadas = new ArrayList<>();
        for (int i = 1; i <= datos.totalTemporadas() ; i++) {
            json = consumoApi.obtenerDatos(URL_BASE+nombreSerie.replace(" ","+")+"&Season="+i+API_KEY);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporadas);
        }
        //temporadas.forEach(System.out::println);

        //Mostrar solo titulo de los episodios de cada temporada

//        for (int i = 0; i <datos.totalTemporadas() ; i++) {
//            List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size() ; j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }
        //temporadas.forEach(t->t.episodios().forEach(e-> System.out.println(e.titulo())));
        //Convertir todas la informaciones en una lista de tipo DatosEpisodio
        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t->t.episodios().stream())
                .collect(Collectors.toList());

        //Top 5 episodios
//        System.out.println("TOP 5 EPISODIOS");
//        datosEpisodios.stream()
//                .filter(n->!n.evaluacion().equalsIgnoreCase("N/A"))
//                .peek(e-> System.out.println("Primer filtro (N/A)" + e))
//                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
//                .peek(e-> System.out.println("Segundo filtro (ORDENAMIENTO)" + e))
//                .map(e->e.titulo().toUpperCase())
//                .peek(e-> System.out.println("Tercer filtro (MAUSCULA)"))
//                .limit(5)
//                .forEach(System.out::println);

        //Convirtiendo los datos a una lista de tipo Espisodios

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t->t.episodios().stream()
                        .map(d->new Episodio(t.numeroTemporada(),d)))
                .collect(Collectors.toList());
        //episodios.forEach(System.out::println);

        //Busqueda de episodios a partir de x año

//        System.out.println("Indique un año para ver sus episodios: ");
//        var fecha = teclado.nextInt();
//        teclado.nextLine();
//        LocalDate fechaBusqueda = LocalDate.of(fecha, 1,1);
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e->e.getFechaLanzamiento()!=null && e.getFechaLanzamiento().isAfter(fechaBusqueda))
//                .forEach(e-> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                " - Episodio: " + e.getTitulo() +
//                                " - Fecha de lanzamiento: " + e.getFechaLanzamiento().format(dtf)
//                ));

        //Busca episodios por titulo

        System.out.println("Ingrese titulo del episodio a buscar: ");
        var tituloBuscado = teclado.nextLine();
        Optional<Episodio> episodioEncontrado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(tituloBuscado.toUpperCase()))
                .findFirst();
        if (episodioEncontrado.isPresent()) {
            System.out.println("Episodio encontrado");
            System.out.println("Titulo episodio: " + episodioEncontrado.get());
        } else {
            System.out.println("Episodio no encontrado");
        }
//        Map<Integer, Double> evalucionesPorTemporada = episodios.stream()
//                .filter(a->a.getEvaluaciones()>0.0)
//                .collect(Collectors.groupingBy(Episodio::getTemporada,
//                        Collectors.averagingDouble(Episodio::getEvaluaciones)));
//        System.out.println(evalucionesPorTemporada);
//
//        DoubleSummaryStatistics est = episodios.stream()
//                .filter(e->e.getEvaluaciones()>0.0)
//                .collect(Collectors.summarizingDouble(Episodio::getEvaluaciones));
//        System.out.println(est);

    }
}
