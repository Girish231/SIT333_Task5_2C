package sit707_week5;

import org.junit.Assert;
import org.junit.Test;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherControllerTest {

    interface TimeProvider {
        Date now();
    }

    @Test
    public void testStudentIdentity() {
        String studentId = "12345678";
        Assert.assertNotNull("Student ID is null", studentId);
    }

    @Test
    public void testStudentName() {
        String studentName = "John Doe";
        Assert.assertNotNull("Student name is null", studentName);
    }

    @Test
    public void testTemperatureMin() {
        System.out.println("+++ testTemperatureMin +++");

        WeatherController wController = WeatherController.getInstance();
        int nHours = wController.getTotalHours();
        double minTemperature = 1000;
        for (int i = 0; i < nHours; i++) {
            double temperatureVal = wController.getTemperatureForHour(i + 1);
            if (minTemperature > temperatureVal) {
                minTemperature = temperatureVal;
            }
        }
        Assert.assertTrue(wController.getTemperatureMinFromCache() == minTemperature);
        wController.close();
    }

    @Test
    public void testTemperatureMax() {
        System.out.println("+++ testTemperatureMax +++");

        WeatherController wController = WeatherController.getInstance();
        int nHours = wController.getTotalHours();
        double maxTemperature = -1;
        for (int i = 0; i < nHours; i++) {
            double temperatureVal = wController.getTemperatureForHour(i + 1);
            if (maxTemperature < temperatureVal) {
                maxTemperature = temperatureVal;
            }
        }
        Assert.assertTrue(wController.getTemperatureMaxFromCache() == maxTemperature);
        wController.close();
    }

    @Test
    public void testTemperatureAverage() {
        System.out.println("+++ testTemperatureAverage +++");

        WeatherController wController = WeatherController.getInstance();
        int nHours = wController.getTotalHours();
        double sumTemp = 0;
        for (int i = 0; i < nHours; i++) {
            double temperatureVal = wController.getTemperatureForHour(i + 1);
            sumTemp += temperatureVal;
        }
        double averageTemp = sumTemp / nHours;
        Assert.assertTrue(wController.getTemperatureAverageFromCache() == averageTemp);
        wController.close();
    }

    @Test
    public void testTemperaturePersist() {
        System.out.println("+++ testTemperaturePersist +++");

        WeatherController wController = WeatherController.getInstance();

        // Use fixed time provider
        Date fixedDate = new Date(); // current moment
        String expectedTime = new SimpleDateFormat("H:m:s").format(fixedDate);

        // Inject time provider
        wController.setTimeProvider(() -> fixedDate);

        String persistTime = wController.persistTemperature(10, 19.5);
        System.out.println("Persist time: " + persistTime + ", expected: " + expectedTime);

        Assert.assertEquals(expectedTime, persistTime);
        wController.close();
    }
}
