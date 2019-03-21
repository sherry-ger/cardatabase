package com.packt.cardatabase;

import com.packt.cardatabase.domain.CarRepository;
import com.packt.cardatabase.domain.Car;
import com.packt.cardatabase.domain.MarketEstimate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.Optional;


@RestController
public class CarController {


    @Value("${estimator.uri}")
    private String estimatorUri;

    @Autowired
    CarRepository carRepository;

    @Autowired
    public CarController(CarRepository repo) {
        carRepository = repo;
    }

    @RequestMapping(method= RequestMethod.GET, value="/api/cars")
    public Iterable<Car> Car() {
        System.out.println("In GET All");
        return carRepository.findAll();
    }

    @RequestMapping(method=RequestMethod.POST, value="/api/cars")
    public Car save(@RequestBody Car car) {
        System.out.println("In POST add");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(estimatorUri)
                // Add query parameter
                .queryParam("brand", car.getBrand())
                .queryParam("model", car.getModel())
                .queryParam("year", car.getYear());


        System.out.println(builder.build().toString());
        RestTemplate restTemplate = new RestTemplate();

        MarketEstimate carEstimate = restTemplate.getForObject(builder.build().toString(), MarketEstimate.class);
        System.out.println(carEstimate.toString());

        car.setMarketEstimate(carEstimate.getEstimate());

        carRepository.save(car);

        return car;
    }

    @RequestMapping(method=RequestMethod.GET, value="/api/cars/{id}")
    public Optional<Car> show(@PathVariable Long id) {
        System.out.println("In GET by id");
        return carRepository.findById(id);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/api/cars/{id}")
    public Car update(@PathVariable Long id, @RequestBody Car Car) {
        System.out.println("In PUT by id");
        Optional<Car> optCar = carRepository.findById(id);
        Car car = optCar.get();
        if(Car.getBrand() != null)
            car.setBrand(Car.getBrand());
        if(Car.getModel() != null)
            car.setModel(Car.getModel());
        if(Car.getColor() != null)
            car.setColor(Car.getColor());
        if(Car.getYear() != 0)
            car.setYear(Car.getYear());
        if(Car.getPrice() != 0)
            car.setPrice(Car.getPrice());

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(estimatorUri)
                // Add query parameter
                .queryParam("brand", car.getBrand())
                .queryParam("model", car.getModel())
                .queryParam("year", car.getYear());


        System.out.println(builder.build().toString());
        RestTemplate restTemplate = new RestTemplate();

        MarketEstimate carEstimate = restTemplate.getForObject(builder.build().toString(), MarketEstimate.class);
        System.out.println(carEstimate.toString());

        car.setMarketEstimate(carEstimate.getEstimate());


        carRepository.save(car);
        return car;

    }

    @RequestMapping(method=RequestMethod.DELETE, value="/api/cars/{id}")
    public String delete(@PathVariable long id) {
        Optional<Car> optCar = carRepository.findById(id);
        Car Car = optCar.get();
        carRepository.delete(Car);

        return "";
    }
}