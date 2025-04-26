package sit707_week5;

import org.junit.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class WeatherControllerTest {

    private static WeatherController wController;
    private static List<Double> hourlyTemperatures;

    @BeforeClass
    public static void setUp() {
        wController = WeatherController.getInstance();
        hourlyTemperatures = new ArrayList<>();

        int nHours = wController.getTotalHours();
        for (int i = 0; i < nHours; i++) {
            double temperatureVal = wController.getTemperatureForHour(i + 1);
            hourlyTemperatures.add(temperatureVal);
        }
    }

    @AfterClass
    public static void tearDown() {
        wController.close();
    }

    @Test
    public void testStudentIdentity() {
        String studentId = "222121587"; // Updated
        Assert.assertNotNull("Student ID is null", studentId);
    }

    @Test
    public void testStudentName() {
        String studentName = "Girish Sirpali"; // Updated
        Assert.assertNotNull("Student name is null", studentName);
    }

    @Test
    public void testTemperatureMin() {
        System.out.println("+++ testTemperatureMin +++");

        double minTemperature = hourlyTemperatures.stream().min(Double::compare).get();

        Assert.assertEquals(minTemperature, wController.getTemperatureMinFromCache(), 0.001);
    }

    @Test
    public void testTemperatureMax() {
        System.out.println("+++ testTemperatureMax +++");

        double maxTemperature = hourlyTemperatures.stream().max(Double::compare).get();

        Assert.assertEquals(maxTemperature, wController.getTemperatureMaxFromCache(), 0.001);
    }

    @Test
    public void testTemperatureAverage() {
        System.out.println("+++ testTemperatureAverage +++");

        double average = hourlyTemperatures.stream().mapToDouble(Double::doubleValue).average().getAsDouble();

        Assert.assertEquals(average, wController.getTemperatureAverageFromCache(), 0.001);
    }

    @Test
    public void testTemperaturePersist() {
        System.out.println("+++ testTemperaturePersist +++");

        WeatherController wController = WeatherController.getInstance();

        // Create fixed Date
        Date fixedDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("H:m:s");
        String expectedTime = sdf.format(fixedDate);

        // Inject TimeProvider that returns the fixed Date
        wController.setTimeProvider(() -> fixedDate);

        // Call persistTemperature, it will internally call timeProvider.now()
        String persistTime = wController.persistTemperature(10, 19.5);

        System.out.println("Persist time: " + persistTime + ", expected: " + expectedTime);

        Assert.assertEquals(expectedTime, persistTime);

        wController.close();
    }
}
