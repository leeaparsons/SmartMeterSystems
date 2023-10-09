//package com.group15.interactivemapservice;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import java.time.Duration;
//import static org.junit.Assert.assertTrue;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class TableDataSearchSortAcceptanceTest {
//
//    @Value("${local.server.port}")
//    private int port;
//    WebDriver webDriver;
//    WebDriverWait wait;
//
//    @BeforeEach
//    void setupTest() {
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--remote-debugging-port=42227");
//        options.addArguments("--headless");
//        options.addArguments("--window-size=1296x696");
//        webDriver = new ChromeDriver(options);
//        wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
//    }
//
//    @AfterEach
//    void teardown() {
//        webDriver.quit();
//    }
//
//
//    @Test
//    public void mpxnSearchAcceptance() {
//        this.webDriver.get("http://localhost:" + Integer.toString(port) + "/table.html");
//        WebElement dataTable = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"table-body\"]/tr[1]/td[1]")));
//        assertTrue(dataTable.getText().contains("1023536563502"));
//
//        this.webDriver.findElement(By.id("search-input")).sendKeys("3506");
//        this.webDriver.findElement(By.id("searchButton")).click();
//
//        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\"table-body\"]/tr[1]/td[1]"), ("1023536563506")));
//
//        this.webDriver.findElement(By.id("clearButton")).click();
//        this.webDriver.findElement(By.id("search-input")).sendKeys("3561");
//        this.webDriver.findElement(By.id("searchButton")).click();
//
//        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\"table-body\"]/tr[1]/td[1]"), ("1023536563561")));
//
//        this.webDriver.findElement(By.id("clearButton")).click();
//
//        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\"table-body\"]/tr[1]/td[1]"), ("1023536563502")));
//    }
//
//    @Test
//    public void postcodeSearchAcceptance() {
//        this.webDriver.get("http://localhost:" + Integer.toString(port) + "/table.html");
//        WebElement dataTable = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"table-body\"]/tr[1]/td[9]")));
//        assertTrue(dataTable.getText().contains("GU1 4RY"));
//
//        this.webDriver.findElement(By.id("search-input")).sendKeys("EX13");
//        this.webDriver.findElement(By.id("searchButton")).click();
//
//        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\"table-body\"]/tr[1]/td[9]"), ("EX13 5RZ")));
//
//        this.webDriver.findElement(By.id("clearButton")).click();
//        this.webDriver.findElement(By.id("search-input")).sendKeys("BD9");
//        this.webDriver.findElement(By.id("searchButton")).click();
//
//        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\"table-body\"]/tr[1]/td[9]"), ("BD9 6DL")));
//
//        this.webDriver.findElement(By.id("clearButton")).click();
//
//        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\"table-body\"]/tr[1]/td[9]"), ("GU1 4RY")));
//    }
//
//
//    @Test
//    public void mpxnSortAcceptance() {
//        this.webDriver.get("http://localhost:" + Integer.toString(port) + "/table.html");
//        WebElement dataTable = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"table-body\"]/tr[1]/td[1]")));
//        assertTrue(dataTable.getText().contains("1023536563502"));
//
//        this.webDriver.findElement(By.cssSelector(".sort-header:nth-child(1)")).click();
//        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\"table-body\"]/tr[1]/td[1]"), ("1023536564000")));
//
//        this.webDriver.findElement(By.cssSelector(".sort-header:nth-child(1)")).click();
//        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\"table-body\"]/tr[1]/td[1]"), ("1023536563502")));
//
//        this.webDriver.findElement(By.cssSelector(".sort-header:nth-child(1)")).click();
//        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\"table-body\"]/tr[1]/td[1]"), ("1023536564000")));
//    }
//
//    @Test
//    public void postcodeSortAcceptance() {
//        this.webDriver.get("http://localhost:" + Integer.toString(port) + "/table.html");
//        WebElement dataTable = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"table-body\"]/tr[1]/td[9]")));
//        assertTrue(dataTable.getText().contains("GU1 4RY"));
//
//        this.webDriver.findElement(By.cssSelector(".sort-header:nth-child(9)")).click();
//        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\"table-body\"]/tr[1]/td[9]"), ("YO30 5PB")));
//
//        this.webDriver.findElement(By.cssSelector(".sort-header:nth-child(9)")).click();
//        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\"table-body\"]/tr[1]/td[9]"), ("AB11 7SY")));
//
//        this.webDriver.findElement(By.cssSelector(".sort-header:nth-child(9)")).click();
//        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\"table-body\"]/tr[1]/td[9]"), ("YO30 5PB")));
//    }
//}