# Vehicle Circulation - "Pico y Placa" predictor

## Problem
Using the language that you feel most proficient in, we’d like you to write a "Pico y Placa" predictor. The inputs should be a license plate number (the full number, not the last digit), a date (as a String), and a time, and the program will return whether or not that car can be on the road. You may use any input and output method you prefer.

>Pico & Placa it's an is a driving restriction policy aimed to mitigate traffic congestion. The system restricts traffic access into a pre-established urban area for vehicles with their plate numbers ending in certain digits on pre-established days and during certain hours.
>
>In Quito vehicles that has their ending plate number like this are not able to circulate on traffic:
>
>Monday : 1 - 2 
>
>Tuesday: 3 - 4
>
>Wednesday: 5 - 6
>
>Thursday: 7 - 8 
>
>Friday: 9 - 0. 
>
>*There's no restriction on weekends.* 
>
>Initially the system restricted traffic between 7:00 to 9:30 am and from 16:00 pm to 19:30 pm.

## Solution
The solution exposes a rest service for the validation of the "pico y placa".

For the development was used `Spring Boot` and `Java`, a configuration file (`application.properties`) was used to enter time ranges

#### Project Structure

* `com.jcalvopinam.vehiclecirculation`: It has the main class `VehicleCirculationApplication.java`.
* `com.jcalvopinam.vehiclecirculation.config`: It contains the `SwaggerConfig.java` class for the API documentation.
* `com.jcalvopinam.vehiclecirculation.controller`: It contains the `VehicleController.java` class for the incoming requests.
* `com.jcalvopinam.vehiclecirculation.domain`: It contains the domain classes such as `Policy.java`, `Time.java` and `Vehicle.java`.
* `com.jcalvopinam.vehiclecirculation.dto`: It contains the DTOs. `ExceptionResponseDTO.java` is used for the Error Handling and `VehicleResponseDTO.java` is used to show the response of incoming requests.
* `com.jcalvopinam.vehiclecirculation.exception`: It contains the custom exceptions to be displayed depending on the validation.
* `com.jcalvopinam.vehiclecirculation.service`: It contains the services in charge of the logic.
* `com.jcalvopinam.vehiclecirculation.utils`: It contains the class for the validations.

## Technology Stack
* Java 11
* Spring Boot Web 2.3.4.RELEASE
* Lombok
* Swagger 2.9.2
* Spring Boot Tests (jUnit 5, Mockito Core)
* Maven 3.6.3 (wrapper)

## How to run?

1. Open the terminal and go to the root of the *vehicle-circulation* project:
    ```sh
    $ cd vehicle-circulation/
    ```

2. Clean, Compile and run the application:

    2.1 For S.O. based on Unix:
    ```sh
    $ ./mvnw clean install spring-boot:run
    ```

    2.2 For Windows:
    ```sh
    $ mvnw clean install spring-boot:run
    ```

3. Rest Client:

    The application will start at port 8080, in the browser open the following address:

    ```
    http://localhost:8080/vehicle-circulation/swagger-ui.html
    ```

    * Swagger, an interactive interface with the API documentation will be shown to execute the requests.
    
    * As an alternative it is possible to import the `src/main/resources/collection/vehicle-circulation.postman_collection.json` file into [Postman](https://www.postman.com/downloads/) client

4. Request Structure:

    **URI:** `http://localhost:8080/vehicle-circulation/api`
    
    **METHOD:** `POST`
    
    **BODY:** structure
    ```
    {
        "plateNumber":"3Chars-4Numbers",
        "date": "yyyy/MM/dd",
        "time": "hh:mm"
    }
    ```
    
    **BODY:** sample
    ```
    {
        "plateNumber":"AAA-0001",
        "date": "2020/10/05",
        "time": "06:00"
    }
    ```

    **OUTPUT:** sample
    ```
    {
        "type": "Allowed hours",
        "message": "It is permitted to circulate in this hours."
    }
    ```

4. To exit press <kbd>Ctrl</kbd>+<kbd>C</kbd> in the terminal

--
> Author:
> Juan Calvopina | <juan.calvopina@gmail.com>