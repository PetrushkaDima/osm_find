package com.osm.findarea.service;

import com.osm.findarea.Result;
import com.osm.findarea.jsonModel.*;
import org.locationtech.jts.geom.CoordinateXY;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkService {
    RestTemplate restTemplate = new RestTemplate();

    private static final String SEARCH_URL = "https://nominatim.openstreetmap.org/search?q=%s" +
            "&country=russia&format=json&polygon_geojson=1";

    public static final String DETAILS_URL = "https://nominatim.openstreetmap.org/details?place_id=%s" +
            "&hierarchy=1&format=json&polygon_geojson=1";

    @Cacheable("find")
    public Result find(String query, String type) {
        Model[] model = restTemplate.getForObject(String.format(SEARCH_URL, query), Model[].class);
        if (model == null) {
            throw new IllegalArgumentException("По данному запросу ничего не найдено");
        }
        String placeId = model[0].getPlaceId();
        Structure structure = restTemplate.getForObject(String.format(DETAILS_URL, placeId), Structure.class);
        if (structure == null) {
            throw new NullPointerException();
        }
        if (structure.getHierarchy().length == 0) {
            throw new IllegalArgumentException("У объекта нет \"предков\"");
        }
        List<Hierarchy> filterHierarchy = Arrays.stream(structure.getHierarchy()).
                filter(hierarchy -> type.equals(hierarchy.getType())).
                collect(Collectors.toList());
        if (filterHierarchy.size() == 0) {
            throw new IllegalArgumentException(String.format("У объекта нет \"предков\" с типом \"%s\"", type));
        }
        return calculateArea(filterHierarchy);
    }

    private Result calculateArea(List<Hierarchy> filterHierarchy) {
        double maxArea = 0;
        Result result = new Result();
        for (Hierarchy candidate : filterHierarchy) {
            TypeStructure typeStructure = restTemplate.getForObject(String.format(
                    DETAILS_URL, candidate.getPlaceId()), TypeStructure.class);
            String geometryType = typeStructure.getGeometryType().getType();
            List<List<Double>> listCoordinates = new ArrayList<>();
            if (geometryType.equals("Polygon")) {
                listCoordinates = restTemplate.getForObject(
                        String.format(DETAILS_URL, candidate.getPlaceId()), PolygonStructure.class)
                        .getGeometry().getCoordinates().get(0);
            } else if (geometryType.equals("Multipolygon")) {
                listCoordinates = restTemplate.getForObject(
                        String.format(DETAILS_URL, candidate.getPlaceId()), MultipolygonStructure.class)
                        .getGeometry().getCoordinates().get(0).get(0);

            } else continue;
            List<CoordinateXY> coordinates = new ArrayList<>();
            for (List<Double> doubles : listCoordinates) {
                coordinates.add(new CoordinateXY(doubles.get(0), doubles.get(1)));
            }
            GeometryFactory geometryFactory = new GeometryFactory();
            Polygon polygon = geometryFactory.createPolygon(coordinates.toArray(new CoordinateXY[]{}));
            if (polygon.getArea() > maxArea) {
                maxArea = polygon.getArea();
                result.setName(candidate.getLocalname());
                result.setCentroid(polygon.getCentroid().toString());
                result.setCoordinates(listCoordinates);
            }
        }
        return result;
    }
}
