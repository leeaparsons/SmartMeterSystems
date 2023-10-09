// package com.group15.interactivemapservice;

// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.openqa.selenium.By;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.chrome.ChromeOptions;
// import org.springframework.boot.test.context.SpringBootTest;
// import io.github.bonigarcia.wdm.WebDriverManager;

// import static org.junit.jupiter.api.Assertions.assertFalse;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
// public class MapAcceptanceTest {

//     WebDriver driver;

//     @BeforeAll
//     static void setupDriver() {
//         WebDriverManager.chromedriver().setup();
//     }

//     @BeforeEach
//     public void setup() {
//         ChromeOptions options = new ChromeOptions();
//         options.addArguments("--headless");
//         options.addArguments("--no-sandbox");
//         driver = new ChromeDriver(options);
//     }

//     @AfterEach
//     public void close() {
//         driver.quit();
//     }

//     @Test
//     public void mapLoads(){
//         driver.get("http://localhost:8080/home.html");
//         WebElement mapContainer = driver.findElement(By.className("map-container"));
//         assertFalse(mapContainer.getText().isEmpty());
//     }
// }
